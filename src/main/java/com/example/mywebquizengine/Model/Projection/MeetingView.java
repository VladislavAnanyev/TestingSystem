package com.example.mywebquizengine.Model.Projection;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public interface MeetingView {

    Long getId();

    UserCommonView getFirstUser();

    UserCommonView getSecondUser();

    Double getLng();
    Double getLat();

    Date getTime();

}
