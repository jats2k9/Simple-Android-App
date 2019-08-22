package com.users.model;

import android.graphics.Bitmap;

public class User {

    private String userName;
    private Bitmap userImg;

    public User(String userName, Bitmap userImg) {
        this.userName = userName;
        this.userImg = userImg;
    }

    public String getUserName() {
        return userName;
    }

    public Bitmap getUserImg() {
        return userImg;
    }
}
