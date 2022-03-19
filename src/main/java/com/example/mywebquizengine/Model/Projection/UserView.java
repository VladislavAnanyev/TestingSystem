package com.example.mywebquizengine.Model.Projection;

import com.example.mywebquizengine.Model.Role;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserView {
    String getUsername();
    String getFirstName();
    String getLastName();
    String getAvatar();
    String getEmail();
    List<Role> getRoles();
    boolean isStatus();
    String getOnline();
}
