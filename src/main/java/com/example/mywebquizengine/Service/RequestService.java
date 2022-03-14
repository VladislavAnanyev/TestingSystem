package com.example.mywebquizengine.Service;

import com.example.mywebquizengine.Model.Chat.Dialog;
import com.example.mywebquizengine.Model.Chat.MessageStatus;
import com.example.mywebquizengine.Model.Projection.ReceivedRequestView;
import com.example.mywebquizengine.Model.Projection.SentRequestView;
import com.example.mywebquizengine.Model.Request;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Repos.DialogRepository;
import com.example.mywebquizengine.Repos.RequestRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    private MessageService messageService;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private DialogRepository dialogRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    public void sendRequest(Request request, Principal principal) throws JsonProcessingException, ParseException {
        request.setSender(userService.loadUserByUsername(principal.getName()));
        request.setStatus("PENDING");
        Long dialogId = messageService.checkDialog(request.getTo().getUsername(), principal.getName());


        Dialog dialog = new Dialog();
        dialog.setDialogId(dialogId);

        if (request.getMessage() != null) {

            request.getMessage().setDialog(dialog);
            request.getMessage().setSender(userService.loadUserByUsernameProxy(principal.getName()));
            request.getMessage().setStatus(MessageStatus.DELIVERED);
            request.getMessage().setTimestamp(new Date());

        }

        requestRepository.save(request);

        ReceivedRequestView requestView = requestRepository.findRequestById(request.getId());

        JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(objectMapper
                .writeValueAsString(requestView));
        jsonObject.put("type", "REQUEST");

        rabbitTemplate.convertAndSend(request.getTo().getUsername(),
                jsonObject);

        simpMessagingTemplate.convertAndSend("/topic/" + request.getTo().getUsername(),
                jsonObject);
    }

    public List<SentRequestView> getSentRequests(String username) {
        return requestRepository.findAllBySenderUsernameAndStatus(username, "PENDING");
    }


    public void rejectRequest(Long id, String username) {
        Optional<Request> optionalRequest = requestRepository.findById(id);
        if (optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
            if (request.getTo().getUsername().equals(username)) {
                requestRepository.updateStatus(request.getId(), "REJECTED");
            } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    public ArrayList<ReceivedRequestView> getMyRequests(String username) {
        return requestRepository.findAllByToUsernameAndStatus(username, "PENDING");
    }

    public Long acceptRequest(Long requestId, String username) {
        User authUser = userService.loadUserByUsername(username);


        Request request = requestRepository.findById(requestId).get();
        request.setStatus("ACCEPTED");

        authUser.addFriend(request.getSender());
        requestRepository.save(request);
        Long dialog_id = messageService.checkDialog(request.getSender().getUsername(), username);

        if (dialog_id == null) {
            Dialog dialog = new Dialog();
            //  Set<User> users = new HashSet<>();
            dialog.addUser(userService.loadUserByUsername(request.getSender().getUsername()));
            dialog.addUser(authUser);

            dialogRepository.save(dialog);
            return dialog.getDialogId();
        } else {
            return dialog_id;
        }
    }

    public ArrayList<ReceivedRequestView> findAllMyRequestsViaStatus(String name, String status) {
        return requestRepository.findAllByToUsernameAndStatus(name, status);
    }
}
