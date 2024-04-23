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
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Playlist;
import com.example.meowtify.models.Type;
import com.example.meowtify.services.PlaylistService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PlaylistLibraryFragment extends Fragment {


    RecyclerView listPlaylist;
    PlaylistService playlistService;
    AdapterLibraryList adapter;

    public PlaylistLibraryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_playlist_library, container, false);

        listPlaylist = v.findViewById(R.id.list_library);
        playlistService = new PlaylistService(v.getContext());
        playlistService.getUserPlayLists(this::IntroduceMyPlaylists, 50, 0);

        List<GeneralItem> items = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("5tXPbKvuDsSgctH5Mlpn18", "Create playlist", Type.playlist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", null, null),
                new GeneralItem("7xsdr3YuARtJxqssk1m3Kq", "Item12", Type.playlist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "creator12", null),
                new GeneralItem("3ForlWAUJFtzxezcS47JmB", "Item22", Type.playlist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "creator12", null),
                new GeneralItem("6dJMlk3nncKD4y0wzuyhWr", "Item32", Type.playlist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "creator12", null)
        ));

        adapter = new AdapterLibraryList(items, getContext());
        listPlaylist.setAdapter(adapter);
        listPlaylist.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    private void IntroduceMyPlaylists() {
        List<Playlist> itemsSongs = playlistService.getPlaylists();
        List<GeneralItem> items21 = new ArrayList<>();
        items21.add(new GeneralItem("id", "Create playlist", Type.playlist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", null, null));
        for (Playlist p : itemsSongs
        ) {
            items21.add(p.toGeneralItem());
        }
        System.out.println("Items in param : = " + items21.toString());
        adapter.setItems(items21);
        System.out.println("The list " + adapter.toString());
    }
}