package com.example.mywebquizengine.model.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserTestAnswerView {
    @Value("#{target.user.firstName}")
    String getFirstName();
    @Value("#{target.user.lastName}")
    String getLastName();
    @Value("#{target.user.userId}")
    Long getUserId();
    Double getPercent();
    List<UserQuizAnswerView> getUserQuizAnswers();
}
