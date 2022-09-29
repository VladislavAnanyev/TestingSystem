package com.example.mywebquizengine.model.dto.input;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

public class CreateTestRequest {
    private String description;
    private LocalTime duration;
    private List<AddQuizRequest> quizzes;
    private Long courseId;
    private Integer attempts;
    private Calendar startAt;
    private Calendar finishAt;
    private boolean displayAnswers;
    private Integer percent;

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public Calendar getStartAt() {
        return startAt;
    }

    public Calendar getFinishAt() {
        return finishAt;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public void setFinishAt(Calendar finishAt) {
        this.finishAt = finishAt;
    }

    public void setStartAt(Calendar startAt) {
        this.startAt = startAt;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public boolean isDisplayAnswers() {
        return displayAnswers;
    }

    public void setDisplayAnswers(boolean displayAnswers) {
        this.displayAnswers = displayAnswers;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AddQuizRequest> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<AddQuizRequest> quizzes) {
        this.quizzes = quizzes;
    }
}
