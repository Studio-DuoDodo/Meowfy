package com.example.meowtify.models;

import java.util.List;

public class Artist {
    public int followers;
    public List<genre> genres;
    public String name;
    public int popularity;
public String id;

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
    @Override
    public String toString() {
        return "Artist{" +
                "followers=" + followers +
                ", genres=" + genres +
                ", name='" + name + '\'' +
                ", popularity=" + popularity +
                '}';
    }
}
