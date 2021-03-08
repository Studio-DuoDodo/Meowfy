package com.example.meowtify;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.meowtify.models.Artist;
import com.example.meowtify.models.Song;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
public  Artist getArtistByid(String id,final VolleyCallBack callBack){
    String endpoint = "https://api.spotify.com/v1/artists/" + id ;
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
            (Request.Method.GET, endpoint, null, response -> {
                Gson gson = new Gson();
                JSONArray jsonArray = response.optJSONArray("items");
                for (int n = 0; n < jsonArray.length(); n++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(n);
                        object = object.optJSONObject("artist");
                        Artist artist = gson.fromJson(object.toString(), Artist.class);
                        System.out.println(artist.toString());
                        artists.add(artist);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

}

    //+"-H \"Accept: application/json\" -H \"Content-Type: application/json\" -H \"Authorization: Bearer " + MainActivity.TOKEN;
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
            (Request.Method.GET, endpoint, null, response -> {
                Gson gson = new Gson();
                JSONArray jsonArray = response.optJSONArray("items");
                for (int n = 0; n < jsonArray.length(); n++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(n);
                        object = object.optJSONObject("track");
                        Song song = gson.fromJson(object.toString(), Song.class);
                        System.out.println(song.toString());
                        songs.add(song);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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