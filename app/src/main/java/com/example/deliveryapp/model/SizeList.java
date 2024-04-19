package com.example.deliveryapp.model;

import java.util.List;

public class SizeList <T>{
    List<T> data;

    public SizeList(List<T> data) {
        this.data = data;
    }

    public SizeList() {
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
