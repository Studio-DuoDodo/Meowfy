package com.example.meowtify.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Artist {
    public Followers followers;
    public List<genre> genres;
     public int popularity;
     public int total=0;
    public  List<Image> images;


    @SerializedName("name")
    private String name;

    @SerializedName("href")
    private String href;

    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private Type type;

    @SerializedName("external_urls")
    private ExternalUrls externalUrls;

    @SerializedName("uri")
    private String uri;

    public Artist(Followers followers, List<genre> genres, int popularity, int total, List<Image> images, String name, String href, String id, Type type, ExternalUrls externalUrls, String uri) {
        this.followers = followers;
        this.genres = genres;
        this.popularity = popularity;
        this.total = total;
        this.images = images;
        this.name = name;
        this.href = href;
        this.id = id;
        this.type = type;
        this.externalUrls = externalUrls;
        this.uri = uri;
    }

    public String getName(){
        return name;
    }

    public String getHref(){
        return href;
    }

    public String getId(){
        return id;
    }

    public Type getType(){
        return type;
    }

    public ExternalUrls getExternalUrls(){
        return externalUrls;
    }

    public String getUri(){
        return uri;
    }
    public Artist() {
        images= new ArrayList<>();
        name= "Ryuk";
    }


    public void setId(String id) {
        this.id = id;
    }

    public Followers getFollowers() {
        return followers;
    }

    public void setFollowers(Followers followers) {
        this.followers = followers;
    }

    public List<genre> getGenres() {
        return genres;
    }

    public void setGenres(List<genre> genres) {
        this.genres = genres;
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


    public void setType(Type type) {
        this.type = type;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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
        GeneralItem item;
        if (images!=null && images.size()>0)
         item= new GeneralItem(id, name, type, images.get(0).url, "songs "+total, null);
        else{
            item= new GeneralItem(id, name, type, "https://consequenceofsound.net/wp-content/uploads/2015/10/screen-shot-2015-10-17-at-6-57-13-pm.png", "songs "+total, null);

        }

        System.out.println("GeneralItem generado: " + item.toString());
        return item;
    }
}
