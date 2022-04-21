package com.example.mywebquizengine.Model.Projection;

import org.springframework.beans.factory.annotation.Value;

public interface UserViewForCourseInfo {
    Long getUserId();
    String getEmail();
    String getFirstName();
    String getLastName();
    String getAvatar();
    @Value("#{@userAnswerService.getPercentageOfComplete(target.courseId, target.userId)}")
    Integer getPercentageOfComplete();
}
