package com.example.meowtify.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meowtify.R;
import com.example.meowtify.adapters.AdapterLibraryList;
import com.example.meowtify.adapters.AdapterLibraryListAdd1;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Type;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView listaArtist, listaRecomended;

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

        listaArtist = v.findViewById(R.id.artistas_library);
        listaRecomended = v.findViewById(R.id.recomended_library);

        List<GeneralItem> artist = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("id", "artist12", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "1", null),
                new GeneralItem("id", "artist22", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "2", null),
                new GeneralItem("id", "artist32", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "3", null)
        ));
        List<GeneralItem> recomendedArtist = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("id", "artist12", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "1", null),
                new GeneralItem("id", "artist22", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "2", null),
                new GeneralItem("id", "artist32", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "3", null)
        ));

        AdapterLibraryList adapter = new AdapterLibraryList(artist, getContext());
        listaArtist.setAdapter(adapter);
        listaArtist.setLayoutManager(new LinearLayoutManager(getContext()));

        AdapterLibraryListAdd1 adapterAdd = new AdapterLibraryListAdd1(recomendedArtist, getContext());
        listaRecomended.setAdapter(adapterAdd);
        listaRecomended.setLayoutManager(new LinearLayoutManager(getContext()));
        
        return v;
    }
}