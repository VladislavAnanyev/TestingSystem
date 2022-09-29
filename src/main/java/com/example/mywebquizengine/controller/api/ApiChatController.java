package com.example.mywebquizengine.controller.api;

import com.example.mywebquizengine.model.User;
import com.example.mywebquizengine.model.chat.Message;
import com.example.mywebquizengine.model.projection.DialogWithUsersViewPaging;
import com.example.mywebquizengine.model.projection.api.LastDialog;
import com.example.mywebquizengine.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/dialog/{id}")
    public DialogWithUsersViewPaging getMessages(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "50") Integer pageSize,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @AuthenticationPrincipal User authUser) {
        return messageService.getMessages(id, page, pageSize, sortBy, authUser.getUserId());
    }

    @GetMapping(path = "/dialogs")
    public ArrayList<LastDialog> getDialogs(@AuthenticationPrincipal User authUser) {
        return messageService.getDialogsForApi(authUser.getUserId());
    }

    @PostMapping(path = "/dialog/create")
    public Long checkDialog(@RequestParam Long userId, @AuthenticationPrincipal User authUser) {
        return messageService.checkDialog(userId, authUser.getUserId());
    }
}
