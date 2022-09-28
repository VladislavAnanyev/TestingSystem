package com.example.mywebquizengine.model.dto.output;

public class SendAnswerResponse {
    private Double percent;
    private char[] result;

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public char[] getResult() {
        return result;
    }

    public void setResult(char[] result) {
        this.result = result;
    }
}
