package com.example.mywebquizengine.model.test;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

/*S в конце*/
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
