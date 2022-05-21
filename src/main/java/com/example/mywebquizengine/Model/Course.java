package com.example.mywebquizengine.Model;

import com.example.mywebquizengine.Model.Test.Test;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity(name = "COURSES")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long courseId;
    private String name;
    @ManyToOne(optional = false)
    private User owner;
    @ManyToMany(mappedBy = "courses", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> members = new HashSet<>();
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<Test> tests;

    @NotNull
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Long getCourseId() {
        return courseId;
    }

    public List<Test> getTests() {
        return tests;
    }

    public Set<User> getMembers() {
        return members;
    }

    public User getOwner() {
        return owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourseId(Long id) {
        this.courseId = id;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public void addMember(User user) {
        this.members.add(user);
        user.getCourses().add(this);
    }

    public void removeMember(User user) {
        this.members.remove(user);
        user.getCourses().remove(this);
    }

}
