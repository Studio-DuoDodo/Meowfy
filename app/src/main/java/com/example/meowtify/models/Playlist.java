package com.example.meowtify.models;

import java.util.Arrays;

public class Playlist extends GeneralItem {
    boolean colaborative = false;
    //todo hacer que followers sea una clase con string href y  int total
    int followers;
    String description;

    String href;
    String id;
    Image[]  images;
    String name;
    User owner;
    boolean isPublic;
    String snapshotId;
    Track tracks;
    Type type;

    public Playlist() {
        super("","","");

    }

    public  GeneralItem  toGeneralItem(){
        GeneralItem item= new GeneralItem(name,description,images[0].url);
        System.out.println("GeneralItem generado: " + item.toString());
 return item;
    }
    public Playlist(boolean colaborative, int followers, String description, String href, String id, Image[] images, String name, User owner, boolean isPublic, String snapshotId, Track tracks, Type type) {
        super("","","");
        super.setImage(images[0].url);
        super.setTitle(name);
        super.setSubtitle(description);
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

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
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
