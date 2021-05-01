package com.example.meowtify.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.meowtify.R;
import com.example.meowtify.adapters.LibraryPagerAdapter;


public class YourLibraryFragment extends Fragment {


    public YourLibraryFragment() {
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