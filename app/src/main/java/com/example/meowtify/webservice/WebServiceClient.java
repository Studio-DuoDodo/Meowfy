package com.example.meowtify.webservice;

import com.example.meowtify.models.Data;
import com.example.meowtify.models.Playlist;
import com.example.meowtify.models.Song;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WebServiceClient {

    @GET()
    Call<Data> getAllSongs(@Url String url);

    @GET()
    Call<Data> getAllFilms(@Url String url);

    @GET()
    Call<Song> getSonngs(@Url String url);


    @GET()
    Call<Playlist> getPlaylist(@Url String url);

}
