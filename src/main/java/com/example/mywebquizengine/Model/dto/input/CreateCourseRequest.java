package com.example.mywebquizengine.Model.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateCourseRequest {
    @NotNull
    @NotBlank
    private String courseName;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
