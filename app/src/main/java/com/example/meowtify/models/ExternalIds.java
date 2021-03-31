package com.example.meowtify.models;

import com.google.gson.annotations.SerializedName;

public class ExternalIds{

	@SerializedName("isrc")
	private String isrc;

	public String getIsrc(){
		return isrc;
	}
}