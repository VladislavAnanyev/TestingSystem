package com.example.mywebquizengine.Model.Test;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity(name = "USER_QUIZ_ANSWERS")
public class UserQuizAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int quizAnswerId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable
    private List<Integer> answer;

    private Boolean status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false/*, cascade = CascadeType.ALL*/)
    @JoinColumn(nullable = false, name = "quiz_id")
    //@Cascade(org.hibernate.annotations.CascadeType.DELETE)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private Quiz quiz;
    //private Test test;

    @ManyToOne(fetch = FetchType.LAZY, optional = false/*, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH}*/)
    @JoinColumn(nullable = false, name = "user_answer_id")
    private UserTestAnswer userAnswer;



    UserQuizAnswer(ArrayList<Integer> answer){
        this.answer = answer;
    }

    public UserQuizAnswer(){}

    public Boolean getStatus() {
        return status;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public void setQuiz(Quiz id){
        this.quiz = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }


    public void setQuizAnswerId(int id) {
        this.quizAnswerId = id;
    }

    public int getQuizAnswerId() {
        return quizAnswerId;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    public void setUserAnswer(UserTestAnswer userTestAnswer) {
        this.userAnswer = userTestAnswer;
    }

    public UserTestAnswer getUserAnswer() {
        return userAnswer;
    }
}
