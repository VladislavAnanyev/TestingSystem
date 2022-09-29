package com.example.mywebquizengine.Model.dto.input;

import java.util.Map;

public class MapAnswerQuizRequest extends AddQuizRequest {
    private Map<String, String> answer;

    public Map<String, String> getAnswer() {
        return answer;
    }

    public void setAnswer(Map<String, String> answer) {
        this.answer = answer;
    }
}
