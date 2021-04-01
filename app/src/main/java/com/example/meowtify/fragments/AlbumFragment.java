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
import com.example.meowtify.adapters.AdapterSongsList;
import com.example.meowtify.models.Album;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Type;
import com.example.meowtify.services.AlbumService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ImageView imagePlaylist;
    TextView nameAlbum, subtitelAlbum;
    Button buttonShuffel;
    ImageButton buttonFavorite;
    RecyclerView songs;

    AdapterSongsList adapterSongs;
    Album album;
    AlbumService albumService;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AlbumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlbumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlbumFragment newInstance(String param1, String param2) {
        AlbumFragment fragment = new AlbumFragment();
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

            //todo: get the album from the api with the id of generalItem.
            albumService.getAlbumByRef(this::updateAlbumByAPI, generalItem.getId());
        }

        //todo: una vez ya se coja el album de la api descomentar estas lineas
        /*Picasso.with(getContext()).load(album.getImages().get(0).url).
                resize(500, 500).into(imagePlaylist);
        nameAlbum.setText(album.getName());
        String subtitel = "album by "+ album.getArtists() + " · " + album.getReleaseDate();
        subtitelAlbum.setText(subtitel);*/

        buttonShuffel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: do funcion navigation to reproductor
            }
        });
        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (buttonFavorite.getDrawable() == getContext().getDrawable(R.drawable.ic_baseline_favorite_border_24)) {
                        buttonFavorite.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_favorite_24));

                        //todo: add to the favorit list of albums
                    } else {
                        buttonFavorite.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                        //todo: delete of the favorit list of albums
                    }
                }
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

    private void updateAlbumByAPI() {
        Album currentAlbum = albumService.getLastAlbum();
        final boolean[] bool = new boolean[1];
        //  albumService.checkIfTheUserFollowsAPlaylist(()->{
            /*bool[0] = playlistService.isLastCheck();
            System.out.println("the callback bool is" + bool);
            Toast.makeText(getView().getContext(), "\"the callback bool is\" + bool", Toast.LENGTH_SHORT).show();
            if (bool[0]){
                buttonFolllow.setText("unfollow");
            }else buttonFolllow.setText("follow");
            List<GeneralItem> generalItemList = new ArrayList<>();
            for (Song s : playlists.get(0).getSongs()) {
                generalItemList.add(s.toGeneralItem());
            } */
        nameAlbum.setText(currentAlbum.getName());
        System.out.println("last album has" + currentAlbum.toString());
        if (currentAlbum.getImages() != null)
            Picasso.with(getContext()).load(currentAlbum.getImages().get(0).url).into(imagePlaylist);
        String subtitel = "BY " + currentAlbum.getArtistNames() + " · " + currentAlbum.getTotalTracks() + " TRACKS";

        subtitelAlbum.setText(subtitel);

        adapterSongs.setItems(currentAlbum.getSongsConverted());
        //});

    }
}