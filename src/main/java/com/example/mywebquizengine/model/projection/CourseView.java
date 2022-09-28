package com.example.mywebquizengine.model.projection;

import org.springframework.beans.factory.annotation.Value;

public interface CourseView {
    Long getCourseId();
    String getName();
    String getImage();
    Long getOwnerUserId();
    @Value("#{@userAnswerService.getPercentageOfComplete(target.courseId, null)}")
    Integer getPercentageOfComplete();
}
