package com.example.mywebquizengine.Model.Test;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity(name = "USER_QUIZ_ANSWERS")
@Inheritance(
        strategy = InheritanceType.JOINED
)
public class UserQuizAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long quizAnswerId;

    private Boolean status;

    @ManyToOne
    @JoinColumn(nullable = false, name = "quiz_id")
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "user_answer_id")
    private UserTestAnswer userAnswer;

    public UserQuizAnswer() {}

    public Boolean getStatus() {
        return status;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public UserTestAnswer getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(UserTestAnswer userTestAnswer) {
        this.userAnswer = userTestAnswer;
    }
}
