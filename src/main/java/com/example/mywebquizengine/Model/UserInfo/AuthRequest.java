package com.example.mywebquizengine.Model.UserInfo;

import javax.validation.constraints.Size;

public class AuthRequest {
    private String username;

    private String password;
    // геттеры сеттеры


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
