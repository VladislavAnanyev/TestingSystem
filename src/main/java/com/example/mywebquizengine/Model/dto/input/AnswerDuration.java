package com.example.mywebquizengine.Model.dto.input;

public class AnswerDuration {
    private Long answerSessionId;
    private Long quizId;
    private Double duration;

    public Long getAnswerSessionId() {
        return answerSessionId;
    }

    public void setAnswerSessionId(Long answerSessionId) {
        this.answerSessionId = answerSessionId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }
}
