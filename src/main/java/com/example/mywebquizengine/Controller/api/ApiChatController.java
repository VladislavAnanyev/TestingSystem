package com.example.mywebquizengine.Controller.api;

import com.example.mywebquizengine.Model.Chat.Message;
import com.example.mywebquizengine.Model.Projection.Api.MessageForApiViewCustomQuery;
import com.example.mywebquizengine.Model.Projection.DialogWithUsersViewPaging;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping(path = "/api")
public class ApiChatController {

    @Autowired
    private MessageService messageService;

    @DeleteMapping(path = "/message/{id}")
    public void deleteMessage(@PathVariable Long id, @AuthenticationPrincipal User authUser
    ) throws JsonProcessingException, ParseException {
        messageService.deleteMessage(id, authUser.getUserId());
    }

    @PutMapping(path = "/message/{id}")
    public void editMessage(@RequestBody Message message,
                            @AuthenticationPrincipal User authUser
    ) throws JsonProcessingException, ParseException {
        messageService.editMessage(message, authUser.getUserId());
    }

    @GetMapping(path = "/messages")
    public DialogWithUsersViewPaging getMessages(
            @RequestParam Long dialogId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "50") Integer pageSize,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @AuthenticationPrincipal User authUser) {
        return messageService.getMessages(dialogId, page, pageSize, sortBy, authUser.getUserId());
    }

    @GetMapping(path = "/dialogs")
    public ArrayList<MessageForApiViewCustomQuery> getDialogs(@AuthenticationPrincipal User authUser) {
        return messageService.getDialogsForApi(authUser.getUserId());
    }

    @GetMapping(path = "/getDialogId")
    public Long checkDialog(@RequestParam Long userId, @AuthenticationPrincipal User authUser) {
        return messageService.checkDialog(userId, authUser.getUserId());
    }
}
