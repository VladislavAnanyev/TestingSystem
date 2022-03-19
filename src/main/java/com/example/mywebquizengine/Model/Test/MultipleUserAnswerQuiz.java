package com.example.mywebquizengine.Model.Test;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.List;

@Entity
public class MultipleUserAnswerQuiz extends UserQuizAnswer {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable
    private List<Integer> answer;

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }
}
