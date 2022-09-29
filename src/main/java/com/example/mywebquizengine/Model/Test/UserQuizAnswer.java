package com.example.mywebquizengine.Model.Test;

import javax.persistence.*;

@Entity(name = "USER_QUIZ_ANSWERS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class UserQuizAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long quizAnswerId;

    private Boolean status;

    @ManyToOne
    @JoinColumn(nullable = false, name = "quiz_id")
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_answer_id")
    private UserTestAnswer userAnswer;

    private Double duration;

    public UserQuizAnswer() {}

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public void addDuration(Double duration) {
        if (this.duration == null) {
            this.duration = 0.0;
        }
        this.duration += duration;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz id) {
        this.quiz = id;
    }

    public Long getQuizAnswerId() {
        return quizAnswerId;
    }

    public void setQuizAnswerId(Long id) {
        this.quizAnswerId = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean isStatus() {
        return status;
    }

    public UserTestAnswer getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(UserTestAnswer userTestAnswer) {
        this.userAnswer = userTestAnswer;
    }
}
