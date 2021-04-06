package com.example.meowtify.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.meowtify.R;
import com.example.meowtify.adapters.AdapterLibraryList;
import com.example.meowtify.adapters.AdapterSearchList;
import com.example.meowtify.adapters.AdapterSearchRecentlyList;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Type;
import com.example.meowtify.services.PlaylistService;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {


 public  static  boolean searched =false;


    private RecyclerView search, recentlySearch;
    private TextInputLayout searchLayout;
    AdapterSearchList adapterSearch;
    public AdapterSearchRecentlyList adapterRecently;
    private TextView recentlyText;
    public static List<GeneralItem> recentlySearchList= new ArrayList<>();
    PlaylistService playlistService;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        searchLayout = v.findViewById(R.id.search_edit);
        search = v.findViewById(R.id.lista_search);
        recentlySearch = v.findViewById(R.id.lista_recenly_search);
        recentlyText = v.findViewById(R.id.textRecycel);

        search.setVisibility(View.INVISIBLE);

        List<GeneralItem> searchList = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("id1", "playlist", Type.playlist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "creator12", null),
                new GeneralItem("id2", "artist", Type.artist, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "songs "+1, null),
                new GeneralItem("id3", "album", Type.album, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artist11", null),
                new GeneralItem("id4", "track", Type.track, "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2", "artist12", null)
        ));


        adapterSearch = new AdapterSearchList(searchList, getContext(), this);
        search.setAdapter(adapterSearch);
        search.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterRecently = new AdapterSearchRecentlyList(recentlySearchList, getContext());
        recentlySearch.setAdapter(adapterRecently);
        recentlySearch.setLayoutManager(new LinearLayoutManager(getContext()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            DrawableCompat.setTint( searchLayout.getEndIconDrawable(), v.getContext().getColor(R.color.white));
        }

        searchLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                recentlySearch.setVisibility(View.INVISIBLE);
                recentlyText.setVisibility(View.INVISIBLE);
                search.setVisibility(View.VISIBLE);
                 playlistService= new PlaylistService(v.getContext());
                 playlistService.search(this::updateSearchByAPI,charSequence.toString(),new ArrayList<Type>(Arrays.asList(Type.artist,Type.album,Type.playlist,Type.track)),"ES",40,0);
            }
            public void updateSearchByAPI() {
                adapterSearch.setItems(playlistService.getSearchResults());
                searched=true;
            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchLayout.getEditText().setText("");

                search.setVisibility(View.INVISIBLE);
                recentlySearch.setVisibility(View.VISIBLE);
                recentlyText.setVisibility(View.VISIBLE);
            }
        });

        return v;
    }

    public boolean checkRecentlySearch(GeneralItem generalItem) {
        for (GeneralItem gI: recentlySearchList) {
            System.out.println(gI.getId().equals(generalItem.getId()));
            if(gI.getId().equals(generalItem.getId())) return true;
        }

        return false;
    }
}
