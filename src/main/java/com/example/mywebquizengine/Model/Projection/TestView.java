package com.example.mywebquizengine.Model.Projection;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Date;
import java.time.LocalTime;


public interface TestView {
    Long getTestId();
    String getDescription();
    @Value("#{target.quizzes.size()}")
    Integer getNumberOfQuestions();
    LocalTime getDuration();
    @Value("#{@userAnswerService.checkComplete(target.testId)}")
    boolean isComplete();
    Integer getAttempts();
    Date getStartTime();
    Date getEndTime();
}
