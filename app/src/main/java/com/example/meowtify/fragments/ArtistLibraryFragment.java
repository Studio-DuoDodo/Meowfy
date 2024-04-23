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
import com.example.meowtify.adapters.AdapterLibraryListAdd1;
import com.example.meowtify.models.Artist;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Song;
import com.example.meowtify.models.Type;
import com.example.meowtify.services.ArtistService;
import com.example.meowtify.services.SongService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ArtistLibraryFragment extends Fragment {

    AdapterLibraryListAdd1 adapterRecommended;

    RecyclerView listArtist, listRecommended;
    ArtistService artistService;
    AdapterLibraryList adapter;
    List<Artist> followedArtists;
    List<String> followedArtistsIds = new ArrayList<>();

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
        artistService = new ArtistService(v.getContext());
        SongService songService = new SongService(v.getContext());

        artistService.getUserFollowedArtists(this::updateFollowedArtistsByAPI, 30);
        songService.getRecentlyPlayedTracks(() -> {
            List<Song> songs = songService.getSongs();
            Random random = new Random();
            if (songs.size() != 0)
                artistService.getRelatedArtists(this::updateRecommendedArtistsByAPI, songs.get((random.nextInt(songs.size()))).getArtists().get(0).getId());
        });
        listArtist = v.findViewById(R.id.artists_library);
        listRecommended = v.findViewById(R.id.recommended_library);

        List<GeneralItem> artist = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("7vlM4bn4gPubcmntK8UBp0", "artist12", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "followers 1", null),
                new GeneralItem("06F1MiFx0dHLHEPQBIrcr9", "artist22", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "followers 2", null),
                new GeneralItem("0blbVefuxOGltDBa00dspv", "artist32", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "followers 3", null)
        ));
        List<GeneralItem> recommendedArtist = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("5t7eQ2d1UmzfIr9oWQ538Y", "artist12", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "followers 1", null),
                new GeneralItem("06F1MiFx0dHLHEPQBIrcr9", "artist22", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "followers 2", null),
                new GeneralItem("0blbVefuxOGltDBa00dspv", "artist32", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "followers 3", null)
        ));

        adapter = new AdapterLibraryList(artist, getContext());
        listArtist.setAdapter(adapter);
        listArtist.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterRecommended = new AdapterLibraryListAdd1(recommendedArtist, getContext());
        listRecommended.setAdapter(adapterRecommended);
        listRecommended.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    private void updateFollowedArtistsByAPI() {
        followedArtists = artistService.getUserFollowedArtists();
        List<GeneralItem> generalItemList = new ArrayList<>();
        for (Artist artist : followedArtists) {
            generalItemList.add(artist.toGeneralItem());
            followedArtistsIds.add(artist.getId());
        }
        adapter.setItems(generalItemList);

    }

    private void updateRecommendedArtistsByAPI() {
        List<Artist> a = artistService.getRelatedArtist();
        List<GeneralItem> generalItemList = new ArrayList<>();
        for (Artist artist : a) {
            System.out.println("contiene artist f" + followedArtistsIds.contains(artist.getId()));
            if (!followedArtistsIds.contains(artist.getId()))
                generalItemList.add(artist.toGeneralItem());
        }
        adapterRecommended.setItems(generalItemList);

    }
}