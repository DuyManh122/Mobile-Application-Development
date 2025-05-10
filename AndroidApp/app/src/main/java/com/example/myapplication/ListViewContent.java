package com.example.myapplication;

public class ListViewContent {
    int image;
    String name;
    String version;

    public ListViewContent(int image, String name, String version) {
        this.image = image;
        this.name = name;
        this.version = version;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}
