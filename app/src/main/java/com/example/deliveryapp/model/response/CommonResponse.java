package com.example.deliveryapp.model.response;

import java.util.List;

public class CommonResponse<T> {
    private List<T> data;
    private int status;
    private String message;

    public CommonResponse(List<T> data, int status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public CommonResponse() {
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

