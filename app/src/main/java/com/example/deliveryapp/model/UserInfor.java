package com.example.deliveryapp.model;

public class UserInfor {
    private User data;

    public UserInfor() {
    }

    public UserInfor(User data) {
        this.data = data;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
