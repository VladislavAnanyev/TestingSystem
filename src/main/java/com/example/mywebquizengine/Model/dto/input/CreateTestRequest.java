package com.example.mywebquizengine.Model.dto.input;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

public class CreateTestRequest {
    private String description;
    private LocalTime duration;
    private List<Object> quizzes;
    private Long courseId;
    private Integer attempts;
    private Calendar startAt;
    private Calendar finishAt;
    private boolean displayAnswers;

    public Calendar getFinishAt() {
        return finishAt;
    }

    public Calendar getStartAt() {
        return startAt;
    }

    public void setFinishAt(Calendar finishAt) {
        this.finishAt = finishAt;
    }

    public void setStartAt(Calendar startAt) {
        this.startAt = startAt;
    }

    public boolean isDisplayAnswers() {
        return displayAnswers;
    }

    public void setDisplayAnswers(boolean displayAnswers) {
        this.displayAnswers = displayAnswers;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public List<Object> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Object> quizzes) {
        this.quizzes = quizzes;
    }
}
