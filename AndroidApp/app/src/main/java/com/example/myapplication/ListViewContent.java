package com.example.myapplication;

import android.graphics.Bitmap;

public class ListViewContent {
    String mssv;
    String name;
    Bitmap image;

    public ListViewContent(String mssv, String name, Bitmap image) {
        this.image = image;
        this.name = name;
        this.mssv = mssv;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getMssv() {
        return mssv;
    }
}
