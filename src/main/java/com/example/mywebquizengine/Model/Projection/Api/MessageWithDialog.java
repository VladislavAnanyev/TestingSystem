package com.example.mywebquizengine.Model.Projection.Api;

import com.example.mywebquizengine.Model.Projection.UserCommonView;

import java.util.Date;

public interface MessageWithDialog {
    Integer getMessageId();
    String getContent();
    UserCommonView getSender();
    Date getTimestamp();
    DialogWithoutMessages getDialog();
    //Long getDialogId();
}


