package com.example.mywebquizengine.Model.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
//S в конце
@Entity(name = "USER_QUIZ_STRING_ANSWERS")
public class StringUserAnswerQuiz extends UserQuizAnswer {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
