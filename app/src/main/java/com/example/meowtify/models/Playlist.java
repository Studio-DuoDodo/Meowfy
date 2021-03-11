package com.example.meowtify.models;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    boolean colaborative = false;
    int followers;
    String href;
    String id;
    List<Image> images;
    String name;
    User owner;
    boolean isPublic;
    String snapshotId;
    List<Song> songs;
    Type playlist;

    public Playlist() {
        songs= new ArrayList<>();
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
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

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public Type getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Type playlist) {
        this.playlist = playlist;
    }

    public String getIdPlaylist() {
        return "notImplemented";
    }
}
