package com.example.mywebquizengine.Model.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity(name = "USER_QUIZ_MULTIPLE_ANSWER")
//@OnDelete(action = OnDeleteAction.CASCADE)
public class MultipleUserAnswerQuiz extends UserQuizAnswer {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "USER_QUIZ_MULTIPLE_ANSWERS")
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "QUIZ_ANSWER_ID")
    private List<Integer> answer;

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public void removeAll() {
        for (Integer integer : answer) {
            answer.remove(integer);
        }
    }
}
