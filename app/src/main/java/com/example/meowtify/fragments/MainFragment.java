package com.example.meowtify.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meowtify.services.PlaylistService;
import com.example.meowtify.R;
import com.example.meowtify.services.SongService;
import com.example.meowtify.adapters.AdapterMainList;
import com.example.meowtify.models.Album;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Playlist;
import com.example.meowtify.models.Song;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    private SongService songService;
    private PlaylistService playlistService;

    private TextView missatgePersonalitzat;
    private RecyclerView lista1, lista2, lista3, lista4,lista5;
    public  List<AdapterMainList> adapters = new ArrayList<>(6);

    final  List<GeneralItem> defaultItem = new ArrayList<GeneralItem>(Arrays.asList(

            new GeneralItem("5tXPbKvuDsSgctH5Mlpn18", "Item11", null, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "Item12", null),
            new GeneralItem("7xsdr3YuARtJxqssk1m3Kq", "Item21", null, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "Item22", null),
            new GeneralItem("3ForlWAUJFtzxezcS47JmB", "Item31", null, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "Item32", null),
            new GeneralItem("6dJMlk3nncKD4y0wzuyhWr", "Item41", null, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "Item42", null)
    ));

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
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
          adapters.add( new AdapterMainList(defaultItem, v.getContext(),400));
          adapters.add( new AdapterMainList(defaultItem, v.getContext(), 400));
          adapters.add( new AdapterMainList(defaultItem, v.getContext(), 400));
          adapters.add( new AdapterMainList(defaultItem, v.getContext(), 400));
          adapters.add( new AdapterMainList(defaultItem, v.getContext(), 400));




        missatgePersonalitzat = v.findViewById(R.id.missatgePersonalitzat);
        lista1 = v.findViewById(R.id.listaRecently);
        lista2 = v.findViewById(R.id.listaYourPlaylist);
        lista3 = v.findViewById(R.id.listaJumpBack);
        lista4 = v.findViewById(R.id.listaJumpBack2);
        lista5 = v.findViewById(R.id.listaRecomendedByDeveloper);


        int date = Integer.parseInt(new SimpleDateFormat("H", Locale.UK).format(new Date().getTime()));
        System.out.println(date);
        if (8 > date) {
            missatgePersonalitzat.setText("Too early");
        } else if (12 > date) {
            missatgePersonalitzat.setText("Good morning");
        } else if (15 > date) {
            missatgePersonalitzat.setText("God noon");
        } else if (21 > date) {
            missatgePersonalitzat.setText("Good afternoon");
        } else {
            missatgePersonalitzat.setText("Good evening");
        }
        songService = new SongService(v.getContext());
        lista1.setAdapter(adapters.get(0));
        lista1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        lista2.setAdapter(adapters.get(1));
        lista2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        lista3.setAdapter(adapters.get(2));
        lista3.setLayoutManager(new LinearLayoutManager(getContext(),  LinearLayoutManager.HORIZONTAL, false));
        lista4.setAdapter(adapters.get(3));
        lista4.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        lista5.setAdapter(adapters.get(4));
        lista5.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        songService.getRecentlyPlayedTracks(this::IntroduceRecentlyPlayedSongs);
        playlistService.getFeaturedPlayList(this::IntroduceFeaturedPlaylists);
        playlistService.getUserPlayLists(this::IntroduceMyPlaylists,50,0);
        playlistService.getNewReleases(this::IntroduceNewReleases,"JP",10,0);
        playlistService.getAPlayListByRef(this::IntroduceDevelopersPlaylist,"5tXPbKvuDsSgctH5Mlpn18");
        playlistService.getAPlayListByRef(this::IntroduceDevelopersPlaylist,"7xsdr3YuARtJxqssk1m3Kq");
        playlistService.getAPlayListByRef(this::IntroduceDevelopersPlaylist,"3ForlWAUJFtzxezcS47JmB");
        playlistService.getAPlayListByRef(this::IntroduceDevelopersPlaylist,"6dJMlk3nncKD4y0wzuyhWr");

 return v;
    }

    private void IntroduceRecentlyPlayedSongs() {
        List<Song> itemsSongs = songService.getSongs();
        List<GeneralItem> items21 = new ArrayList<>();
        System.out.println("Songs" + songService.getSongs().toString());
        for (Song p: itemsSongs
        ) {
            items21.add(p.toGeneralItem());
        }
        System.out.println("Items in param : = " + items21.toString());
        adapters.get(0).setItems(items21);
    }
    private void IntroduceMyPlaylists() {
        List<Playlist> itemsSongs = playlistService.getPlaylists();
        List<GeneralItem> items21 = new ArrayList<>();
        for (Playlist p: itemsSongs
        ) {
            items21.add(p.toGeneralItem());
        }
        System.out.println("Items in param : = " + items21.toString());
        adapters.get(1).setItems(items21);
        System.out.println("The list " + adapters.get(1).toString());
    }
    private void IntroduceDevelopersPlaylist() {
        List<Playlist> itemsSongs= playlistService.getDevelopersPlaylist();
        List<GeneralItem> items21 = new ArrayList<>();
        for (Playlist p: itemsSongs
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
        for (Playlist p: itemsSongs
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
        for (Album p: itemsSongs
        ) {
            items21.add(p.toGeneralItem());
        }
        System.out.println("Items in param for new releases : = " + items21.toString());
        adapters.get(3).setItems(items21);
        System.out.println("The list " + adapters.get(3).toString());
    }

//todo limpiar codigo
}