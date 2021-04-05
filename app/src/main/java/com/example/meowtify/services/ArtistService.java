package com.example.meowtify.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.meowtify.Utilitis;
import com.example.meowtify.VolleyCallBack;
import com.example.meowtify.models.Album;
import com.example.meowtify.models.Artist;
import com.example.meowtify.models.Image;
import com.example.meowtify.models.Song;
import com.example.meowtify.models.Type;
import com.example.meowtify.models.genre;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ArtistService {
    public List<Artist> relatedArtist = new ArrayList<>();
    public Artist lastSearchedArtist;
    private ArrayList<Artist> artists = new ArrayList<>();
    private ArrayList<Album>  albumsLastArtist = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private RequestQueue queue;

    public List<Artist> getRelatedArtist() {
        return relatedArtist;
    }

    public ArrayList<Album> getAlbumsLastArtist() {
        return albumsLastArtist;
    }

    public ArtistService(Context context) {
        sharedPreferences = context.getSharedPreferences("SPOTIFY", 0);
        queue = Volley.newRequestQueue(context);

    }

    public Artist getLastSearchedArtist() {
        return lastSearchedArtist;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public Artist getArtistByid(String id, final VolleyCallBack callBack) {
        String endpoint = "https://api.spotify.com/v1/artists/" + id;
        AtomicReference<Artist> artistAtomic = new AtomicReference<>();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, response -> {
                    Gson gson= new Gson();
                    Artist artist = gson.fromJson(response.toString(),Artist.class);
                    lastSearchedArtist = artist;
                    System.out.println("The artist" + artist.toString());
                    callBack.onSuccess();
                }, error -> {
                    // TODO: Handle error

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Utilitis.getHeaders(sharedPreferences.getString("token", ""));
            }
        };
        queue.add(jsonObjectRequest);

        return getLastSearchedArtist();
    }

    public List<Artist> getRelatedArtists(final VolleyCallBack callBack, String id) {
        String endpoint = "https://api.spotify.com/v1/artists/" + id + "/related-artists";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("artists");
                        Gson gson = new Gson();
                        for (int n = 0; n < jsonArray.length(); n++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(n);
                                relatedArtist.add(gson.fromJson(jsonObject.toString(), Artist.class));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    callBack.onSuccess();
                }, error -> {
                    System.out.println("error" + error.getMessage());

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Utilitis.getHeaders(sharedPreferences.getString("token", ""));
            }
        };
        queue.add(jsonObjectRequest);

        return relatedArtist;
    }
    public List<Album> getArtistAlbums(final VolleyCallBack callBack,String id, String market, int limit, int offset) {
        //todo aqui hay un parametro hardcodeado
        String endpoint = "\thttps://api.spotify.com/v1/artists/"+id+"/albums?include_groups=single%2Cappears_on&market="+ market + "&limit=" + limit + "&offset="+offset;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, response -> {
                    System.out.println("Response :  " + response.toString());
                    Gson gson = new Gson();
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = response.getJSONArray("items");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        assert jsonArray != null;
                        System.out.println(jsonArray.toString());
                        for (int n = 0; n < jsonArray.length(); n++) {

                            JSONObject object1 = jsonArray.getJSONObject(n);
                             System.out.println("print used saved album album" + object1.toString());
                            //     object = object.optJSONObject("tracks");
                            Album a = gson.fromJson(object1.toString(), Album.class);
                            System.out.println(a.toString());
                            albumsLastArtist.add(a);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    callBack.onSuccess();
                }, error -> {
                    System.out.println("Error" + error.getMessage());
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
            return Utilitis.getHeaders(sharedPreferences.getString("token", ""));
             }
        };
        queue.add(jsonObjectRequest);

        return albumsLastArtist;
    }


    private JSONObject preparePutPayload(Song song) {
        JSONArray idarray = new JSONArray();
        idarray.put(song.getId());
        JSONObject ids = new JSONObject();
        try {
            ids.put("ids", idarray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ids;
    }
}