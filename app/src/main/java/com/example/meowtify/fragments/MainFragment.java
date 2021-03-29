package com.example.meowtify.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meowtify.PlaylistService;
import com.example.meowtify.R;
import com.example.meowtify.SongService;
import com.example.meowtify.adapters.AdapterMainList;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Playlist;

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
    BackTask backTask;

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
        List<GeneralItem> items21 = new ArrayList<>();
        playlistService = new PlaylistService(v.getContext());
        final List<Playlist>[] items2 = new List[]{new ArrayList<>()};
        AdapterMainList adapter1 = new AdapterMainList(items21, getContext());

        playlistService.getUserPlayLists(()->{
            items2[0] =playlistService.getPlaylists();
            for (Playlist p: items2[0]
            ) {
                items21.add(p.toGeneralItem());
            }
            System.out.println("Items in param : = " + items21.toString());
            adapter1.setItems(items21);

        },50,0);


        System.out.println("items2= " + items2[0].toString());

        missatgePersonalitzat = v.findViewById(R.id.missatgePersonalitzat);
        lista1 = v.findViewById(R.id.listaRecently);
        lista2 = v.findViewById(R.id.listaYourPlaylist);
        lista3 = v.findViewById(R.id.listaJumpBack);
        lista4 = v.findViewById(R.id.listaJumpBack2);

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

        items = new ArrayList<GeneralItem>(Arrays.asList(

                new GeneralItem("Item11", "subItem11", "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2"),
                new GeneralItem("Item21", "subItem21", "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2"),
                new GeneralItem("Item31", "subItem31", "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2"),
                new GeneralItem("Item41", "subItem41", "https://i.scdn.co/image/0f057142f11c251f81a22ca639b7261530b280b2")
        ));
        adapter = new AdapterMainList(items, getContext());
        lista1.setAdapter(adapter);
        lista1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        lista2.setAdapter(adapter1);
         lista2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter.notifyDataSetChanged();
        items = new ArrayList<GeneralItem>(Arrays.asList(
                new GeneralItem("Item13", "subItem13", "https://mosaic.scdn.co/640/e337f3661f68bc4d96a554de0ad7988d65edb25a134cd5ccaef9d411eba33df9542db9ba731aaf98ec04f9acee17a7576f939eb5aa317d20c6322494c4b4399d9b7c6f61b6a6ee70c616bc1a985c7ab8"),
                new GeneralItem("Item23", "subItem23", "https://mosaic.scdn.co/60/ab67616d0000b27305fda9ec255cb06c5342fb6dab67616d0000b2734ef422e5fd3e7ca85b47e3fdab67616d0000b27379604d10cd1083e9af0a7891ab67616d0000b27388f50f79184550f7346a1c6d%27%7D"),
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
    class BackTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // do web stuff
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // fetches data now start anim
         //   doAnimation();

        }

    }
}