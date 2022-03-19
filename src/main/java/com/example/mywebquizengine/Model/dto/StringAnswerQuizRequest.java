package com.example.mywebquizengine.Model.dto;

public class StringAnswerQuizRequest extends AddQuizRequest {
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
