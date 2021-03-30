package com.example.meowtify.models;

import com.google.gson.annotations.SerializedName;

public class ArtistsItem{

	@SerializedName("name")
	private String name;

	@SerializedName("href")
	private String href;

	@SerializedName("id")
	private String id;

	@SerializedName("type")
	private String type;

	@SerializedName("external_urls")
	private ExternalUrls externalUrls;

	@SerializedName("uri")
	private String uri;

	public String getName(){
		return name;
	}

	public String getHref(){
		return href;
	}

	public String getId(){
		return id;
	}

	public String getType(){
		return type;
	}

	public ExternalUrls getExternalUrls(){
		return externalUrls;
	}

	public String getUri(){
		return uri;
	}

	@Override
 	public String toString(){
		return 
			"ArtistsItem{" + 
			"name = '" + name + '\'' + 
			",href = '" + href + '\'' + 
			",id = '" + id + '\'' + 
			",type = '" + type + '\'' + 
			",external_urls = '" + externalUrls + '\'' + 
			",uri = '" + uri + '\'' + 
			"}";
		}
}