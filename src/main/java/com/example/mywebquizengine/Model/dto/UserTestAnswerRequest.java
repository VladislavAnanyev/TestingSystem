package com.example.mywebquizengine.Model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserTestAnswerRequest {
    @NotNull
    private Long quizId;
    @NotNull
    private Object answer;

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Object getAnswer() {
        return answer;
    }

    public void setAnswer(Object answer) {
        this.answer = answer;
    }
}
