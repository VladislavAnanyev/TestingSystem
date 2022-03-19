package com.example.mywebquizengine.Model.dto;

public class UserTestAnswerRequest {
    private Long quizId;
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
