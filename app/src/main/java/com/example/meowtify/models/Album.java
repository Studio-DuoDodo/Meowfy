package com.example.meowtify.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Album {

    @SerializedName("images")
    private List<Image> images;

    @SerializedName("available_markets")
    private List<String> availableMarkets;

    @SerializedName("release_date_precision")
    private String releaseDatePrecision;

    @SerializedName("type")
    private Type type;

    @SerializedName("uri")
    private String uri;

    @SerializedName("total_tracks")
    private int totalTracks;

    @SerializedName("artists")
    private List<ArtistsItem> artists;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("name")
    private String name;

    @SerializedName("album_type")
    private String albumType;

    @SerializedName("href")
    private String href;

    @SerializedName("id")
    private String id;

    @SerializedName("external_urls")
    private ExternalUrls externalUrls;

    public List<Image> getImages() {
        return images;
    }

    public List<String> getAvailableMarkets() {
        return availableMarkets;
    }

    public String getReleaseDatePrecision() {
        return releaseDatePrecision;
    }

    public Type getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }

    public int getTotalTracks() {
        return totalTracks;
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

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }

    public ExternalUrls getExternalUrls() {
        return externalUrls;
    }

    @Override
    public String toString() {
        return
                "Album{" +
                        "images = '" + images + '\'' +
                        ",available_markets = '" + availableMarkets + '\'' +
                        ",release_date_precision = '" + releaseDatePrecision + '\'' +
                        ",type = '" + type + '\'' +
                        ",uri = '" + uri + '\'' +
                        ",total_tracks = '" + totalTracks + '\'' +
                        ",artists = '" + artists + '\'' +
                        ",release_date = '" + releaseDate + '\'' +
                        ",name = '" + name + '\'' +
                        ",album_type = '" + albumType + '\'' +
                        ",href = '" + href + '\'' +
                        ",id = '" + id + '\'' +
                        ",external_urls = '" + externalUrls + '\'' +
                        "}";
    }

    public GeneralItem toGeneralItem() {
        GeneralItem item= new GeneralItem();
        item.setId(id);
        item.setName(name);
        item.setType(type);
        item.setImage(getImages().get(0).url);
        item.setExtra1(artists.get(0).getName());
        return  item;}
}