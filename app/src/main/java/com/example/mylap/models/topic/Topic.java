package com.example.mylap.models.topic;

import java.util.ArrayList;

public class Topic {
    private String _id;
    private String name;
    private int status;
    private String idCourse;
    private ArrayList<String> topicChild;
    private ArrayList<Topic> topicChildData;
    private String parentId;
    private int type;
    private int topicType;
    private int timeExam;
    private String video;
    private ArrayList<TimePracticeInVideo> timePracticeInVideo;
    private int numQuestion;
    private String des;
    private int index;
    private int createDate;
    private int updateDate;

    public Topic(String _id, String name, int status, String idCourse, ArrayList<String> topicChild, ArrayList<Topic> topicChildData, String parentId, int type, int topicType, int timeExam, String video, ArrayList<TimePracticeInVideo> timePracticeInVideo, int numQuestion, String des, int index, int createDate, int updateDate) {
        this._id = _id;
        this.name = name;
        this.status = status;
        this.idCourse = idCourse;
        this.topicChild = topicChild;
        this.topicChildData = topicChildData;
        this.parentId = parentId;
        this.type = type;
        this.topicType = topicType;
        this.timeExam = timeExam;
        this.video = video;
        this.timePracticeInVideo = timePracticeInVideo;
        this.numQuestion = numQuestion;
        this.des = des;
        this.index = index;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Topic() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public ArrayList<String> getTopicChild() {
        return topicChild;
    }

    public void setTopicChild(ArrayList<String> topicChild) {
        this.topicChild = topicChild;
    }

    public ArrayList<Topic> getTopicChildData() {
        return topicChildData;
    }

    public void setTopicChildData(ArrayList<Topic> topicChildData) {
        this.topicChildData = topicChildData;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTopicType() {
        return topicType;
    }

    public void setTopicType(int topicType) {
        this.topicType = topicType;
    }

    public int getTimeExam() {
        return timeExam;
    }

    public void setTimeExam(int timeExam) {
        this.timeExam = timeExam;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public ArrayList<TimePracticeInVideo> getTimePracticeInVideo() {
        return timePracticeInVideo;
    }

    public void setTimePracticeInVideo(ArrayList<TimePracticeInVideo> timePracticeInVideo) {
        this.timePracticeInVideo = timePracticeInVideo;
    }

    public int getNumQuestion() {
        return numQuestion;
    }

    public void setNumQuestion(int numQuestion) {
        this.numQuestion = numQuestion;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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
