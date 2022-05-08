package com.example.mywebquizengine.Model.Test;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "QUIZZES")
@Inheritance(
        strategy = InheritanceType.SINGLE_TABLE
)
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "QUIZ_ID")
    private Long quizId;

    @NotBlank
    @NotNull
    private String title;

    @NotBlank
    @NotNull
    private String text;

    private String fileUrl;

    @ManyToOne(optional = false)
    @JoinColumn(name = "test_id")
    private Test test;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserQuizAnswer> userQuizAnswers;

    public Quiz() {}

    Quiz(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String image) {
        this.fileUrl = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long id) {
        this.quizId = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Test getTest() {
        return test;
    }

    public List<UserQuizAnswer> getUserQuizAnswers() {
        return userQuizAnswers;
    }

    public void setUserQuizAnswers(List<UserQuizAnswer> answers) {
        this.userQuizAnswers = answers;
    }
}
