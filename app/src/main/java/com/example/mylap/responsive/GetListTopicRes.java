package com.example.mylap.responsive;

import com.example.mylap.models.topic.Topic;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetListTopicRes {
    @SerializedName("data")
    private List<Topic> data;

    @SerializedName("status")
    private int status;

    public void setData(List<Topic> data) {
        this.data = data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public GetListTopicRes(List<Topic> data, int status) {
        this.data = data;
        this.status = status;
    }

    public List<Topic> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
