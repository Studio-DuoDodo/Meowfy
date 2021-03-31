package com.example.meowtify.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Album{

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

	public int getTotalTracks(){
		return totalTracks;
	}

	public List<Image> getImages(){
		return images;
	}

	public List<ArtistsItem> getArtists(){
		return artists;
	}

	public String getReleaseDate(){
		return releaseDate;
	}

	public String getName(){
		return name;
	}

	public String getAlbumType(){
		return albumType;
	}

	public String getReleaseDatePrecision(){
		return releaseDatePrecision;
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
	public GeneralItem toGeneralItem() {
		GeneralItem item= new GeneralItem();
		item.setId(id);
		item.setName(name);
		item.setType(type);
		item.setImage(getImages().get(0).getUrl());
		item.setExtra1(artists.get(0).getName());
		return  item;}
}