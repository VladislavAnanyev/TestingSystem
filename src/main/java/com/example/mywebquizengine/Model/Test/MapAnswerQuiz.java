package com.example.mywebquizengine.Model.Test;

import javax.persistence.*;
import java.util.Map;

@Entity(name = "QUIZZES_MAP_TRUE_ANSWER")
public class MapAnswerQuiz extends Quiz {

    @ElementCollection
    @CollectionTable(name = "QUIZZES_MAP_TRUE_ANSWERS")
    @MapKeyColumn(name = "answer_id")
    @Column(name = "answers")
    private Map<Long, String> answer;

    public Map<Long, String> getAnswer() {
        return answer;
    }

    public void setAnswer(Map<Long, String> answer) {
        this.answer = answer;
    }
}
