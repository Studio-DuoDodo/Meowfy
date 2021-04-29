package com.example.meowtify.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meowtify.R;
import com.example.meowtify.Utilitis;
import com.example.meowtify.adapters.AdapterSongsList;
import com.example.meowtify.models.Album;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Type;
import com.example.meowtify.services.AlbumService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class AlbumFragment extends Fragment {

    ImageView imagePlaylist;
    TextView nameAlbum, subtitelAlbum;
    Button buttonShuffel;
    ImageButton buttonFavorite;
    RecyclerView songs;

    AdapterSongsList adapterSongs;
    Album album;
    AlbumService albumService;
    int tagFavorite;

    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_album, container, false);
        albumService = new AlbumService(v.getContext());
        imagePlaylist = v.findViewById(R.id.image_album);
        nameAlbum = v.findViewById(R.id.name_album2);
        subtitelAlbum = v.findViewById(R.id.subname_album);
        buttonShuffel = v.findViewById(R.id.shuffel_album);
        buttonFavorite = v.findViewById(R.id.favorits_album2);
        songs = v.findViewById(R.id.songs);

        Bundle b = getArguments();
        if (b != null) {
            GeneralItem generalItem = (GeneralItem) b.getSerializable("generalItem");

            albumService.getAlbumByRef(this::updateAlbumByAPI, generalItem.getId());
        }
        buttonShuffel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GeneralItem generalItem = album.toGeneralItem();

                generalItem.setId(album.getId());
                generalItem.setType(Type.track);
                generalItem.setExtra1(String.valueOf(new Random().nextInt(adapterSongs.getItemCount())));
                generalItem.setExtra2(Type.album.toString());

                Utilitis.navigationToAAP(generalItem, getContext());
            }
        });
        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (buttonFavorite.getTag(R.string.albunes).equals("1")) {
                    buttonFavorite.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_favorite_24));
                    buttonFavorite.setTag(R.string.albunes,"0");

                    albumService.saveAlbumToUserLibrary(album);
                } else {
                    buttonFavorite.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                    buttonFavorite.setTag(R.string.albunes,"1");

                    albumService.unsaveAnAlbum(album);
                }

            }
        });

        List<GeneralItem> songsList = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("7vlM4bn4gPubcmntK8UBp0", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artist11", null),
                new GeneralItem("6Ynd3UhOWONEzAC2PtWGXw", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artist12", null),
                new GeneralItem("0CeV1QZH5267PmzIpqRZmS", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artist13", null),
                new GeneralItem("1wuW57ULEfM9pgCYIhROMs", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artist14", null)
        ));

        //todo: modificar lo que se pasa como id
        adapterSongs = new AdapterSongsList(songsList, getContext(), 130, Type.album, null);
        songs.setAdapter(adapterSongs);
        songs.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    private void updateAlbumByAPI() {
        Album currentAlbum = albumService.getLastAlbum();
        final boolean[] bool = new boolean[1];
        albumService.checkIfTheUserSavedAAlbum(() -> {
                    if(albumService.isLastCheck()) {
                        buttonFavorite.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_favorite_24));
                        buttonFavorite.setTag(R.string.albunes,"0");
                    }else {
                        buttonFavorite.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                        buttonFavorite.setTag(R.string.albunes, "1");
                    }}
                ,currentAlbum.getId());
        nameAlbum.setText(currentAlbum.getName());
        System.out.println("last album has" + currentAlbum.toString());
        if (currentAlbum.getImages() != null)
            Picasso.with(getContext()).load(currentAlbum.getImages().get(0).url).into(imagePlaylist);
        String subtitel = "BY " + currentAlbum.getArtistNames() + " · " + currentAlbum.getTotalTracks() + " TRACKS";

        subtitelAlbum.setText(subtitel);
        album=currentAlbum;
        adapterSongs.setItems(currentAlbum.getSongsConverted());
        adapterSongs.setIdList(album.getId());
        //});

    }
}