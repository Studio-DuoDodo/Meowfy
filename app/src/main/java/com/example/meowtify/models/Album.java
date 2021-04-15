package com.example.meowtify.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Album {

    List<Song> songs = new ArrayList<>();
    @SerializedName("total_tracks")
    private int totalTracks;
    @SerializedName("images")
    private List<Image> images;
    @SerializedName("artists")
    private List<ArtistsItem> artists;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("name")
    private String name;
    @SerializedName("album_type")
    private String albumType;
    @SerializedName("release_date_precision")
    private String releaseDatePrecision;
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

    public String getArtistNames() {
        String artistNames = "";
        for (int i = 0; i < artists.size(); i++) {
            if (i == artists.size() - 1) {
                artistNames += artists.get(i).getName();

            } else {
                artistNames += artists.get(i).getName() + ", ";

            }
        }
        return artistNames;
    }

    public int getTotalTracks() {
        return totalTracks;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<ArtistsItem> getArtists() {
        return artists;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getName() {
        return name;
    }

    public String getAlbumType() {
        return albumType;
    }

    public String getReleaseDatePrecision() {
        return releaseDatePrecision;
    }

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public ExternalUrls getExternalUrls() {
        return externalUrls;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public List<GeneralItem> getSongsConverted() {
        List<GeneralItem> songs = new ArrayList<>();
        for (Song s : this.songs) {
            songs.add(s.toGeneralItem(images.get(0).url, s.name));
        }
        return songs;
    }

    public String getUri() {
        return uri;
    }

    public void addSong(Song s) {
        songs.add(s);
    }

    public GeneralItem toGeneralItem() {
        GeneralItem item = new GeneralItem();
        item.setId(id);
        item.setName(name);
        item.setType(type);
        if (images!=null)
        item.setImage(images.get(0).getUrl());
      else item.setImage("https://static.wikia.nocookie.net/memes-pedia/images/a/ac/Cj2.jpg/revision/latest?cb=20200708183438&path-prefix=es");
        item.setExtra1(artists.get(0).getName());
        return item;
    }

    public GeneralItem toGeneralItemArtist() {
        GeneralItem item = new GeneralItem();
        item.setId(id);
        item.setName(name);
        item.setType(type);
        if (images!=null)
            item.setImage(images.get(0).getUrl());
        else item.setImage("https://static.wikia.nocookie.net/memes-pedia/images/a/ac/Cj2.jpg/revision/latest?cb=20200708183438&path-prefix=es");
        item.setExtra1(releaseDate);
        return item;
    }

    @Override
    public String toString() {
        return "Album{" +
                "songs=" + songs +
                ", totalTracks=" + totalTracks +
                ", images=" + images +
                ", artists=" + artists +
                ", releaseDate='" + releaseDate + '\'' +
                ", name='" + name + '\'' +
                ", albumType='" + albumType + '\'' +
                ", releaseDatePrecision='" + releaseDatePrecision + '\'' +
                ", href='" + href + '\'' +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", externalUrls=" + externalUrls +
                ", uri='" + uri + '\'' +
                '}';
    }
}