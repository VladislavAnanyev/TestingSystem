package com.example.mywebquizengine.Model.Projection.Api;

import com.example.mywebquizengine.Model.Projection.UserCommonView;

import java.util.Set;

public interface DialogWithoutMessages {
    Long getDialogId();
    String getName();
    String getImage();
    Set<UserCommonView> getUsers();
}
