package com.example.mywebquizengine.Model.Projection;

import java.time.LocalTime;
import java.util.List;

public interface TestForAnswerView {
    Long getTestId();
    LocalTime getDuration();
    List<QuizView> getQuizzes();
}
