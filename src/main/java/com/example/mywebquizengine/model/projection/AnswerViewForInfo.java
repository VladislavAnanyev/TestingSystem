package com.example.mywebquizengine.model.projection;

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
    String getGroupName();
    Long getUserId();
}
