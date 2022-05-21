package com.example.mywebquizengine.Model.Test;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;

@Entity(name = "USER_QUIZ_MAP_ANSWER")
public class MapUserAnswerQuiz extends UserQuizAnswer {

    @ElementCollection
    @CollectionTable(name = "USER_QUIZ_MAP_ANSWERS", joinColumns=@JoinColumn(name = "QUIZ_ID"))
    @MapKeyColumn(name = "answer_key")
    @Column(name = "answer_value")
    private Map<String, String> answer;

    public Map<String, String> getAnswer() {
        return answer;
    }

    public void setAnswer(Map<String, String> answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapUserAnswerQuiz that = (MapUserAnswerQuiz) o;
        return answer.equals(that.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answer);
    }
}
