package com.example.mywebquizengine.Model;

import com.example.mywebquizengine.Model.Test.Test;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity(name = "COURSES")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long courseId;
    private String name;
    @ManyToOne
    private User owner;
    @OneToMany
    private List<User> members;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Test> tests;

    public String getName() {
        return name;
    }

    public Long getCourseId() {
        return courseId;
    }

    public List<Test> getTests() {
        return tests;
    }

    public List<User> getMembers() {
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

    public void setMembers(List<User> members) {
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
       // user.getC.add(this);
    }

}
