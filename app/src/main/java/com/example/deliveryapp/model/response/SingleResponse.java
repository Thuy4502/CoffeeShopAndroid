package com.example.deliveryapp.model.response;

import com.example.deliveryapp.model.FullCart;

public class SingleResponse<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

