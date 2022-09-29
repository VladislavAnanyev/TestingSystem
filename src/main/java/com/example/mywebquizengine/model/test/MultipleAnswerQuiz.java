package com.example.mywebquizengine.model.test;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name = "QUIZZES_MULTIPLE_TRUE_ANSWER")
//@OnDelete(action = OnDeleteAction.CASCADE)
public class MultipleAnswerQuiz extends Quiz {

    @ElementCollection
    @CollectionTable(name = "QUIZZES_MULTIPLE_OPTIONS", joinColumns=@JoinColumn(name = "QUIZ_ID"))
    @NotNull
    @NotEmpty
    @Size(min = 2)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "QUIZ_ID")
    private List<@NotEmpty String> options;

    @ElementCollection
    @CollectionTable(name = "QUIZZES_MULTIPLE_TRUE_ANSWERS", joinColumns=@JoinColumn(name = "QUIZ_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "QUIZ_ID")
    private List<Integer> answer;

    public List<String> getOptions() {
        return options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }
}
