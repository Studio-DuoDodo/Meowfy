package com.example.meowtify.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.meowtify.fragments.AlbumLibraryFragment;
import com.example.meowtify.fragments.ArtistLibraryFragment;
import com.example.meowtify.fragments.PlaylistLibraryFragment;

public class LibraryPagerAdapter extends FragmentStatePagerAdapter {

    public LibraryPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return  new PlaylistLibraryFragment();
            case 1: return  new ArtistLibraryFragment();
            default: return  new AlbumLibraryFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
