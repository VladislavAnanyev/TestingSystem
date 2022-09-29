package com.example.mywebquizengine.model.projection.api;

import com.example.mywebquizengine.model.projection.UserCommonView;

import java.util.Date;

public interface MessageWithDialog {
    Integer getMessageId();
    String getContent();
    UserCommonView getSender();
    Date getTimestamp();
    DialogWithoutMessages getDialog();
    //Long getDialogId();
}


