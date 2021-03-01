package com.example.meowtify.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;

import com.example.meowtify.R;
import com.example.meowtify.fragments.HomeFragment;
import com.example.meowtify.fragments.RegisterFragment;

public class MainActivity extends AppCompatActivity {

    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            currentFragment = new HomeFragment();
            changeFragment(currentFragment);
        }
    }



    private void changeFragment(Fragment currentFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentFragment).commit();
    }
}