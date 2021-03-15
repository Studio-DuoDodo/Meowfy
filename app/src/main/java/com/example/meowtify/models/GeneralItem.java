package com.example.meowtify.models;

import android.media.Image;

public class GeneralItem {
    private Image image;
    private String titel, subTitel;

    public GeneralItem( String titel, String subTitel) {
        //this.image = image;
        this.titel = titel;
        this.subTitel = subTitel;
    }

    public Image getImage() {
        return image;
    }

    public String getTitel() {
        return titel;
    }

    public String getSubTitel() {
        return subTitel;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setSubTitel(String subTitel) {
        this.subTitel = subTitel;
    }
}
