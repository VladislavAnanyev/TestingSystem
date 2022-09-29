package com.example.mywebquizengine.model.projection.api;

import com.example.mywebquizengine.model.projection.UserCommonView;

import java.util.Set;

public interface DialogWithoutMessages {
    Long getDialogId();
    String getName();
    String getImage();
    Set<UserCommonView> getUsers();
}
