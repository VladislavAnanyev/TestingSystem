package com.example.mywebquizengine.Model.Test;

import com.example.mywebquizengine.Model.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalTime;
import java.util.List;

@Entity(name = "TESTS")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    //@Fetch(FetchMode.SUBSELECT)
    //@Fetch(value = FetchMode)
    private List<@Valid Quiz> quizzes;

    @ManyToOne/*(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)*/
    @JoinColumn(name = "username")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    //@Fetch(FetchMode.SUBSELECT)
    private List<UserTestAnswer> answers;


    private String description;

    private LocalTime duration;

    public Test() {}

    public List<Quiz> getQuizzes() {
        //Hibernate.initialize(this);
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        /*
        Для корректной работы с orphanRemoval и обновлением строк в БД
        (Обработка ошибки: A collection with cascade=”all-delete-orphan”
         was no longer referenced by the owning entity instance)
         */
        if (this.quizzes == null) {
            this.quizzes = quizzes;
            return;
        } else {
            this.quizzes.clear();
        }
        if (quizzes != null) {
            this.quizzes.addAll(quizzes);
            this.quizzes.forEach((quiz) -> quiz.setTest(this));
        }
        //this.quizzes = quizzes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAnswers(List<UserTestAnswer> answers) {
        //this.answers = answers;
        /*
        Для корректной работы с orphanRemoval и обновлением строк в БД
        (Обработка ошибки: A collection with cascade=”all-delete-orphan”
         was no longer referenced by the owning entity instance)
         */
        if (this.answers == null) {
            this.answers = answers;
            return;
        } else {
            this.answers.clear();
        }
        if (answers != null) {
            this.answers.addAll(answers);
            //this.answers.forEach((userQuizAnswer) -> userQuizAnswer.setUserAnswer(this));
        }
    }

    public List<UserTestAnswer> getAnswers() {
        return answers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(LocalTime time) {
        this.duration = time;
    }

    public LocalTime getDuration() {
        return duration;
    }
}
