package com.example.meowtify.models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Playlist {
    boolean colaborative = false;
    //todo hacer que followers sea una clase con string href y  int total
    @SerializedName("followers")
    private Followers followers;    String description;

    String href;
    String id;
    Image[]  images;
    String name;
    User owner;
    boolean isPublic;
    String snapshotId;
    Track tracks;
    Type type;

    public  GeneralItem  toGeneralItem(){
        GeneralItem item;
        if (images[0]!=null){
         item= new GeneralItem(name,description,images[0].url);
        }else {
         item= new GeneralItem(name,description,"https://www.futuro.cl/wp-content/uploads/2020/06/145d9c203f45af092d3ab58de5ab9518-590x340.jpg");

        }
        System.out.println("GeneralItem generado: " + item.toString());
 return item;
    }
    public Playlist(boolean colaborative, Followers followers, String description, String href, String id, Image[] images, String name, User owner, boolean isPublic, String snapshotId, Track tracks, Type type) {
        this.colaborative = colaborative;
        this.followers = followers;
        this.description = description;
        this.href = href;
        this.id = id;
        this.images = images;
        this.name = name;
        this.owner = owner;
        this.isPublic = isPublic;
        this.snapshotId = snapshotId;
        this.tracks = tracks;
        this.type = type;
    }

    public boolean isColaborative() {
        return colaborative;
    }

    public void setColaborative(boolean colaborative) {
        this.colaborative = colaborative;
    }

    public Followers getFollowers() {
        return followers;
    }

    public void setFollowers(Followers followers) {
        this.followers = followers;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Image[] getImages() {
        return images;
    }

    public void setImages(Image[] images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }

    public Track getTracks() {
        return tracks;
    }

    public void setTracks(Track tracks) {
        this.tracks = tracks;
    }

    public Type getPlaylist() {
        return type;
    }

    public void setPlaylist(Type playlist) {
        this.type = playlist;
    }

    public String getIdPlaylist() {
        return "notImplemented";
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "colaborative=" + colaborative +
                ", followers=" + followers +
                ", description='" + description + '\'' +
                ", href='" + href + '\'' +
                ", id='" + id + '\'' +
                ", images=" + Arrays.toString(images).toString() +
                ", name='" + name + '\'' +
                ", owner=" + owner.toString() +
                ", isPublic=" + isPublic +
                ", snapshotId='" + snapshotId + '\'' +
                ", tracks=" + tracks +
                ", type=" + type +
                '}';
    }
}
