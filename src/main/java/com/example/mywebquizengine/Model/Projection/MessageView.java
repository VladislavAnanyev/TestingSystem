package com.example.mywebquizengine.Model.Projection;

import com.example.mywebquizengine.Model.Chat.MessageFile;

import java.util.Date;
import java.util.List;

public interface MessageView {
    Integer getId();
    String getContent();
    UserCommonView getSender();
    //ZonedDateTime getTimestamp();
    Date getTimestamp();
    List<MessageView> getForwardedMessages();

    List<MessageFile> getPhotos();
    //DialogWithUsersView getDialog();
}
