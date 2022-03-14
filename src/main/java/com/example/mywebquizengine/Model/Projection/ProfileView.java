package com.example.mywebquizengine.Model.Projection;

import com.example.mywebquizengine.Model.Photo;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface ProfileView {
    String getUsername();
    String getFirstName();
    String getLastName();
    List<Photo> getPhotos();

}
