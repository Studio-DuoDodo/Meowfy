package com.example.meowtify.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Song {
    public String getIdSong() {
        return "  notimplemented";
    }
    public String name;
public int popularity;
@SerializedName("release_date")
    public Date release_date;
    public List<Artist> artists;

    public String grupo;
    public Image[] images;
    public Album album;
    public int duration_ms;

public String preview_url;
  public  Type type;
    //bottom fragment sheet
    //musica youtube

    private String id;

    public  GeneralItem  toGeneralItem(){
        GeneralItem item= new GeneralItem(id, name, type, album.getImages().get(0).getUrl(), album.getName(), null);


        System.out.println("GeneralItem generado: " + item.toString());
        return item;
    }

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
                ", artist=" + artists +
                ", grupo='" + grupo + '\'' +
                ", type=" + type +
                ", id='" + id + '\'' +
                '}';
    }
}
