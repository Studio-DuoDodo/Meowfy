package com.example.meowtify.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meowtify.PlaylistService;
import com.example.meowtify.R;
import com.example.meowtify.adapters.AdapterSongsList;
import com.example.meowtify.models.Followers;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Playlist;
import com.example.meowtify.models.Song;
import com.example.meowtify.models.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaylistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaylistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView imagePlaylist;
    TextView namePlaylist, subtitelPlaylist;
    Button buttonShuffel, buttonFolllow;
    RecyclerView songs;
    Playlist playlist;
    AdapterSongsList adapterSongs;
    PlaylistService playlistService;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlaylistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlaylistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaylistFragment newInstance(String param1, String param2) {
        PlaylistFragment fragment = new PlaylistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_playlist, container, false);
        playlistService = new PlaylistService(v.getContext());
        imagePlaylist = v.findViewById(R.id.image_playlist);
        namePlaylist = v.findViewById(R.id.name_playlist);
        subtitelPlaylist = v.findViewById(R.id.subname_playlist);
        buttonShuffel = v.findViewById(R.id.shuffel_playlist);
        buttonFolllow = v.findViewById(R.id.follow_playlist);
        songs = v.findViewById(R.id.songs);
        playlist = new Playlist(false, new Followers(), null, null, null, null, null, null, true, null, null, null);

        Bundle b = getArguments();
        if (b != null) {
            GeneralItem generalItem = (GeneralItem) b.getSerializable("generalItem");
            playlistService.getAPlayListByRef(this::updatePlaylistByAPI, generalItem.getId());
        }

        namePlaylist.setText(playlist.getName());
        String subtitel = "BY " + playlist.getOwner() + " · " + playlist.getFollowers().getTotal() + " FOLLOWERS";
        subtitelPlaylist.setText(subtitel);

        buttonShuffel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: do funcion navigation
            }
        });
        buttonFolllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: add to the follow playlist
                PlaylistService playlistService = new PlaylistService(v.getContext());
                System.out.println("ID to follow" + " = " + playlist.getId());
               String text = buttonFolllow.getText().toString();
                System.out.println(text);
               if (text.equals("follow")){
                   playlistService.followAPlaylist(playlist);
                  text="unfollow";
               }else if (text.equals("unfollow")){
                   text="follow";
                   playlistService.unfollowAPlaylist(playlist);

               }
                buttonFolllow.setText(text);
            }
        });

        List<GeneralItem> songsList = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("7vlM4bn4gPubcmntK8UBp0", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artist11", null),
                new GeneralItem("6Ynd3UhOWONEzAC2PtWGXw", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artist12", null),
                new GeneralItem("0CeV1QZH5267PmzIpqRZmS", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artist13", null),
                new GeneralItem("1wuW57ULEfM9pgCYIhROMs", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artist14", null)
        ));

        adapterSongs = new AdapterSongsList(songsList, getContext());
        songs.setAdapter(adapterSongs);
        songs.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    public void updatePlaylistByAPI() {
        List<Playlist> playlists = playlistService.getDevelopersPlaylist();
        List<GeneralItem> generalItemList = new ArrayList<>();
        for (Song s : playlists.get(0).getSongs()) {
            generalItemList.add(s.toGeneralItem());
        }
        playlist = playlists.get(0);
        namePlaylist.setText(playlist.getName());
        String subtitel = "BY " + playlist.getOwner() + " · " + playlist.getFollowers().getTotal() + " FOLLOWERS";
        subtitelPlaylist.setText(subtitel);

        adapterSongs.setItems(generalItemList);
    }
}
