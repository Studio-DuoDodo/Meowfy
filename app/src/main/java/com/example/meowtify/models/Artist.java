package com.example.meowtify.models;

import java.util.ArrayList;
import java.util.List;

public class Artist {
    public int followers;
    public List<genre> genres;
    public String name;
    public int popularity;
    public String id;
    public  Type type;
    public  List<Image> images;

    public Artist() {
        images= new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public List<genre> getGenres() {
        return genres;
    }

    public void setGenres(List<genre> genres) {
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopularity() {
        return popularity;
    }


    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
public void addGenre(genre genre){
        this.genres.add(genre);
}

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Image> getImages() {
        return images;
    }
    public void  addImages(Image image){
        images.add(image);
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "followers=" + followers +
                ", genres=" + genres +
                ", name='" + name + '\'' +
                ", popularity=" + popularity +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", images=" + images.toString() +
                '}';
    }

    public  GeneralItem  toGeneralItem(){
        GeneralItem item= new GeneralItem(id, name, type, images.get(0).url, null, null);


        System.out.println("GeneralItem generado: " + item.toString());
        return item;
    }
}
