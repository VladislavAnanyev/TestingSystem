package com.example.mywebquizengine.Model.Test;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class MultipleAnswerQuiz extends Quiz {

    @ElementCollection
    @CollectionTable
    @NotNull
    @NotEmpty
    @Size(min = 2)
    private List<@NotEmpty String> options;

    @ElementCollection
    @CollectionTable
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Long> answer;

    public List<String> getOptions() {
        return options;
    }

    public List<Long> getAnswer() {
        return answer;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setAnswer(List<Long> answer) {
        this.answer = answer;
    }
}
