package com.example.mywebquizengine.Model.dto;

import java.util.List;

public class MultipleAnswerQuizRequest extends AddQuizRequest {
    private List<Long> answer;
    private List<String> options;

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Long> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Long> answer) {
        this.answer = answer;
    }
}
