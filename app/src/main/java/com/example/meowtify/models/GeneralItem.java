package com.example.meowtify.models;

import android.media.Image;
import android.widget.ScrollView;

public class GeneralItem {
    private String image, titel, subTitel;

    public GeneralItem(String titel, String subTitel, String image) {
        this.image = image;
        this.titel = titel;
        this.subTitel = subTitel;
    }

    public String getImage() {
        return image;
    }

    public String getTitel() {
        return titel;
    }

    public String getSubTitel() {
        return subTitel;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setSubTitel(String subTitel) {
        this.subTitel = subTitel;
    }
}
