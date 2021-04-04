package com.example.meowtify;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.meowtify.fragments.AlbumFragment;
import com.example.meowtify.fragments.ArtistFragment;
import com.example.meowtify.fragments.PlaylistFragment;
import com.example.meowtify.fragments.ReproductorFragment;
import com.example.meowtify.models.GeneralItem;

import java.util.ArrayList;
import java.util.List;

public class Utilitis {
    public static void navigationToAAP(GeneralItem generalItem, Context context) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("generalItem", generalItem);
        Fragment fragment;

        switch (generalItem.getType()){
            case playlist:
                fragment =  new PlaylistFragment();
                break;
            case artist:
                fragment =  new ArtistFragment();
                break;
            case album:
                fragment =  new AlbumFragment();
                break;
            default:
                fragment =  new ReproductorFragment();
        }

        fragment.setArguments(bundle);
        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
    }
}
