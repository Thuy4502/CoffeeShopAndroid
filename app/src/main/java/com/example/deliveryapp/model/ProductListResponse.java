package com.example.deliveryapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductListResponse<T> {
    @SerializedName("data")
    private List<T> data;

    // Getter và setter

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}

