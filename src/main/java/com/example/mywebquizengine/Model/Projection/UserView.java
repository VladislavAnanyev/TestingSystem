package com.example.mywebquizengine.Model.Projection;

import com.example.mywebquizengine.Model.Role;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserView {
    Long getUserId();
    String getEmail();
    String getFirstName();
    String getLastName();
    String getAvatar();
    Role getRole();
}
