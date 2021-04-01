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
import com.example.meowtify.adapters.AdapterLibraryListAdd2;
import com.example.meowtify.models.Album;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Type;
import com.example.meowtify.services.AlbumService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumLibraryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumLibraryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    AdapterLibraryListAdd2 adapterAdd;
    AdapterLibraryList adapter;

    RecyclerView listaAlbum, listaRecomended;
    AlbumService albumService;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AlbumLibraryFragment() {
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
    public static AlbumLibraryFragment newInstance(String param1, String param2) {
        AlbumLibraryFragment fragment = new AlbumLibraryFragment();
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
        View v = inflater.inflate(R.layout.fragment_artist_library, container, false);
        albumService = new AlbumService(v.getContext());
        listaAlbum = v.findViewById(R.id.artistas_library);
        listaRecomended = v.findViewById(R.id.recomended_library);

        List<GeneralItem> album = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("7pA5KQnWxXWSHJv8g1wWQK", "album12", Type.album, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artista1", null),
                new GeneralItem("4sTehljxd3DNsjHWx3a64L", "album22", Type.album, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artista2", null),
                new GeneralItem("5uu6yCShtYnAp4qvrmQs72", "album32", Type.album, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artista3", null)
        ));
        List<GeneralItem> recomendedAlbum = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("51PpTmf21xMgbjdcirTTDa", "album12", Type.album, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artista1", null),
                new GeneralItem("4sTehljxd3DNsjHWx3a64L", "album22", Type.album, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artista2", null),
                new GeneralItem("5uu6yCShtYnAp4qvrmQs72", "album32", Type.album, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artista3", null)
        ));
        albumService.getUserSavedAlbums(this::updateAlbumByAPI, "ES", 10, 0);
        adapter = new AdapterLibraryList(album, getContext());
        listaAlbum.setAdapter(adapter);
        listaAlbum.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterAdd = new AdapterLibraryListAdd2(recomendedAlbum, getContext());
        listaRecomended.setAdapter(adapterAdd);
        listaRecomended.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    private void updateAlbumByAPI() {
        List<Album> savedAlbums = albumService.getUserSavedAlbums();
        List<GeneralItem> g = new ArrayList<>();
        for (Album a : savedAlbums) {
            System.out.println("pre boom" + a.toString());
            g.add(a.toGeneralItem());
        }
        adapter.setItems(g);
    }
}