package com.example.mywebquizengine.Model;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortComparator;
import org.springframework.data.web.SortDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "USERS_PHOTOS")
public class Photo implements Comparable<Photo> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String url;

    @NotNull
    private Integer position;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public static List<Photo> getList(String url) {
        Photo photo = new Photo();
        photo.setUrl(url);
        List<Photo> photos = new ArrayList<>();
        photos.add(photo);
        return photos;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int compareTo(Photo photo) {
        return this.position.compareTo(photo.position);
    }
}
