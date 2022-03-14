package com.example.mywebquizengine.Model.Projection;

import org.springframework.beans.factory.annotation.Value;

public interface UserCommonView {
    String getUsername();
    String getFirstName();
    String getLastName();

    @Value("#{target.photos.get(0).url}")
    String getAvatar();
}

