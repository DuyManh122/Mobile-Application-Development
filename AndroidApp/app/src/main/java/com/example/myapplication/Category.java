package com.example.myapplication;

import android.graphics.Bitmap;

public class Category {
    private String name;
    private Bitmap iconBitmap;

    public Category(String name, Bitmap iconBitmap) {
        this.name = name;
        this.iconBitmap = iconBitmap;
    }

    public String getName() {
        return name;
    }

    public Bitmap getIconBitmap() {
        return iconBitmap;
    }
}
