package com.example.meowtify.models;

import java.io.Serializable;

public class GeneralItem implements Serializable {
    private String id, name, image, extra1, extra2;
    Type type;

    public GeneralItem(String id, String name, Type type, String image, String extra1, String extra2) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.image = image;
        this.extra1 = extra1;
        this.extra2 = extra2;
    }

    public GeneralItem() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public String getExtra1() {
        return extra1;
    }

    public String getExtra2() {
        return extra2;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setExtra1(String extra1) {
        this.extra1 = extra1;
    }

    public void setExtra2(String extra2) {
        this.extra2 = extra2;
    }

    @Override
    public String toString() {
        return "GeneralItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", extra1='" + extra1 + '\'' +
                ", extra2='" + extra2 + '\'' +
                ", type=" + type +
                '}';
    }
}
