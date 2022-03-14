package com.example.mywebquizengine.Model.Geo;

import com.example.mywebquizengine.Model.User;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

@Entity(name = "GEOLOCATIONS")
public class Geolocation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    //@JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "username")
    private User user;

    private Double lat;

    private Double lng;


    //@ColumnDefault(value = "CURRENT_TIMESTAMP()")
    /*@Column(
            columnDefinition = "DATETIME(6) DEFAULT CURRENT_TIMESTAMP"
    )
    @Generated(GenerationTime.INSERT)*/
    private Date time;

    public Geolocation() {}

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date calendar) {
        this.time = calendar;
    }

    @Override
    public String toString() {
        return "Geolocation{" +
                "id='" + id + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}
