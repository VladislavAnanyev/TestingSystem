package com.example.mywebquizengine.Model.Test;

import javax.persistence.*;
import java.util.Map;

@Entity(name = "QUIZZES_MAP_TRUE_ANSWER")
public class MapAnswerQuiz extends Quiz {

    @ElementCollection
    @CollectionTable(name = "QUIZZES_MAP_TRUE_ANSWERS", joinColumns=@JoinColumn(name = "QUIZ_ID"))
    @MapKeyColumn(name = "answer_id")
    @Column(name = "answers")
    private Map<String, String> answer;

    public Map<String, String> getAnswer() {
        return answer;
    }

    public void setAnswer(Map<String, String> answer) {
        this.answer = answer;
    }
}
