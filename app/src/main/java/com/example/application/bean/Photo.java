package com.example.application.bean;

public class Photo {

    private int src;
    private String description;


    public Photo(int src, String description) {
        this.src = src;
        this.description = description;
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
