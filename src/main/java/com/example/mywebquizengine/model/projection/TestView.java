package com.example.mywebquizengine.model.projection;

import org.springframework.beans.factory.annotation.Value;


import java.time.LocalTime;
import java.util.Calendar;


public interface TestView {
    Long getTestId();
    String getDescription();
    @Value("#{target.quizzes.size()}")
    Integer getNumberOfQuestions();
    LocalTime getDuration();
    @Value("#{@userAnswerService.checkComplete(target.testId)}")
    boolean isComplete();
    Integer getAttempts();
    Calendar getStartTime();
    Calendar getEndTime();
}
