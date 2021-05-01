package com.example.meowtify.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meowtify.R;
import com.example.meowtify.adapters.AdapterLibraryList;
import com.example.meowtify.adapters.AdapterLibraryListAdd2;
import com.example.meowtify.models.Album;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Song;
import com.example.meowtify.models.Type;
import com.example.meowtify.services.AlbumService;
import com.example.meowtify.services.SongService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlbumLibraryFragment extends Fragment {
    AdapterLibraryListAdd2 adapterAdd;
    AdapterLibraryList adapter;

    RecyclerView listaAlbum, listaRecomended;
    AlbumService albumService;
    List<Album> savedAlbums;

    public AlbumLibraryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_artist_library, container, false);
        albumService = new AlbumService(v.getContext());
        listaAlbum = v.findViewById(R.id.artistas_library);
        listaRecomended = v.findViewById(R.id.recomended_library);

        List<GeneralItem> album = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("7pA5KQnWxXWSHJv8g1wWQK", "album12", Type.album, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artista1", null),
                new GeneralItem("4sTehljxd3DNsjHWx3a64L", "album22", Type.album, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artista2", null),
                new GeneralItem("5uu6yCShtYnAp4qvrmQs72", "album32", Type.album, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artista3", null)
        ));
        List<GeneralItem> recomendedAlbum = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("51PpTmf21xMgbjdcirTTDa", "album12", Type.album, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artista1", null),
                new GeneralItem("4sTehljxd3DNsjHWx3a64L", "album22", Type.album, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artista2", null),
                new GeneralItem("5uu6yCShtYnAp4qvrmQs72", "album32", Type.album, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artista3", null)
        ));
        albumService.getUserSavedAlbums(this::updateAlbumByAPI, "ES", 10, 0);
        adapter = new AdapterLibraryList(album, getContext());
        listaAlbum.setAdapter(adapter);
        listaAlbum.setLayoutManager(new LinearLayoutManager(getContext()));
        SongService songService = new SongService(getContext());
        songService.getRecentlyPlayedTracks(() -> {
            List<Song> songs = songService.getSongs();
            List<Album> albums = new ArrayList<>();
            for (Song s : songs) {
                //    if ()
                albums.add(s.getAlbum());


            }
        });
        adapterAdd = new AdapterLibraryListAdd2(recomendedAlbum, getContext());
        listaRecomended.setAdapter(adapterAdd);
        listaRecomended.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    private void updateAlbumByAPI() {
        List<Album> savedAlbums = albumService.getUserSavedAlbums();
        List<GeneralItem> g = new ArrayList<>();
        for (Album a : savedAlbums) {
            System.out.println("pre boom" + a.toString());
            g.add(a.toGeneralItem());
        }
        adapter.setItems(g);
    }
}