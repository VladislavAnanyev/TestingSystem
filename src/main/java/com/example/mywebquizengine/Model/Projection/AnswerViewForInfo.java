package com.example.mywebquizengine.Model.Projection;

import java.util.Calendar;
import java.util.Date;

public interface AnswerViewForInfo {
    String getStatus();
    Long getUserAnswerId();
    Date getCompletedAt();
    Date getStartAt();
    Double getPercent();
    String getFirstName();
    String getLastName();
    String getEmail();
    Long getUserId();
}
