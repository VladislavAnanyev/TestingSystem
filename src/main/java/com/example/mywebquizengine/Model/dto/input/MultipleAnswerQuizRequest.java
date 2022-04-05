package com.example.mywebquizengine.Model.dto.input;

import java.util.List;

public class MultipleAnswerQuizRequest extends AddQuizRequest {
    private List<Integer> answer;
    private List<String> options;

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }
}
