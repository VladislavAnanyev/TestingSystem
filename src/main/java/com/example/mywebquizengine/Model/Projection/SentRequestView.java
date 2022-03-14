package com.example.mywebquizengine.Model.Projection;

public interface SentRequestView {
    Long getId();

    //UserCommonView getSender();

    UserCommonView getTo();

    String getStatus();

    MeetingCommonView getMeeting();

    MessageView getMessage();
}
