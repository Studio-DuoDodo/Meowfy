package com.example.meowtify.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.meowtify.fragments.AlbumFragment;
import com.example.meowtify.fragments.ArtistFragment;
import com.example.meowtify.fragments.PlaylistFragment;

public class LibraryPagerAdapter extends FragmentStatePagerAdapter {

    public LibraryPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return  new PlaylistFragment();
            case 1: return  new ArtistFragment();
            default: return  new AlbumFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
