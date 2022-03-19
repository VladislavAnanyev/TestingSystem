package com.example.mywebquizengine.Model.dto;

import java.time.LocalTime;
import java.util.List;

public class CreateTestRequest {
    private String description;
    private LocalTime duration;
    private List<Object> quizzes;
    private Long courseId;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getDescription() {
        return description;
    }
/*
    public List<AddQuizRequest> getQuizzes() {
        return quizzes;
    }*/

    public LocalTime getDuration() {
        return duration;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }
/*
    public void setQuizzes(List<AddQuizRequest> quizzes) {
        this.quizzes = quizzes;
    }*/

    public List<Object> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Object> quizzes) {
        this.quizzes = quizzes;
    }
}
