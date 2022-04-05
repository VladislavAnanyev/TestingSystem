package com.example.mywebquizengine.Model.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StringAnswerQuizRequest extends AddQuizRequest {

    @NotNull
    @NotBlank
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
