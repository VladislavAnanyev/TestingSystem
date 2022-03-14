package com.example.mywebquizengine.Model.Test;

import com.example.mywebquizengine.Model.User;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity(name = "USER_TEST_ANSWERS")
public class UserTestAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int userAnswerId;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @OneToMany(mappedBy = "userAnswer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    //@Fetch(FetchMode.SUBSELECT)
    private List<UserQuizAnswer> userQuizAnswers;

    @ManyToOne
    @JoinColumn(nullable = false, name = "test_id")
    private Test test;

    private Double percent;

    public Double getPercent() {
        return percent;
    }

    private Calendar startAt;

    private Calendar completedAt;

    public Calendar getStartAt() {
        return startAt;
    }

    public void setStartAt(Calendar startAt) {
        this.startAt = startAt;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserAnswerId() {
        return userAnswerId;
    }

    public void setUserAnswerId(int userAnswerId) {
        this.userAnswerId = userAnswerId;
    }

    public List<UserQuizAnswer> getUserQuizAnswers() {
        return userQuizAnswers;
    }

    public void setUserQuizAnswers(List<UserQuizAnswer> userQuizAnswers) {
        /*
        Для корректной работы с orphanRemoval и обновлением строк в БД
        (Обработка ошибки: A collection with cascade=”all-delete-orphan”
         was no longer referenced by the owning entity instance)
         */
        if (this.userQuizAnswers == null) {
            this.userQuizAnswers = userQuizAnswers;
            return;
        } else {
            this.userQuizAnswers.clear();
        }
        if (userQuizAnswers != null) {
            this.userQuizAnswers.addAll(userQuizAnswers);
            this.userQuizAnswers.forEach((userQuizAnswer) -> userQuizAnswer.setUserAnswer(this));
        }

    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Test getTest() {
        return test;
    }

    public Calendar getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Calendar data) {
        this.completedAt = data;
    }


}
