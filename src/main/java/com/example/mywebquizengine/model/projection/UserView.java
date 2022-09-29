package com.example.mywebquizengine.model.projection;

import com.example.mywebquizengine.model.Role;

public interface UserView {
    Long getUserId();
    String getEmail();
    String getFirstName();
    String getLastName();
    String getAvatar();
    Role getRole();
}
