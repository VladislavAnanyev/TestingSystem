package com.example.mywebquizengine.Model.Projection;

import org.springframework.beans.factory.annotation.Value;


import java.time.Instant;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;


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
