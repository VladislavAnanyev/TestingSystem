package com.example.mywebquizengine.Service;


import com.example.mywebquizengine.Controller.api.ApiChatController;
import com.example.mywebquizengine.Model.Chat.Dialog;
import com.example.mywebquizengine.Model.Chat.Message;

//import com.example.mywebquizengine.Model.Projection.MessageForStompView;
import com.example.mywebquizengine.Model.Chat.MessageStatus;
import com.example.mywebquizengine.Model.Projection.Api.MessageForApiViewCustomQuery;
import com.example.mywebquizengine.Model.Projection.Api.MessageWithDialog;
import com.example.mywebquizengine.Model.Projection.DialogWithUsersViewPaging;
import com.example.mywebquizengine.Model.User;

import com.example.mywebquizengine.Repos.DialogRepository;
import com.example.mywebquizengine.Repos.MessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private DialogRepository dialogRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${hostname}")
    private String hostname;


    public void saveMessage(Message message) {
        messageRepository.save(message);
    }


    public Long checkDialog(String username, String authUsername) {

        if (!authUsername.equals(username)) {
            Long dialog_id = dialogRepository.findDialogByName(username,
                    authUsername);

            if (dialog_id == null) {
                Dialog dialog = new Dialog();

                dialog.addUser(userService.loadUserByUsername(username));
                dialog.addUser(userService.loadUserByUsername(authUsername));

                dialogRepository.save(dialog);
                return dialog.getDialogId();
            } else {
                return dialog_id;
            }
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);


    }



    /*public List<Message> getDialogs(String username) {
        return messageRepository.getDialogs(username);
    }*/

    public ArrayList<MessageForApiViewCustomQuery> getDialogsForApi(String username) {

        List<MessageForApiViewCustomQuery> messageViews = messageRepository.getDialogsForApi(username);

        return (ArrayList<MessageForApiViewCustomQuery>) messageViews;
    }


    @Transactional
    public void deleteMessage(Long id, String username) throws JsonProcessingException, ParseException {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            if (message.getSender().getUsername().equals(username)) {
                message.setStatus(MessageStatus.DELETED);

                MessageWithDialog messageDto = messageRepository.findMessageById(message.getId());
                JSONObject jsonObject = (JSONObject) JSONValue
                        .parseWithException(objectMapper.writeValueAsString(messageDto));
                jsonObject.put("type", "DELETE-MESSAGE");

                for (User user :message.getDialog().getUsers()) {

                    simpMessagingTemplate.convertAndSend("/topic/" + user.getUsername(),
                            jsonObject);

                    rabbitTemplate.convertAndSend(user.getUsername(),
                            jsonObject);

                }



            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public DialogWithUsersViewPaging getMessages(Long dialogId,
                                                 Integer page,
                                                 Integer pageSize,
                                                 String sortBy,
                                                 String username) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
        Optional<Dialog> optionalDialog = dialogRepository.findById(dialogId);
        optionalDialog.ifPresent(dialog -> dialog.setPaging(paging));
        DialogWithUsersViewPaging dialog = dialogRepository.findAllDialogByDialogId(dialogId);

        // If user contains in dialog
        if (dialog.getUsers().stream().anyMatch(o -> o.getEmail()
                .equals(username))) {
            return dialog;
        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);

    }

    @Transactional
    public void editMessage(Message newMessage, String username) throws JsonProcessingException, ParseException {
        Optional<Message> optionalMessage = messageRepository.findById(newMessage.getId());
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            if (message.getSender().getUsername().equals(username)) {
                message.setContent(newMessage.getContent());
                message.setStatus(MessageStatus.EDIT);

                MessageWithDialog messageDto = messageRepository.findMessageById(message.getId());
                JSONObject jsonObject = (JSONObject) JSONValue
                        .parseWithException(objectMapper.writeValueAsString(messageDto));
                jsonObject.put("type", "EDIT-MESSAGE");

                for (User user :message.getDialog().getUsers()) {

                    simpMessagingTemplate.convertAndSend("/topic/" + user.getUsername(),
                            jsonObject);

                    rabbitTemplate.convertAndSend(user.getUsername(),
                            jsonObject);

                }

            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }
    }




    public void sendMessage(Message message, String client) throws JsonProcessingException, ParseException {

        User sender = userService.loadUserByUsernameProxy(message.getSender().getUsername());

        message.setSender(sender);
        message.setDialog(dialogRepository.findById(message.getDialog().getDialogId()).get());
        message.setTimestamp(new Date());
        message.setStatus(MessageStatus.DELIVERED);
        
        messageRepository.save(message);

        Dialog dialog = dialogRepository.findById(message.getDialog().getDialogId()).get();

        Hibernate.initialize(dialog.getUsers());

        MessageWithDialog messageDto = messageRepository.findMessageById(message.getId());

        rabbitTemplate.setExchange("message-exchange");

        JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(objectMapper.writeValueAsString(messageDto));
        jsonObject.put("type", "MESSAGE");




        jsonObject.put("client", client);

        if (message.getUniqueCode() != null) {
            jsonObject.put("uniqueCode", message.getUniqueCode());
        }

        for (User user :dialog.getUsers()) {

            simpMessagingTemplate.convertAndSend("/topic/" + user.getUsername(),
                    jsonObject);

            rabbitTemplate.convertAndSend(user.getUsername(),
                    jsonObject);

        }

    }


    /*public Long createGroup(Dialog newDialog, String username) throws JsonProcessingException, ParseException {

        User authUser = new User();

        authUser.setUsername(username);

        newDialog.addUser(authUser);

        if (newDialog.getUsers().stream().anyMatch(o -> o.getUsername()
                .equals(username))) {

            Dialog dialog = new Dialog();

            newDialog.getUsers().forEach(user -> dialog.addUser(userService.loadUserByUsername(user.getUsername())));

            Message message = new Message();
            message.setContent("Группа создана");
            message.setSender(userService.loadUserByUsername(username));
            message.setStatus(MessageStatus.DELIVERED);
            message.setTimestamp(new Date());
            message.setDialog(dialog);

            dialog.setMessages(new ArrayList<>());
            dialog.getMessages().add(message);



            if (newDialog.getName() == null || newDialog.getName().equals("")) {
                dialog.setName("Конференция");
            } else {
                dialog.setName(newDialog.getName());
            }

            //group.setCreator(userService.getAuthUser(SecurityContextHolder.getContext().getAuthentication()));
            dialog.setImage("https://" + hostname + "/img/default.jpg");
            dialogRepository.save(dialog);

            MessageWithDialog messageDto = messageRepository.findMessageById(message.getId());
            JSONObject jsonObject = (JSONObject) JSONValue
                    .parseWithException(objectMapper.writeValueAsString(messageDto));
            jsonObject.put("type", "MESSAGE");
            for (User user : dialog.getUsers()) {
                simpMessagingTemplate.convertAndSend("/topic/" + user.getUsername(),
                        jsonObject);
            }


            return dialog.getDialogId();

        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }*/

    public DialogWithUsersViewPaging getDialogWithPaging(String dialog_id, Integer page, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());

        dialogRepository.findById(Long.valueOf(dialog_id)).get().setPaging(paging);
        return dialogRepository.findAllDialogByDialogId(Long.valueOf(dialog_id));
    }
}
