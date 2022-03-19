package com.example.mywebquizengine.Model.Test;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

@Entity
public class StringUserAnswerQuiz extends UserQuizAnswer {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
