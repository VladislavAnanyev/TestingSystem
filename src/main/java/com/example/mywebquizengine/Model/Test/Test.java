package com.example.mywebquizengine.Model.Test;

import com.example.mywebquizengine.Model.Course;
import com.example.mywebquizengine.Model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

@Entity(name = "TESTS")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long testId;

    //@OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private List<@Valid Quiz> quizzes;

    //@OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private List<UserTestAnswer> userTestAnswers;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "COURSE_ID")
    private Course course;

    private Calendar startTime;

    private Calendar endTime;

    private Integer attempts;

    private String description;

    private LocalTime duration;

    @ColumnDefault("true")
    private boolean displayAnswers;

    public Test() {}

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

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

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long id) {
        this.testId = id;
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

    public boolean isDisplayAnswers() {
        return displayAnswers;
    }

    public void setDisplayAnswers(boolean displayAnswers) {
        this.displayAnswers = displayAnswers;
    }
}
