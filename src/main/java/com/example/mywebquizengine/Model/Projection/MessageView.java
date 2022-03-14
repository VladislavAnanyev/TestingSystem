package com.example.mywebquizengine.Model.Projection;

import com.example.mywebquizengine.Model.Chat.MessagePhoto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public interface MessageView {
    Integer getId();
    String getContent();
    UserCommonView getSender();
    //ZonedDateTime getTimestamp();
    Date getTimestamp();
    List<MessageView> getForwardedMessages();

    List<MessagePhoto> getPhotos();
    //DialogWithUsersView getDialog();
}
