package com.example.mywebquizengine.Controller;


import com.example.mywebquizengine.Model.Chat.Dialog;
import com.example.mywebquizengine.Model.Chat.Message;
import com.example.mywebquizengine.Model.Chat.MessageStatus;
import com.example.mywebquizengine.Model.Projection.DialogWithUsersViewPaging;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Repos.DialogRepository;
import com.example.mywebquizengine.Repos.MessageRepository;
import com.example.mywebquizengine.Service.MessageService;
import com.example.mywebquizengine.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.Date;


@Controller
@EnableRabbit
@Component
@Validated
public class ChatController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;


    @GetMapping(path = "/chat")
    public String chat(Model model, @AuthenticationPrincipal Principal principal) {

        User user = userService.loadUserByUsernameProxy(principal.getName());
        model.addAttribute("myUsername", user);
        model.addAttribute("lastDialogs", messageService.getDialogsForApi(principal.getName()));
        model.addAttribute("userList", userService.getUserList());
        return "chat";
    }

    @PostMapping(path = "/checkdialog")
    @ResponseBody
    @PreAuthorize(value = "!#principal.name.equals(#user.username)")
    public Long checkDialog(@RequestBody User user, @AuthenticationPrincipal Principal principal) {

        return messageService.checkDialog(user.getUsername(), principal.getName());

    }

    @GetMapping(path = "/chat/{dialog_id}")
    @Transactional
    public String chatWithUser(Model model, @PathVariable String dialog_id,
                               @RequestParam(required = false,defaultValue = "0") @Min(0) Integer page,
                               @RequestParam(required = false,defaultValue = "50") @Min(1) @Max(100) Integer pageSize,
                               @RequestParam(defaultValue = "timestamp") String sortBy,
                               @AuthenticationPrincipal Principal principal) {


            DialogWithUsersViewPaging dialog = messageService.getDialogWithPaging(dialog_id, page, pageSize, sortBy);

            if (dialog.getUsers().stream().anyMatch(o -> o.getUsername()
                    .equals(principal.getName()))) {

                model.addAttribute("lastDialogs", messageService.getDialogsForApi(principal.getName()));
                model.addAttribute("dialog", dialog.getDialogId());
                model.addAttribute("messages", dialog.getMessages());
                model.addAttribute("dialogObj", dialog);
                model.addAttribute("userList", userService.getUserList());

                return "chat";

            } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);



    }

    @GetMapping(path = "/chat/nextPages")
    @Transactional
    @ResponseBody
    public DialogWithUsersViewPaging chatWithUserPages(@RequestParam String dialog_id,
                               @RequestParam(required = false,defaultValue = "0") @Min(0) Integer page,
                               @RequestParam(required = false,defaultValue = "50") @Min(1) @Max(100) Integer pageSize,
                               @RequestParam(defaultValue = "timestamp") String sortBy,
                               @AuthenticationPrincipal Principal principal) {
        return messageService.getMessages(Long.valueOf(dialog_id), page, pageSize, sortBy, principal.getName());
    }

    @PostMapping(path = "/createGroup")
    @ResponseBody
    public Long createGroup(@Valid @RequestBody Dialog newDialog,
                            @AuthenticationPrincipal Principal principal
    ) throws JsonProcessingException, ParseException {
        return messageService.createGroup(newDialog, principal.getName());
    }



    @Modifying
    @Transactional
    @MessageMapping("/user/{dialogId}")
    public void sendMessage(@Valid @Payload Message message,
                            @AuthenticationPrincipal Principal principal
    ) throws JsonProcessingException, ParseException {
        if (message.getSender().getUsername().equals(principal.getName())) {
            messageService.sendMessage(message, "WEB");
        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }




    @Modifying
    @Transactional
    @RabbitListener(queues = "incoming-messages")
    public void getMessageFromAndroid(@Valid Message message) throws JsonProcessingException, ParseException {
        messageService.sendMessage(message, "ANDROID");
    }


    @GetMapping(path = "/error")
    public String handleError() {
        return "error";
    }




}
