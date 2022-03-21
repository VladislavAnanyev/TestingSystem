package com.example.mywebquizengine.Model.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;

@Entity(name = "QUIZZES_STRING_TRUE_ANSWERS")
//@OnDelete(action = OnDeleteAction.CASCADE)
public class StringAnswerQuiz extends Quiz {

    //@ElementCollection
    //@CollectionTable
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
