package com.example.mylap.models.question;

import java.util.ArrayList;

public class Question {
    private String _id;
    private String question;
    private ArrayList<AnswerQuestion> answer;
    private String parentId;
    private int status;
    private int index;
    private String hint;
    private int createDate;
    private int updateDate;

    public Question() {
    }

    public Question(String _id, String question, ArrayList<AnswerQuestion> answer, String parentId, int status, int index, String hint, int createDate, int updateDate) {
        this._id = _id;
        this.question = question;
        this.answer = answer;
        this.parentId = parentId;
        this.status = status;
        this.index = index;
        this.hint = hint;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<AnswerQuestion> getAnswer() {
        return answer;
    }

    public void setAnswer(ArrayList<AnswerQuestion> answer) {
        this.answer = answer;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getCreateDate() {
        return createDate;
    }

    public void setCreateDate(int createDate) {
        this.createDate = createDate;
    }

    public int getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(int updateDate) {
        this.updateDate = updateDate;
    }

}
