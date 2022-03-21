package com.example.mywebquizengine.Model.Test;

import com.example.mywebquizengine.Model.Course;
import com.example.mywebquizengine.Model.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;

@Entity(name = "TESTS")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long testId;

    //@OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<@Valid Quiz> quizzes;

    @ManyToOne/*(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)*/
    @JoinColumn(name = "username")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    //@OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTestAnswer> userTestAnswers;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "COURSE_ID")
    private Course course;

    private String description;

    private LocalTime duration;

    public Test() {}

    public List<Quiz> getQuizzes() {
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

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long id) {
        this.testId = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserTestAnswers(List<UserTestAnswer> answers) {
        //this.answers = answers;
        /*
        Для корректной работы с orphanRemoval и обновлением строк в БД
        (Обработка ошибки: A collection with cascade=”all-delete-orphan”
         was no longer referenced by the owning entity instance)
         */
        if (this.userTestAnswers == null) {
            this.userTestAnswers = answers;
            return;
        } else {
            this.userTestAnswers.clear();
        }
        if (answers != null) {
            this.userTestAnswers.addAll(answers);
            //this.answers.forEach((userQuizAnswer) -> userQuizAnswer.setUserAnswer(this));
        }
    }

    public List<UserTestAnswer> getUserTestAnswers() {
        return userTestAnswers;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}