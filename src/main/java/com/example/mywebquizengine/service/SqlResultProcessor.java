package com.example.mywebquizengine.service;


import com.example.mywebquizengine.model.User;
import com.example.mywebquizengine.model.chat.Dialog;
import com.example.mywebquizengine.model.chat.Message;
import com.example.mywebquizengine.model.projection.MessageView;
import com.example.mywebquizengine.repos.DialogRepository;
import com.example.mywebquizengine.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SqlResultProcessor {

    @Autowired
    private DialogRepository dialogRepository;

    @Autowired
    private MessageRepository messageRepository;

    public String getCompanion(String name, Set<User> users) {
        if (name != null) {
            return name;
        } else {
            Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
            Set<User> userSet = new HashSet<>(users);
            userSet.removeIf(user -> user.getUserId().equals(userId));
            return userSet.iterator().next().getFirstName() + " " + userSet.iterator().next().getLastName();
        }
    }

    public String getCompanionAvatar(String image, Set<User> users) {
        if (image != null) {
            return image;
        } else {
            Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
            Set<User> userSet = new HashSet<>(users);
            userSet.removeIf(user -> user.getUserId().equals(userId));
            return userSet.iterator().next().getAvatar();
        }
    }

    public String getCompanionForLastDialogs(Long dialogId, String name) {
        if (name != null) {
            return name;
        } else {
            Dialog dialog = dialogRepository.findById(dialogId).get();
            Set<User> users = dialog.getUsers();
            Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
            Set<User> userSet = new HashSet<>(users);
            userSet.removeIf(user -> user.getUserId().equals(userId));
            return userSet.iterator().next().getFirstName() + " " + userSet.iterator().next().getLastName();
        }
    }

    public String getCompanionAvatarForLastDialogs(Long dialogId, String image) {
        if (image != null) {
            return image;
        } else {
            Dialog dialog = dialogRepository.findById(dialogId).get();
            Set<User> users = dialog.getUsers();
            Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
            Set<User> userSet = new HashSet<>(users);
            userSet.removeIf(user -> user.getUserId().equals(userId));
            return userSet.iterator().next().getAvatar();
        }
    }

    @Transactional
    public List<MessageView> getListOfMessages(Long dialogId, Pageable paging) {

        List<Message> messages = messageRepository
                .findAllByDialog_DialogId(dialogId, paging)
                .getContent();

        List<MessageView> messageViews = ProjectionUtil.parseToProjectionList(messages, MessageView.class);
        Collections.reverse(messageViews);

        return messageViews;
    }

}