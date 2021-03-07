package com.example.meowtify.models;

public class User {
    public String birthdate;
    public String country;
    public String display_name;
    public String email;
    public String id;

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
}
