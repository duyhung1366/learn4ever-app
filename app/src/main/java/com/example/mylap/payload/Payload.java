package com.example.mylap.payload;

import com.google.gson.annotations.SerializedName;

public class Payload {
    @SerializedName("status")
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
