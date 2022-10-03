package com.example.mywebquizengine.model.test;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity(name = "QUIZZES_STRING_TRUE_ANSWER")
//@OnDelete(action = OnDeleteAction.CASCADE)
public class StringAnswerQuiz extends Quiz {

    @ElementCollection
    @CollectionTable(name = "QUIZZES_STRING_TRUE_ANSWERS", joinColumns=@JoinColumn(name = "QUIZ_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<String> answer;

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

}
