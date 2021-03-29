package com.example.meowtify.models;

public class Image {
    public  int height;
    public int width;
    public String url;

    public Image(int height, int width, String url) {
        this.height = height;
        this.width = width;
        this.url = url;
    }

    public Image() {
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Image{" +
                "height=" + height +
                ", width=" + width +
                ", url='" + url + '\'' +
                '}';
    }
}
