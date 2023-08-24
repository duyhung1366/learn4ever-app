package com.example.mylap.responsive;

import com.example.mylap.models.Course;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetListCourse {
    @SerializedName("data")
    private ArrayList<Course> data;

    @SerializedName("status")
    private int status;

    public ArrayList<Course> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
