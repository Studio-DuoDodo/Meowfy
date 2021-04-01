package com.example.meowtify.models;

public class User {
    public String birthdate;
    public String country;
    public String display_name;
    public String email;
    public String id;

    public String getDisplayName() {
        return display_name;
    }

    public User(String display_name) {
        this.display_name = display_name;
    }

    @Override
    public String toString() {
        return "User{" +
                "birthdate='" + birthdate + '\'' +
                ", country='" + country + '\'' +
                ", display_name='" + display_name + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }
}
