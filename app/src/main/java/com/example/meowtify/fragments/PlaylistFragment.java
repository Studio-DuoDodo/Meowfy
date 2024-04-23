package com.example.meowtify.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meowtify.R;
import com.example.meowtify.Utilitis;
import com.example.meowtify.activities.MainActivity;
import com.example.meowtify.adapters.AdapterSongsList;
import com.example.meowtify.models.Followers;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Playlist;
import com.example.meowtify.models.Song;
import com.example.meowtify.models.Type;
import com.example.meowtify.models.User;
import com.example.meowtify.services.PlaylistService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PlaylistFragment extends Fragment {


    ImageView imagePlaylist;
    TextView namePlaylist, subtitelPlaylist;
    Button buttonShuffel, buttonFolllow;
    RecyclerView songs;
    Playlist playlist;
    AdapterSongsList adapterSongs;
    PlaylistService playlistService;


    public PlaylistFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_playlist, container, false);

        playlistService = new PlaylistService(v.getContext());
        imagePlaylist = v.findViewById(R.id.image_playlist);
        namePlaylist = v.findViewById(R.id.name_playlist);
        subtitelPlaylist = v.findViewById(R.id.subname_playlist);
        buttonShuffel = v.findViewById(R.id.shuffle_playlist);
        buttonFolllow = v.findViewById(R.id.follow_playlist);
        songs = v.findViewById(R.id.songs);
        playlist = new Playlist(false, new Followers(), null, null, null, null, null, new User("default"), true, null, null, null);
        Picasso.with(v.getContext()).load("http://i.imgur.com/DvpvklR.png").into(imagePlaylist);
        Bundle b = getArguments();
        if (b != null) {
            GeneralItem generalItem = (GeneralItem) b.getSerializable("generalItem");
            playlist.setId(generalItem.getId());
            playlistService.getAPlayListByRef(this::updatePlaylistByAPI, generalItem.getId());
        }

        namePlaylist.setText(playlist.getName());
        String subtitel = "BY " + playlist.getOwner().getDisplayName() + " · " + playlist.getFollowers().getTotal() + " FOLLOWERS";
        subtitelPlaylist.setText(subtitel);

        buttonShuffel.setOnClickListener(view -> {
            GeneralItem generalItem = playlist.toGeneralItem();

            generalItem.setId(playlist.getId());
            generalItem.setType(Type.track);
            generalItem.setExtra1(String.valueOf(new Random().nextInt(adapterSongs.getItemCount())));
            generalItem.setExtra2(Type.playlist.toString());

            Utilitis.navigationToAAP(generalItem, getContext());
        });
        buttonFolllow.setOnClickListener(view -> {
            PlaylistService playlistService = new PlaylistService(v.getContext());
            System.out.println("ID to follow" + " = " + playlist.getId());
            String text = buttonFolllow.getText().toString();
            System.out.println(text);
            if (text.equals("follow")) {
                playlistService.followAPlaylist(playlist);
                text = "unfollow";
            } else if (text.equals("unfollow")) {
                text = "follow";
                playlistService.unfollowAPlaylist(playlist);

            }
            buttonFolllow.setText(text);
        });

        List<GeneralItem> songsList = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("7vlM4bn4gPubcmntK8UBp0", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artist11", null),
                new GeneralItem("6Ynd3UhOWONEzAC2PtWGXw", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artist12", null),
                new GeneralItem("0CeV1QZH5267PmzIpqRZmS", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artist13", null),
                new GeneralItem("1wuW57ULEfM9pgCYIhROMs", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artist14", null)
        ));

        adapterSongs = new AdapterSongsList(songsList, getContext(), 130, Type.playlist, playlist.getId());
        songs.setAdapter(adapterSongs);
        songs.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    public void updatePlaylistByAPI() {
        List<Playlist> playlists = playlistService.getDevelopersPlaylist();
        final boolean[] bool = new boolean[1];
        playlistService.checkIfTheUserFollowsAPlaylist(() -> {
            bool[0] = playlistService.isLastCheck();
            System.out.println("the callback bool is" + bool);
            Toast.makeText(getView().getContext(), "\"the callback bool is\" + bool", Toast.LENGTH_SHORT).show();
            if (bool[0]) {
                buttonFolllow.setText("unfollow");
            } else buttonFolllow.setText("follow");
            List<GeneralItem> generalItemList = new ArrayList<>();
            for (Song s : playlists.get(0).getSongs()) {
                generalItemList.add(s.toGeneralItem());
            }
            playlist = playlists.get(0);
            namePlaylist.setText(playlist.getName());
            if (playlist.getImages().length != 0)
                Picasso.with(getView().getContext()).load(playlist.getImages()[0].url).into(imagePlaylist);
            else
                Picasso.with(getView().getContext()).load("https://depor.com/resizer/y0QpdzhnMuUBnXCguq_9y_MOiFs=/1200x675/smart/filters:format(jpeg):quality(75)/cloudfront-us-east-1.images.arcpublishing.com/elcomercio/RGKQY6IKM5GQ3N55KHVWVKSQI4.png").into(imagePlaylist);
            String subtitel = "BY " + playlist.getOwner().getDisplayName() + " · " + playlist.getFollowers().getTotal() + " FOLLOWERS";
            subtitelPlaylist.setText(subtitel);
            adapterSongs.setItems(generalItemList);
        }, playlists.get(0).getId());
        MainActivity.inReproductorForFirstTime = true;
    }
}
