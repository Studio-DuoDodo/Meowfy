package com.example.meowtify.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.meowtify.R;
import com.example.meowtify.models.User;
import com.example.meowtify.models.UserService;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

public class SplashActivity extends AppCompatActivity {


    private SharedPreferences.Editor editor;
    private SharedPreferences msharedPreferences;
private ProgressBar loading;
    private RequestQueue queue;
    Fragment currentFragment;
    private static final String CLIENT_ID = "8175f0284ba94a128cca4b9d788449a6";
    private static final String REDIRECT_URI = "http://com.example.meowtify/callback";
    // private static final String REDIRECT_URI = " com.example.meowtify://callback";

    private static final int REQUEST_CODE = 1337;
    private static final String SCOPES = "user-read-playback-position,user-read-private,user-read-email,playlist-read-private,user-library-read,user-library-modify,user-top-read,playlist-read-collaborative,ugc-image-upload,user-follow-read,user-follow-modify,user-read-playback-state,user-modify-playback-state,user-read-currently-playing,user-read-recently-played,playlist-modify-public,playlist-modify-private";
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat);
        setTheme(R.style.Theme_AppCompat);
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        login =  findViewById(R.id.buttonLogin);
        loading= findViewById(R.id.loading);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                authenticateSpotify();


                msharedPreferences = v.getContext().getSharedPreferences("SPOTIFY", 0);
                queue = Volley.newRequestQueue(v.getContext());
            }
        });
    }
    private void authenticateSpotify() {
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{SCOPES});
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }
    private void waitForUserInfo() {
        UserService userService = new UserService(queue, msharedPreferences);
        userService.get(() -> {
            User user = userService.getUser();
            editor = getSharedPreferences("SPOTIFY", 0).edit();
            editor.putString("userid", user.id);
            editor.putString("username", user.getDisplayName());
            Log.d("STARTING", "GOT USER INFORMATION");
            // We use commit instead of apply because we need the information stored immediately
            editor.commit();
            startMainActivity();
        });
    }

    private void startMainActivity() {
        Intent newintent = new Intent(SplashActivity.this, MainActivity.class);
        loading.setVisibility(View.INVISIBLE);
        startActivity(newintent);
    }@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    editor = getSharedPreferences("SPOTIFY", 0).edit();
                    editor.putString("token", response.getAccessToken());
                    Log.d("STARTING", "GOT AUTH TOKEN");
                    editor.apply();
                    waitForUserInfo();
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases

            }
        }
    }
}