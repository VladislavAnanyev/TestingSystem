package com.example.mywebquizengine.model.test;

import javax.persistence.*;

@Entity(name = "USER_QUIZ_ANSWERS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class UserQuizAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long quizAnswerId;

    private Boolean status;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "quiz_id")
    private Quiz quiz;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "user_answer_id")
    private UserTestAnswer userAnswer;

    private Double duration;

    public UserQuizAnswer() {}

    public void updateAnswer(Object object) {
        throw new UnsupportedOperationException("Поддерживается только в классах наследниках");
    }

    public void check() {
        throw new UnsupportedOperationException("Поддерживается только в классах наследниках");
    }

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
