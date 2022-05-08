package com.example.mywebquizengine.Controller;


import com.example.mywebquizengine.Model.Chat.Message;
import com.example.mywebquizengine.Model.Projection.DialogWithUsersViewPaging;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Service.MessageService;
import com.example.mywebquizengine.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String home(Model model, @AuthenticationPrincipal User authUser) {
        User user = userService.loadUserByUserIdProxy(authUser.getUserId());
        model.addAttribute("myUsername", user);
        model.addAttribute("lastDialogs", messageService.getDialogsForApi(authUser.getUserId()));
        model.addAttribute("userList", userService.getUserList());
        return "chat2";
    }

    @PostMapping(path = "/checkdialog")
    @ResponseBody
    @PreAuthorize(value = "!#user.userId.equals(#authUser.userId)")
    public Long checkDialog(@RequestBody User user, @AuthenticationPrincipal User authUser) {

        return messageService.checkDialog(user.getUserId(), authUser.getUserId());

    }

    @GetMapping(path = "/chat/{dialog_id}")
    @Transactional
    public String chatWithUser2(Model model, @PathVariable String dialog_id,
                                @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
                                @RequestParam(required = false, defaultValue = "50") @Min(1) @Max(100) Integer pageSize,
                                @RequestParam(defaultValue = "timestamp") String sortBy,
                                @AuthenticationPrincipal User authUser) {

        DialogWithUsersViewPaging dialog = messageService.getDialogWithPaging(dialog_id, page, pageSize, sortBy);

        if (dialog.getUsers().stream().anyMatch(o -> o.getUserId()
                .equals(authUser.getUserId()))) {

            model.addAttribute("lastDialogs", messageService.getDialogsForApi(authUser.getUserId()));
            model.addAttribute("dialog", dialog.getDialogId());
            model.addAttribute("messages", dialog.getMessages());
            model.addAttribute("dialogObj", dialog);
            model.addAttribute("userList", userService.getUserList());

            return "chat2";

        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);


    }

    @GetMapping(path = "/chat/nextPages")
    @Transactional
    @ResponseBody
    public DialogWithUsersViewPaging chatWithUserPages(@RequestParam String dialog_id,
                                                       @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
                                                       @RequestParam(required = false, defaultValue = "50") @Min(1) @Max(100) Integer pageSize,
                                                       @RequestParam(defaultValue = "timestamp") String sortBy,
                                                       @AuthenticationPrincipal User authUser) {
        return messageService.getMessages(Long.valueOf(dialog_id), page, pageSize, sortBy, authUser.getUserId());
    }

    @Modifying
    @Transactional
    @MessageMapping("/user/{dialogId}")
    public void sendMessage(@Valid @Payload Message message,
                            @AuthenticationPrincipal Principal authUser
    ) throws JsonProcessingException, ParseException {
        messageService.sendMessage(message, "WEB");
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
