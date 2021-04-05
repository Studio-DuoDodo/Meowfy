package com.example.meowtify;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.meowtify.activities.MainActivity;
import com.example.meowtify.fragments.AlbumFragment;
import com.example.meowtify.fragments.ArtistFragment;
import com.example.meowtify.fragments.OnFragmentChanged;
import com.example.meowtify.fragments.PlaylistFragment;
import com.example.meowtify.fragments.ReproductorFragment;
import com.example.meowtify.models.GeneralItem;

import java.util.ArrayList;
import java.util.List;

public class Utilitis {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void navigationToAAP(GeneralItem generalItem, Context context) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("generalItem", generalItem);
        String fragmentTag;
        Fragment fragment;
         switch (generalItem.getType()){
            case playlist:
                fragment =  new PlaylistFragment();
               fragmentTag="Playlist";
                break;
            case artist:
                fragment =  new ArtistFragment();
                fragmentTag="Artist";
                break;
            case album:
                fragment =  new AlbumFragment();
                fragmentTag="Album";
                break;
            default:
                fragment =  new ReproductorFragment();
                fragmentTag="Reproductor";

        }

        fragment.setArguments(bundle);
        MainActivity.currentFragment=fragment;

        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment,fragmentTag).commit();

    MainActivity.onFragmentChanged.OnFragmentChanged();
    }
}
