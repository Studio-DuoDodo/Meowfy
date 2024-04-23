package com.example.meowtify.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meowtify.R;
import com.example.meowtify.adapters.AdapterMainList;
import com.example.meowtify.models.Album;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Playlist;
import com.example.meowtify.models.Song;
import com.example.meowtify.services.PlaylistService;
import com.example.meowtify.services.SongService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainFragment extends Fragment {
    final List<GeneralItem> defaultItem = new ArrayList<GeneralItem>(Arrays.asList(

            new GeneralItem("5tXPbKvuDsSgctH5Mlpn18", "Item11", null, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "Item12", null),
            new GeneralItem("7xsdr3YuARtJxqssk1m3Kq", "Item21", null, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "Item22", null),
            new GeneralItem("3ForlWAUJFtzxezcS47JmB", "Item31", null, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "Item32", null),
            new GeneralItem("6dJMlk3nncKD4y0wzuyhWr", "Item41", null, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "Item42", null)
    ));
    public List<AdapterMainList> adapters = new ArrayList<>(6);
    private SongService songService;
    private PlaylistService playlistService;
    private TextView missatgePersonalitzat;
    private RecyclerView list1, list2, list3, list4, list5;

    public MainFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        adapters = new ArrayList<>();

        playlistService = new PlaylistService(v.getContext());
        adapters.add(new AdapterMainList(defaultItem, v.getContext(), 400));
        adapters.add(new AdapterMainList(defaultItem, v.getContext(), 400));
        adapters.add(new AdapterMainList(defaultItem, v.getContext(), 400));
        adapters.add(new AdapterMainList(defaultItem, v.getContext(), 400));
        adapters.add(new AdapterMainList(defaultItem, v.getContext(), 400));
        missatgePersonalitzat = v.findViewById(R.id.missatgePersonalitzat);
        list1 = v.findViewById(R.id.listRecently);
        list2 = v.findViewById(R.id.listYourPlaylist);
        list3 = v.findViewById(R.id.listJumpBack);
        list4 = v.findViewById(R.id.listJumpBack2);
        list5 = v.findViewById(R.id.listRecommendedByDeveloper);
        int date = Integer.parseInt(new SimpleDateFormat("H", Locale.UK).format(new Date().getTime()));
        System.out.println(date);
        String s;
        if (8 > date) {
            s = "Too early";
        } else if (12 > date) {
            s = "Good morning";
        } else if (15 > date) {
            s = "Good noon";
        } else if (21 > date) {
            s = "Good afternoon";
        } else {
            s = "Good evening";
        }
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("SPOTIFY", 0);

        missatgePersonalitzat.setText(s + " " + sharedPreferences.getString("username", "Unknown user"));

        songService = new SongService(v.getContext());
        list1.setAdapter(adapters.get(0));
        list1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        list2.setAdapter(adapters.get(1));
        list2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        list3.setAdapter(adapters.get(2));
        list3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        list4.setAdapter(adapters.get(3));
        list4.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        list5.setAdapter(adapters.get(4));
        list5.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        songService.getRecentlyPlayedTracks(this::IntroduceRecentlyPlayedSongs);
        playlistService.getFeaturedPlayList(this::IntroduceFeaturedPlaylists);
        playlistService.getUserPlayLists(this::IntroduceMyPlaylists, 50, 0);
        playlistService.getNewReleases(this::IntroduceNewReleases, "JP", 10, 0);
        playlistService.getAPlayListByRef(this::IntroduceDevelopersPlaylist, "5tXPbKvuDsSgctH5Mlpn18");
        playlistService.getAPlayListByRef(this::IntroduceDevelopersPlaylist, "7xsdr3YuARtJxqssk1m3Kq");
        playlistService.getAPlayListByRef(this::IntroduceDevelopersPlaylist, "3ForlWAUJFtzxezcS47JmB");
        playlistService.getAPlayListByRef(this::IntroduceDevelopersPlaylist, "6dJMlk3nncKD4y0wzuyhWr");

        return v;
    }

    private void IntroduceRecentlyPlayedSongs() {
        List<Song> itemsSongs = songService.getSongs();
        List<GeneralItem> items21 = new ArrayList<>();
        System.out.println("Songs" + songService.getSongs().toString());
        for (Song p : itemsSongs
        ) {
            items21.add(p.toGeneralItem());
        }
        System.out.println("Items in param : = " + items21.toString());
        adapters.get(0).setItems(items21);
    }

    private void IntroduceMyPlaylists() {
        List<Playlist> itemsSongs = playlistService.getPlaylists();
        List<GeneralItem> items21 = new ArrayList<>();
        for (Playlist p : itemsSongs
        ) {
            items21.add(p.toGeneralItem());
        }
        System.out.println("Items in param : = " + items21.toString());
        adapters.get(1).setItems(items21);
        System.out.println("The list " + adapters.get(1).toString());
    }

    private void IntroduceDevelopersPlaylist() {
        List<Playlist> itemsSongs = playlistService.getDevelopersPlaylist();
        List<GeneralItem> items21 = new ArrayList<>();
        for (Playlist p : itemsSongs
        ) {
            items21.add(p.toGeneralItem());
        }
        System.out.println("Items in dev : = " + items21.toString());
        adapters.get(4).setItems(items21);
        System.out.println("The  dev list " + adapters.get(4).toString());
    }

    private void IntroduceFeaturedPlaylists() {
        List<Playlist> itemsSongs = playlistService.getFeaturedPlaylistsPlaylists();
        List<GeneralItem> items21 = new ArrayList<>();
        for (Playlist p : itemsSongs
        ) {
            items21.add(p.toGeneralItem());
        }
        System.out.println("Items in param : = " + items21.toString());
        adapters.get(2).setItems(items21);
        System.out.println("The list " + adapters.get(2).toString());
    }

    private void IntroduceNewReleases() {
        List<Album> itemsSongs = playlistService.getNewReleases();
        List<GeneralItem> items21 = new ArrayList<>();
        for (Album p : itemsSongs
        ) {
            items21.add(p.toGeneralItem());
        }
        System.out.println("Items in param for new releases : = " + items21.toString());
        adapters.get(3).setItems(items21);
        System.out.println("The list " + adapters.get(3).toString());
    }

}