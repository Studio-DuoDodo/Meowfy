package com.example.meowtify.models;

import com.google.gson.annotations.SerializedName;

public class Followers{

	@SerializedName("total")
	private int total;

	@SerializedName("href")
	private Object href;

	public int getTotal(){
		return total;
	}

	public Object getHref(){
		return href;
	}

	@Override
 	public String toString(){
		return 
			"Followers{" + 
			"total = '" + total + '\'' + 
			",href = '" + href + '\'' + 
			"}";
		}
}