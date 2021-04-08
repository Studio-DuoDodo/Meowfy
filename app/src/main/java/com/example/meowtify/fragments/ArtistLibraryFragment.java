package com.example.meowtify.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meowtify.R;
import com.example.meowtify.adapters.AdapterLibraryList;
import com.example.meowtify.adapters.AdapterLibraryListAdd1;
import com.example.meowtify.models.Artist;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Type;
import com.example.meowtify.services.ArtistService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArtistLibraryFragment extends Fragment {



    RecyclerView listaArtist, listaRecomended;
ArtistService artistService;
    AdapterLibraryList adapter;
    public ArtistLibraryFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_artist_library, container, false);
artistService= new ArtistService(v.getContext());
artistService.getUserFollowedArtists(this::updateFollowedArtistsByAPI,30);
        listaArtist = v.findViewById(R.id.artistas_library);
        listaRecomended = v.findViewById(R.id.recomended_library);

        List<GeneralItem> artist = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("7vlM4bn4gPubcmntK8UBp0", "artist12", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "songs 1", null),
                new GeneralItem("06F1MiFx0dHLHEPQBIrcr9", "artist22", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "songs 2", null),
                new GeneralItem("0blbVefuxOGltDBa00dspv", "artist32", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "songs 3", null)
        ));
        List<GeneralItem> recomendedArtist = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("5t7eQ2d1UmzfIr9oWQ538Y", "artist12", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "songs 1", null),
                new GeneralItem("06F1MiFx0dHLHEPQBIrcr9", "artist22", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "songs 2", null),
                new GeneralItem("0blbVefuxOGltDBa00dspv", "artist32", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "songs 3", null)
        ));

          adapter = new AdapterLibraryList(artist, getContext());
        listaArtist.setAdapter(adapter);
        listaArtist.setLayoutManager(new LinearLayoutManager(getContext()));

        AdapterLibraryListAdd1 adapterAdd = new AdapterLibraryListAdd1(recomendedArtist, getContext());
        listaRecomended.setAdapter(adapterAdd);
        listaRecomended.setLayoutManager(new LinearLayoutManager(getContext()));
        
        return v;
    }

    private void updateFollowedArtistsByAPI() {
    List<Artist> a =artistService.getUserFollowedArtists();
   List<GeneralItem> generalItemList= new ArrayList<>();
        for (Artist artist:a) {
            generalItemList.add(artist.toGeneralItem());
        }
    adapter.setItems(generalItemList);

    }
}