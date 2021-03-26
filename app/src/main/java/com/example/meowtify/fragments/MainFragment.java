package com.example.meowtify.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.meowtify.R;
import com.example.meowtify.adapters.AdapterMainList;
import com.example.meowtify.models.GeneralItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private TextView missatgePersonalitzat;
    private RecyclerView lista1, lista2, lista3, lista4;
    private AdapterMainList adapter;
    private List<GeneralItem> items = new ArrayList<GeneralItem>();

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
        if (getArguments() != null) {
           
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        missatgePersonalitzat = v.findViewById(R.id.missatgePersonalitzat);
        lista1 = v.findViewById(R.id.listaRecently);
        lista2 = v.findViewById(R.id.listaYourPlaylist);
        lista3 = v.findViewById(R.id.listaJumpBack);
        lista4 = v.findViewById(R.id.listaJumpBack2);

        items = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("Item11", "subItem11", "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2"),
                new GeneralItem("Item21", "subItem21", "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2"),
                new GeneralItem("Item31", "subItem31", "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2")
        ));
        adapter = new AdapterMainList(items, getContext());
        lista1.setAdapter(adapter);
        lista1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        items = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("Item12", "subItem12", "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2"),
                new GeneralItem("Item22", "subItem22", "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2"),
                new GeneralItem("Item32", "subItem32", "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2")
        ));
        adapter = new AdapterMainList(items, getContext());
        lista2.setAdapter(adapter);
        lista2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        items = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("Item13", "subItem13", "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2"),
                new GeneralItem("Item23", "subItem23", "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2"),
                new GeneralItem("Item33", "subItem33", "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2")
        ));
        adapter = new AdapterMainList(items, getContext());
        lista3.setAdapter(adapter);
        lista3.setLayoutManager(new LinearLayoutManager(getContext(),  LinearLayoutManager.HORIZONTAL, false));

        items = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("Item13", "subItem13", "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2"),
                new GeneralItem("Item23", "subItem23", "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2"),
                new GeneralItem("Item33", "subItem33", "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2")
        ));
        adapter = new AdapterMainList(items, getContext());
        lista4.setAdapter(adapter);
        lista4.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        return v;
    }
}