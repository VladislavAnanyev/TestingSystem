package com.example.mywebquizengine.Model.Test;

import com.example.mywebquizengine.Model.dto.MultipleAnswerQuizRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AnswerChecker {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Quiz quiz;
    private boolean success;
    private String feedback;

    public AnswerChecker() {}

    public void checkAnswer(UserQuizAnswer answer){

        /*List<Integer> list1 = new ArrayList<>();
        list1 = answer;

        List<Integer> list2 = new ArrayList<>();
        list2 = quiz.getAnswer();*/
        if (quiz.getType().equals("MULTIPLE")) {
            MultipleUserAnswerQuiz multipleUserAnswerQuiz = (MultipleUserAnswerQuiz) answer;
            MultipleAnswerQuiz multipleAnswerQuiz = (MultipleAnswerQuiz) quiz;
            if (multipleUserAnswerQuiz.getAnswer().toString().equals(multipleAnswerQuiz.getAnswer().toString())) {
                this.feedback = "Congratulations, you're right!";
                this.success = true;
            } else {
                this.feedback = "Wrong answer! Please, try again.";
                this.success = false;
            }
        } else if (quiz.getType().equals("STRING")) {
            StringUserAnswerQuiz stringUserAnswerQuiz = (StringUserAnswerQuiz) answer;
            StringAnswerQuiz stringAnswerQuiz = (StringAnswerQuiz) quiz;
            if (stringUserAnswerQuiz.getAnswer().equals(stringAnswerQuiz.getAnswer())) {
                this.feedback = "Congratulations, you're right!";
                this.success = true;
            } else {
                this.feedback = "Wrong answer! Please, try again.";
                this.success = false;
            }
        }

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
