package com.example.mywebquizengine.Model.Chat;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
public class MessagePhoto {
    private String photo_url;

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
}
