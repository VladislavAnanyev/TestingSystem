package com.example.mywebquizengine.Model.Test;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnswerChecker {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Quiz quiz;
    private boolean success;

    public AnswerChecker() {}

    public void checkAnswer(UserQuizAnswer answer){

        if (quiz instanceof MultipleAnswerQuiz multipleAnswerQuiz) {
            MultipleUserAnswerQuiz multipleUserAnswerQuiz = (MultipleUserAnswerQuiz) answer;
            this.success = multipleUserAnswerQuiz.getAnswer().toString().equals(multipleAnswerQuiz.getAnswer().toString());
        } else if (quiz instanceof StringAnswerQuiz stringAnswerQuiz) {
            StringUserAnswerQuiz stringUserAnswerQuiz = (StringUserAnswerQuiz) answer;
            this.success = stringUserAnswerQuiz.getAnswer().equals(stringAnswerQuiz.getAnswer().get(0));
        } else if (quiz instanceof MapAnswerQuiz mapAnswerQuiz) {
            MapUserAnswerQuiz mapUserAnswerQuiz = (MapUserAnswerQuiz) answer;
            this.success = mapUserAnswerQuiz.getAnswer().equals(mapAnswerQuiz.getAnswer());
        }

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
