package com.example.meowtify;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.meowtify.fragments.AlbumFragment;
import com.example.meowtify.fragments.ArtistFragment;
import com.example.meowtify.fragments.PlaylistFragment;
import com.example.meowtify.models.GeneralItem;

public class Utilitis {
    public static void navigationToAAP(GeneralItem generalItem, Context context) {
        Bundle bundle = new Bundle();
        bundle.putString("id", generalItem.getId());
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
                //todo: canviar el fragment de album a reproductor
                fragment =  new AlbumFragment();
        }

        fragment.setArguments(bundle);
        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
    }
}
