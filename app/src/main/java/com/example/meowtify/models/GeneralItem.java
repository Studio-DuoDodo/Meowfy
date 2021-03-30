package com.example.meowtify.models;

public class GeneralItem {
    private String image, title, subtitle;

    public GeneralItem(String title, String subTitle, String image) {
        this.image = image;
        this.title = title;
        this.subtitle = subTitle;
    }

    public GeneralItem() {
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @Override
    public String toString() {
        return "GeneralItem{" +
                "image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                '}';
    }
}
