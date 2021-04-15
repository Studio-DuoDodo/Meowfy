package com.example.meowtify.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.meowtify.VolleyCallBack;
import com.example.meowtify.models.Album;
import com.example.meowtify.models.Playlist;
import com.example.meowtify.models.Song;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class AlbumService {
    boolean lastCheck;
    List<Album> newReleases = new ArrayList<>();
    List<Album> userSavedAlbums = new ArrayList<>();
    List<Playlist> developersPlaylist = new ArrayList<>();
    Album lastAlbum;
    private SharedPreferences sharedPreferences;
    private RequestQueue queue;

    public AlbumService(Context context) {
        sharedPreferences = context.getSharedPreferences("SPOTIFY", 0);
        queue = Volley.newRequestQueue(context);
    }

    public List<Album> getNewReleases() {
        return newReleases;
    }

    public List<Album> getUserSavedAlbums() {
        return userSavedAlbums;
    }

    public List<Album> getNewReleases(final VolleyCallBack callBack, String country, int limit, int offset) {
        String endpoint = "https://api.spotify.com/v1/browse/new-releases?country=" + country + "&limit=" + limit + "&offset=" + offset;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, response -> {
                    System.out.println("Response :  " + response.toString());
                    Gson gson = new Gson();
                    JSONObject jsonObject = response.optJSONObject("albums");
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = jsonObject.getJSONArray("items");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        assert jsonArray != null;
                        System.out.println(jsonArray.toString());
                        for (int n = 0; n < jsonArray.length(); n++) {

                            JSONObject object1 = jsonArray.getJSONObject(n);
                            System.out.println("print new releases album" + object1.toString());
                            //     object = object.optJSONObject("tracks");
                            Album a = gson.fromJson(object1.toString(), Album.class);
                            System.out.println(a.toString());
                            newReleases.add(a);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    callBack.onSuccess();
                }, error -> {
                    System.out.println("Error");
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

        return newReleases;
    }
   public List<Album> getUserSavedAlbums(final VolleyCallBack callBack, String market, int limit, int offset) {
        String endpoint = "https://api.spotify.com/v1/me/albums?limit=" + limit + "&offset="+offset+"&market=" + market;
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
                            object1=object1.getJSONObject("album");
                            System.out.println("print used saved album album" + object1.toString());
                            //     object = object.optJSONObject("tracks");
                            Album a = gson.fromJson(object1.toString(), Album.class);
                            System.out.println(a.toString());
                            userSavedAlbums.add(a);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    callBack.onSuccess();
                }, error -> {
                    System.out.println("Error" + error.getMessage());
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

        return newReleases;
    }



    public void getAlbumByRef(final VolleyCallBack callBack, String albumId) {
        String endpoint = "https://api.spotify.com/v1/albums/" + albumId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, response -> {
                    Gson gson = new Gson();
                    System.out.println("JSON: " + response.toString());
                    Album p = gson.fromJson(response.toString(), Album.class);
                    System.out.println("Album p " + p.toString());
                    lastAlbum = p;
                    getSongsOfAnAlbum(callBack, p.getId(), "ES", 10, 0);

                }, error -> {
                     System.out.println(error.getMessage());
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

    }

    public Album getLastAlbum() {
        return lastAlbum;
    }

    //todo give the bool and method a better name
    public boolean isLastCheck() {
        return lastCheck;
    }

    public List<Song> getSongsOfAnAlbum(final VolleyCallBack callBack, String id, String market, int limit, int offset) {
        String endpoint = "https://api.spotify.com/v1/albums/" + id + "/tracks?market=" + market + "&limit=" + limit + "&offset=" + offset;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, response -> {
                    Gson gson = new Gson();
                    JSONArray jsonArray = response.optJSONArray("items");

                    for (int n = 0; n < jsonArray.length(); n++) {
                        try {
                            JSONObject object = jsonArray.getJSONObject(n);

                            Song song = gson.fromJson(object.toString(), Song.class);
                            System.out.println(song.toString());
                            lastAlbum.addSong(song);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    callBack.onSuccess();

                }, error -> {
                    // TODO: Handle error

                    System.out.println(error.getMessage());
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
        return lastAlbum.getSongs();
    }


    public AtomicBoolean checkIfTheUserSavedAAlbum(final VolleyCallBack callBack, String albumId) {
        AtomicBoolean user = new AtomicBoolean(false);
        String endpoint = "https://api.spotify.com/v1/me/albums/contains?ids=" + albumId ;
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, endpoint, null, response -> {
                    try {
                        for (int n = 0; n < response.length(); n++) {
                            user.set(response.getBoolean(n));
                             lastCheck = user.get();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    callBack.onSuccess();

                }, error -> {
                     System.out.println("error on error " + error.toString() + error.getMessage() + error.getLocalizedMessage());

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
        return user;
    }

    public void saveAlbumToUserLibrary(Album album) {
        JSONObject payload = preparePutPayload(album);
        System.out.println("Album to upload" + " = " + album.getId());
        JsonObjectRequest jsonObjectRequest = prepareSaveAlbumRequest(payload, album.getId());
        queue.add(jsonObjectRequest);
    }

    private JsonObjectRequest prepareSaveAlbumRequest(JSONObject payload, String id) {
        return new JsonObjectRequest(Request.Method.PUT, "https://api.spotify.com/v1/me/albums?ids=" + id , payload, response -> {
        }, error -> {
            System.out.println(error.getMessage());
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

    private JsonObjectRequest prepareUnsaveAlbumRequest(JSONObject payload, String id) {
        return new JsonObjectRequest(Request.Method.DELETE, "https://api.spotify.com/v1/me/albums?ids=" + id , payload, response -> {
        }, error -> {
            System.out.println(error.getMessage());
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

    private JSONObject preparePutPayload(Album album) {
        JSONObject ids = new JSONObject();
        try {
            ids.put("public", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ids;
    }

    public void unsaveAnAlbum(Album album) {
        JSONObject payload = new JSONObject();
        System.out.println("ID to remove of saved albums" + " = " + album.getId());
        JsonObjectRequest jsonObjectRequest = prepareUnsaveAlbumRequest(payload, album.getId());
        queue.add(jsonObjectRequest);
    }


}


