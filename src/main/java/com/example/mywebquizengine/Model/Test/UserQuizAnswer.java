package com.example.mywebquizengine.Model.Test;

import javax.persistence.*;

@Entity(name = "USER_QUIZ_ANSWERS")
@Inheritance(
        strategy = InheritanceType.JOINED
)
public class UserQuizAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int quizAnswerId;

    private Boolean status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false, name = "quiz_id")
    //@Cascade(org.hibernate.annotations.CascadeType.DELETE)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private Quiz quiz;
    //private Test test;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "user_answer_id")
    private UserTestAnswer userAnswer;

    public UserQuizAnswer() {
    }

    public Boolean getStatus() {
        return status;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz id) {
        this.quiz = id;
    }

    public int getQuizAnswerId() {
        return quizAnswerId;
    }

    public void setQuizAnswerId(int id) {
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
