package com.example.mywebquizengine.Model.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name = "QUIZZES_MULTIPLE_TRUE_ANSWER")
//@OnDelete(action = OnDeleteAction.CASCADE)
public class MultipleAnswerQuiz extends Quiz {

    @ElementCollection
    @CollectionTable(name = "QUIZZES_MULTIPLE_OPTIONS")
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
