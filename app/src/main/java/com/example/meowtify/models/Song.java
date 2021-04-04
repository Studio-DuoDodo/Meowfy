package com.example.meowtify.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Song {
    public String getIdSong() {
        return "  notimplemented";
    }
    public String name;
public int popularity;
@SerializedName("release_date")
      private Date release_date;
    private    List<Artist> artists;

    private   String grupo;
    private   Image[] images;
    private  Album album;
    private  int duration_ms;
@SerializedName("preview_url")
    private String preview_url;
    private  Type type;
    //bottom fragment sheet
    //musica youtube

    private String id;

    public  GeneralItem  toGeneralItem(){
        GeneralItem item= new GeneralItem(id, name, type, album.getImages().get(0).getUrl(), artists.get(0).getName(), album.getName());

        System.out.println("GeneralItem generado: " + item.toString());
        return item;
    }
    public  GeneralItem  toGeneralItem(String url,String name){
        GeneralItem item= new GeneralItem(id, name, type,  url, artists.get(0).getName(), name);

        System.out.println("GeneralItem generado: " + item.toString());
        return item;
    }

    public Song(String id, String name,Date release_date, int popularity) {
        this.name = name;
        this.id = id;
        this.release_date=release_date;
        this.popularity=popularity;

    }

    public Song(String name, String preview_url, String id) {
        this.name = name;
        this.preview_url = preview_url;
        this.id = id;
    }
public  Song(){
    Random random= new Random();
    artists= new ArrayList<>();

        artists.add(new Artist( ));
        name="Test Song" + random.nextInt()*30;
        id="" + random.nextInt(100000);


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

    public int getPopularity() {
        return popularity;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public String getGrupo() {
        return grupo;
    }

    public Image[] getImages() {
        return images;
    }

    public Album getAlbum() {
        return album;
    }

    public int getDuration_ms() {
        return duration_ms;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", popularity=" + popularity +
                ", release_date=" + release_date +
                ", artists=" + artists +
                ", grupo='" + grupo + '\'' +
                ", images=" + Arrays.toString(images) +
                ", album=" + album +
                ", duration_ms=" + duration_ms +
                ", preview_url='" + preview_url + '\'' +
                ", type=" + type +
                ", id='" + id + '\'' +
                '}';
    }
}
