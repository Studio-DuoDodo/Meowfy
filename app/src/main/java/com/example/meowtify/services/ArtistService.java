package com.example.meowtify.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.meowtify.VolleyCallBack;
import com.example.meowtify.models.Artist;
import com.example.meowtify.models.Image;
import com.example.meowtify.models.Song;
import com.example.meowtify.models.Type;
import com.example.meowtify.models.genre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ArtistService {
    private ArrayList<Artist> artists = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    private RequestQueue queue;

    public ArtistService(Context context) {
        sharedPreferences = context.getSharedPreferences("SPOTIFY", 0);
        queue = Volley.newRequestQueue(context);

    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public Artist getArtistByid(String id, final VolleyCallBack callBack) {
        String endpoint = "https://api.spotify.com/v1/artists/" + id;
        AtomicReference<Artist> artistAtomic= new AtomicReference<>();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, response -> {
                     try {
                        JSONObject object= response.getJSONObject("followers");
                        Artist artist = new Artist();
                        //todo arreglar esta basura
                        //int followers = object.optInt("total");
                        JSONArray jsonArray = response.optJSONArray("genres");
                       artist.setGenres(new ArrayList<genre>());
                        for (int n = 0; n < jsonArray.length(); n++) {
                            try {
                                String gnere= jsonArray.getString(n);
                                gnere=  gnere.replace(' ','_');
                                artist.addGenre(genre.valueOf(gnere));
                               } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                     //   artist.setFollowers(followers);
                       artist.setId(id);
                       String name= response.getString("name");
                       artist.setName(name);
                       artist.setTotal(response.getInt("total"));
                       int popularity = response.getInt("popularity");
                       artist.setPopularity(popularity);
                         artist.setType(Type.valueOf(response.getString("type")));
                        JSONArray jsonArrayImages = response.optJSONArray("images");
                        for (int n = 0; n < jsonArrayImages.length(); n++) {
                            try {
                                Image image= new Image();
                                image.setHeight(jsonArrayImages.getJSONObject(n).getInt("height"));;
                                image.setWidth(jsonArrayImages.getJSONObject(n).getInt("width"));;
                                image.setUrl(jsonArrayImages.getJSONObject(n).getString("url"));;
                                artist.addImages(image);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                            artistAtomic.set(artist);
                        artists.add(artist);

                         System.out.println(artist.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    callBack.onSuccess();
                }, error -> {
                    // TODO: Handle error

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = sharedPreferences.getString("token", "");
                String auth = "Bearer " + token;

                headers.put("Authorization", auth);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);

    return artistAtomic.get();
    }

    public void addSongToLibrary(Song song) {
        JSONObject payload = preparePutPayload(song);
        JsonObjectRequest jsonObjectRequest = prepareSongLibraryRequest(payload);
        queue.add(jsonObjectRequest);
    }

    private JsonObjectRequest prepareSongLibraryRequest(JSONObject payload) {
        return new JsonObjectRequest(Request.Method.PUT, "https://api.spotify.com/v1/me/tracks", payload, response -> {
        }, error -> {
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = sharedPreferences.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
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