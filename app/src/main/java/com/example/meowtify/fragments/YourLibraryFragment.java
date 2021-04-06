package com.example.meowtify.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meowtify.R;
import com.example.meowtify.adapters.LibraryPagerAdapter;


public class YourLibraryFragment extends Fragment {


    public YourLibraryFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_your_library, container, false);

        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        LibraryPagerAdapter adapter = new LibraryPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);

        return v;
    }
}