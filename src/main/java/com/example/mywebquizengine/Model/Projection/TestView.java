package com.example.mywebquizengine.Model.Projection;

import org.springframework.beans.factory.annotation.Value;

public interface TestView {
    Long getTestId();
    String getDescription();
    @Value("#{target.quizzes.size()}")
    Integer getNumberOfQuestions();
    @Value("#{target.user.username}")
    String getAuthor();
}
