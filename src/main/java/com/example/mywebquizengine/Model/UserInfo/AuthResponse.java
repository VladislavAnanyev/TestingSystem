package com.example.mywebquizengine.Model.UserInfo;

public class AuthResponse {
    private String jwtToken;

    public AuthResponse(String jwt) {
        this.jwtToken = jwt;
    }
    // геттер и сеттер


    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
