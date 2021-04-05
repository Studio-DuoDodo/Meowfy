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

import com.example.meowtify.R;
import com.example.meowtify.adapters.AdapterMainList;
import com.example.meowtify.adapters.AdapterSongsList;
import com.example.meowtify.models.Album;
import com.example.meowtify.models.Artist;
import com.example.meowtify.models.Followers;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Type;
import com.example.meowtify.services.ArtistService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArtistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView imageArtist;
    TextView nameArtist, subtitelArtist;
    Button buttonShuffel, buttonFolllow;
    RecyclerView songs, albums, reletedArtist;
    Artist artist;
    AdapterSongsList adapterSongs;
    AdapterMainList adapterArtist;
    ArtistService artistService;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ArtistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArtistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArtistFragment newInstance(String param1, String param2) {
        ArtistFragment fragment = new ArtistFragment();
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
        View v = inflater.inflate(R.layout.fragment_artist, container, false);

        //artistService = new PlaylistService(v.getContext());
        imageArtist = v.findViewById(R.id.image_artist2);
        nameArtist = v.findViewById(R.id.name_artist2);
        subtitelArtist = v.findViewById(R.id.subname_artist2);
        buttonShuffel = v.findViewById(R.id.shuffel_artist);
        buttonFolllow = v.findViewById(R.id.follow_artist);
        songs = v.findViewById(R.id.songs);
        albums = v.findViewById(R.id.albums);
        reletedArtist = v.findViewById(R.id.related);
        artistService = new ArtistService(v.getContext());
        artist = new Artist(new Followers(), null, 10, 10, null, "FalkKonE", null, "7vlM4bn4gPubcmntK8UBp0", Type.artist, null, null);
        Picasso.with(v.getContext()).load("http://i.imgur.com/DvpvklR.png").into(imageArtist);
        Bundle b = getArguments();
        if (b != null) {
            GeneralItem generalItem = (GeneralItem) b.getSerializable("generalItem");
            artistService.getArtistByid(generalItem.getId(), this::updateArtistByAPI);
            artistService.getRelatedArtists(this::updateRelatedArtistsByAPI, generalItem.getId());
            artistService.getArtistAlbums(this::updateArtistAlbumsByAPI, generalItem.getId(), "ES", 30, 0);
        }

        nameArtist.setText(artist.getName());
        String subtitel = artist.getFollowers().getTotal() + " FOLLOWERS";
        subtitelArtist.setText(subtitel);

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
                String text = buttonFolllow.getText().toString();
                System.out.println(text);
                if (text.equals("follow")) {

                    text = "unfollow";
                } else if (text.equals("unfollow")) {

                    text = "follow";
                }
                buttonFolllow.setText(text);
            }
        });

        List<GeneralItem> songsList = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("7vlM4bn4gPubcmntK8UBp0", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "40/100", null),
                new GeneralItem("6Ynd3UhOWONEzAC2PtWGXw", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "40/100", null),
                new GeneralItem("0CeV1QZH5267PmzIpqRZmS", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "40/100", null),
                new GeneralItem("1wuW57ULEfM9pgCYIhROMs", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "40/100", null),
                new GeneralItem("7vlM4bn4gPubcmntK8UBp0", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "40/100", null),
                new GeneralItem("6Ynd3UhOWONEzAC2PtWGXw", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "40/100", null),
                new GeneralItem("0CeV1QZH5267PmzIpqRZmS", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "40/100", null),
                new GeneralItem("1wuW57ULEfM9pgCYIhROMs", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "40/100", null),
                new GeneralItem("7vlM4bn4gPubcmntK8UBp0", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "40/100", null),
                new GeneralItem("6Ynd3UhOWONEzAC2PtWGXw", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "40/100", null),
                new GeneralItem("0CeV1QZH5267PmzIpqRZmS", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "40/100", null),
                new GeneralItem("1wuW57ULEfM9pgCYIhROMs", "Beliver", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "40/100", null)
        ));
        if (songsList.size() > 10) songsList = songsList.subList(0, 10);

        adapterSongs = new AdapterSongsList(songsList, getContext(), 130);
        songs.setAdapter(adapterSongs);
        songs.setLayoutManager(new LinearLayoutManager(getContext()));

        List<GeneralItem> albumsList = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("7vlM4bn4gPubcmntK8UBp0", "Beliver", Type.album, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "2020", null),
                new GeneralItem("6Ynd3UhOWONEzAC2PtWGXw", "Beliver", Type.album, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "2019", null),
                new GeneralItem("0CeV1QZH5267PmzIpqRZmS", "Beliver", Type.album, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "2017", null),
                new GeneralItem("1wuW57ULEfM9pgCYIhROMs", "Beliver", Type.album, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "2016", null)
        ));

        adapterSongs = new AdapterSongsList(albumsList, getContext(), 200);
        albums.setAdapter(adapterSongs);
        albums.setLayoutManager(new LinearLayoutManager(getContext()));

        List<GeneralItem> artistList = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("7vlM4bn4gPubcmntK8UBp0", "Beliver", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", null, null),
                new GeneralItem("6Ynd3UhOWONEzAC2PtWGXw", "Beliver", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", null, null),
                new GeneralItem("0CeV1QZH5267PmzIpqRZmS", "Beliver", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", null, null),
                new GeneralItem("1wuW57ULEfM9pgCYIhROMs", "Beliver", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", null, null)
        ));

        adapterArtist = new AdapterMainList(artistList, getContext(), 250);
        reletedArtist.setAdapter(adapterArtist);
        reletedArtist.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        return v;
    }

    private void updateArtistAlbumsByAPI() {
        List<Album> a = artistService.getAlbumsLastArtist();
        List<GeneralItem> generalItemList = new ArrayList<>();

        for (Album album : a) {
            generalItemList.add(album.toGeneralItem());
        }
        adapterSongs.setItems(generalItemList);
    }

    private void updateRelatedArtistsByAPI() {
        List<Artist> a = artistService.getRelatedArtist();
        List<GeneralItem> generalItemList = new ArrayList<>();
        for (Artist art : a) {
            generalItemList.add(art.toGeneralItem());
        }
        adapterArtist.setItems(generalItemList);
    }


    private void updateArtistByAPI() {
        Artist a = artistService.getLastSearchedArtist();
        Picasso.with(getContext()).load(a.images.get(0).url).into(imageArtist);
        nameArtist.setText(a.getName());
        subtitelArtist.setText(a.getFollowers().getTotal() + " FOLLOWERS");


    }
}