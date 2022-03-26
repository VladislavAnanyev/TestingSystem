package com.example.mywebquizengine.Model.Test;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnswerChecker {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Quiz quiz;
    private boolean success;
    private String feedback;

    public AnswerChecker() {}

    public void checkAnswer(UserQuizAnswer answer){

        if (quiz instanceof MultipleAnswerQuiz) {
            MultipleUserAnswerQuiz multipleUserAnswerQuiz = (MultipleUserAnswerQuiz) answer;
            MultipleAnswerQuiz multipleAnswerQuiz = (MultipleAnswerQuiz) quiz;
            if (multipleUserAnswerQuiz.getAnswer().toString().equals(multipleAnswerQuiz.getAnswer().toString())) {
                this.feedback = "Congratulations, you're right!";
                this.success = true;
            } else {
                this.feedback = "Wrong answer! Please, try again.";
                this.success = false;
            }
        } else if (quiz instanceof StringAnswerQuiz) {
            StringUserAnswerQuiz stringUserAnswerQuiz = (StringUserAnswerQuiz) answer;
            StringAnswerQuiz stringAnswerQuiz = (StringAnswerQuiz) quiz;
            if (stringUserAnswerQuiz.getAnswer().equals(stringAnswerQuiz.getAnswer().get(0))) {
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
