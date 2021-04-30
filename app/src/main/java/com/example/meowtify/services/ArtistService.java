package com.example.meowtify.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.meowtify.Utilitis;
import com.example.meowtify.VolleyCallBack;
import com.example.meowtify.models.Album;
import com.example.meowtify.models.Artist;
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

public class ArtistService {
    public List<Artist> relatedArtist = new ArrayList<>();
    public Artist lastSearchedArtist;
    private ArrayList<Artist> artists = new ArrayList<>();
    private ArrayList<Album>  albumsLastArtist = new ArrayList<>();
    private ArrayList<Song> topSongsLastArtist = new ArrayList<>();
    List<Artist> userFollowedArtists=new ArrayList<>();
//todo pasar a lista de listas

    private SharedPreferences sharedPreferences;
    private RequestQueue queue;

    public List<Artist> getUserFollowedArtists() {
        System.out.println("FOLLOWED ARTISTS" + userFollowedArtists.toString());
        return userFollowedArtists;
    }

    public ArrayList<Song> getTopSongsLastArtist() {
        return topSongsLastArtist;
    }

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
    public List<Artist> getUserFollowedArtists(final VolleyCallBack callBack, int limit) {
        String endpoint = "https://api.spotify.com/v1/me/following?type=artist&limit=" + limit;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, response -> {
                    try {
                        JSONObject jsonObject= response.getJSONObject("artists");
                        JSONArray jsonArray = jsonObject.getJSONArray("items");
                        Gson gson = new Gson();
                        for (int n = 0; n < jsonArray.length(); n++) {
                            try {
                                 jsonObject = jsonArray.getJSONObject(n);
                                System.out.println("USER FOLLOWED ARTISTS");
                                userFollowedArtists.add(gson.fromJson(jsonObject.toString(), Artist.class));
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
    public List<Song> getTopSongsOfAnArtist(final VolleyCallBack callBack, String id, String market ) {
        String endpoint = "https://api.spotify.com/v1/artists/" + id + "/top-tracks?market=" + market ;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, response -> {
                    Gson gson = new Gson();
                    JSONArray jsonArray = response.optJSONArray("tracks");
                    for (int n = 0; n < jsonArray.length(); n++) {
                        try {
                            JSONObject object = jsonArray.getJSONObject(n);

                            Song song = gson.fromJson(object.toString(), Song.class);
                            System.out.println("TOP SONG" + song.toString());
                            topSongsLastArtist.add(song);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    callBack.onSuccess();

                }, error -> {
                    System.out.println(error.getMessage());
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
              return Utilitis.getHeaders( sharedPreferences.getString("token", ""));
            }
        };
        queue.add(jsonObjectRequest);
        return topSongsLastArtist;
    }



     public void unfollowAArtist(Artist artist) {
         JSONObject payload = new JSONObject();
         System.out.println("ID to follow" + " = " + artist.getId());
         JsonObjectRequest jsonObjectRequest = prepareUnfollowArtistRequest(payload, artist.getId());
         queue.add(jsonObjectRequest);
     }

     public AtomicBoolean checkIfTheUserFollowsAArtist(final VolleyCallBack callBack, String artistId) {
         AtomicBoolean user = new AtomicBoolean(false);
         String endpoint = "https://api.spotify.com/v1/me/following/contains?type=artist&ids=/me/following/contains?type=artist&ids=https://api.spotify.com/v1/playlists/" + artistId + "/followers/contains?ids=" + sharedPreferences.getString("userid", null);
         JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                 (Request.Method.GET, endpoint, null, response -> {
                     try {
                         for (int n = 0; n < response.length(); n++) {
                             user.set(response.getBoolean(n));

                             System.out.println("User is in the playlist " + user);
                             lastCheck = user.get();
                         }

                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                     callBack.onSuccess();

                 }, error -> {
                     // TODO: Handle error
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
         queue.add(jsonArrayRequest);
         return user;
     }
     boolean lastCheck;
     //todo give the bool and methos a better name
     public boolean isLastCheck() {
         return lastCheck;
     }
     public void followAPlaylist(Playlist playlist) {
         JSONObject payload = preparePutPayload(playlist);
         System.out.println("ID to follow" + " = " + playlist.getId());
         JsonObjectRequest jsonObjectRequest = prepareFollowPlaylistRequest(payload, playlist.getId());
         queue.add(jsonObjectRequest);
     }

     private JsonObjectRequest prepareFollowPlaylistRequest(JSONObject payload, String id) {
         return new JsonObjectRequest(Request.Method.PUT, "https://api.spotify.com/v1/me/following?type=artist&ids=" + id + "/followers", payload, response -> {
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

     private JsonObjectRequest prepareUnfollowArtistRequest(JSONObject payload, String id) {
         return new JsonObjectRequest(Request.Method.DELETE, "https://api.spotify.com/v1/playlists/" + id + "/followers", payload, response -> {
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
    private JSONObject preparePutPayload(Playlist playlist) {

        JSONObject ids = new JSONObject();
        try {
            ids.put("public", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ids;
    }

}