package com.example.meowtify.models;

import java.util.Date;
import java.util.List;

public class Song {
    public String getIdSong() {
        return "  notimplemented";
    }
    public String name;
public int popularity;
    public Date release_date;
    public List<Artist> artist;
    public String grupo;
    public int duration_ms;
public String preview_url;
  public  Type type;
    //bottom fragment sheet
    //musica youtube

    private String id;


    public Song(String id, String name,Date release_date, int popularity) {
        this.name = name;
        this.id = id;
        this.release_date=release_date;
        this.popularity=popularity;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", popularity=" + popularity +
                ", release_date=" + release_date +
                ", artist=" + artist +
                ", grupo='" + grupo + '\'' +
                ", type=" + type +
                ", id='" + id + '\'' +
                '}';
    }
}
