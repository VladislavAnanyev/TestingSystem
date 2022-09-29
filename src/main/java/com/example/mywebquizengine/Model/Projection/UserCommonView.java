package com.example.mywebquizengine.Model.Projection;


import org.springframework.beans.factory.annotation.Value;

public interface UserCommonView {
    Long getUserId();
    @Value("#{target.email}")
    String getUsername();
    String getFirstName();
    String getLastName();
    String getAvatar();
    String getEmail();
}

