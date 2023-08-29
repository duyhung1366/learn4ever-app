package com.example.mylap.models.question;

public class AnswerQuestion {
    private int index;
    private String text;
    private boolean isResult;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isResult() {
        return isResult;
    }

    public void setResult(boolean result) {
        isResult = result;
    }

    public AnswerQuestion() {
    }

    public AnswerQuestion(int index, String text, boolean isResult) {
        this.index = index;
        this.text = text;
        this.isResult = isResult;
    }
}
