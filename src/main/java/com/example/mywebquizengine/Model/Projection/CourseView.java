package com.example.mywebquizengine.Model.Projection;

import org.springframework.beans.factory.annotation.Value;

public interface CourseView {
    Long getCourseId();
    String getName();
    String getOwnerEmail();
    @Value("#{@userAnswerService.getPercentageOfComplete(target.courseId)}")
    Integer getPercentageOfComplete();
}
