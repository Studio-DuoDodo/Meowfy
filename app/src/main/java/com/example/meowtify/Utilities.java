package com.example.meowtify;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.example.meowtify.activities.MainActivity;
import com.example.meowtify.fragments.AlbumFragment;
import com.example.meowtify.fragments.ArtistFragment;
import com.example.meowtify.fragments.PlaylistFragment;
import com.example.meowtify.fragments.PlayerFragment;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Type;

import java.util.HashMap;
import java.util.Map;

public class Utilities {
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
                bundle = new Bundle();

                if(Type.valueOf(generalItem.getExtra2()) != Type.track){
                    bundle.putString("idList", generalItem.getId());
                    bundle.putInt("posList",Integer.parseInt(generalItem.getExtra1()));
                }
                bundle.putSerializable("typeList", Type.valueOf(generalItem.getExtra2()));
                bundle.putSerializable("generalItem", generalItem);
                fragment =  new PlayerFragment();
                fragmentTag="Player";

        }

        fragment.setArguments(bundle);
        MainActivity.currentFragment=fragment;

        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment,fragmentTag).commit();

    MainActivity.onFragmentChanged.OnFragmentChanged();
    }
    public static Map<String, String> getHeaders(String token) throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        String auth = "Bearer " + token;
        headers.put("Authorization", auth);
        return headers;
    }
}
