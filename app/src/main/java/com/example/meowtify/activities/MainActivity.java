package com.example.meowtify.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.example.meowtify.ArtistService;
import com.example.meowtify.R;
import com.example.meowtify.SongService;
import com.example.meowtify.fragments.MainFragment;
import com.example.meowtify.fragments.SearchFragment;
import com.example.meowtify.fragments.YourLibraryFragment;
import com.example.meowtify.models.Artist;
import com.example.meowtify.models.Song;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String CLIENT_ID = "8175f0284ba94a128cca4b9d788449a6";
    private static final String REDIRECT_URI = "http://com.example.meowtify/callback";
    // private static final String REDIRECT_URI = " com.example.meowtify://callback";
    private static final int REQUEST_CODE = 1337;
    private static final String SCOPES = "user-read-playback-position,user-read-private,user-read-email,playlist-read-private,user-library-read,user-library-modify,user-top-read,playlist-read-collaborative,ugc-image-upload,user-follow-read,user-follow-modify,user-read-playback-state,user-modify-playback-state,user-read-currently-playing,user-read-recently-played";
    Fragment currentFragment;
    BottomNavigationView navigationMenu;
    private SharedPreferences.Editor editor;
    private SharedPreferences msharedPreferences;
    private RequestQueue queue;
    private TextView userView;
    private TextView songView;
    private Button addBtn;
    private Song song;
    private SongService songService;
    private ArtistService artistService;
    private ArrayList<Song> recentlyPlayedTracks;


    private void getTracks() {
        songService.getRecentlyPlayedTracks(() -> {
            recentlyPlayedTracks = songService.getSongs();
            updateSong();
        });
    }
    private void getArtists() {
        artistService.getArtistByid("0TnOYISbd1XYRBk9myaseg",() -> {
            ArrayList<Artist> artists = artistService.getArtists();
            for (Artist s:artists) {
                System.out.println(s.toString());
            }
        });
    }
    private void updateSong() {
        for (Song s : recentlyPlayedTracks) {
            System.out.println(s.toString());
        }
        if (recentlyPlayedTracks.size() > 0) {
            songView.setText(recentlyPlayedTracks.get(0).getName());
            song = recentlyPlayedTracks.get(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            currentFragment = new MainFragment();
            changeFragment(currentFragment);
        }
apiStuff();
        navigationMenu = findViewById(R.id.bottomNavigation);
        navigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        changeFragment(new MainFragment());
                        System.out.println("home");
                        return true;
                    case R.id.sheare:
                        changeFragment(new SearchFragment());
                        System.out.println("sheare");
                        return true;
                    case R.id.library:
                        changeFragment(new YourLibraryFragment());
                        System.out.println("library");
                        return true;
                }
                System.out.println("null");
                return false;
            }
        });

        /*songService = new SongService(getApplicationContext());

        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        userView.setText(sharedPreferences.getString("userid", "No User"));

        getTracks();

        addBtn.setOnClickListener(addListener);*/


    }

    private void apiStuff() {
        artistService= new ArtistService(getApplicationContext());
        songService = new SongService(getApplicationContext());
       // userView = (TextView) findViewById(R.id.user);
     //   songView = (TextView) findViewById(R.id.song);
      //  addBtn = (Button) findViewById(R.id.add);
        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
    //    userView.setText(sharedPreferences.getString("userid", "No User"));
        //      getTracks();
       // addBtn.setOnClickListener(addListener);
    //    getArtists();
    }

    private void changeFragment(Fragment currentFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentFragment).commit();
    }


    private void startMainActivity() {
        Intent newintent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(newintent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    break;

                // Auth flow returned an error
                case ERROR:
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
            }
        }
    }
    private View.OnClickListener addListener = v -> {
        songService.addSongToLibrary(this.song);
        if (recentlyPlayedTracks.size() > 0) {
            recentlyPlayedTracks.remove(0);
        }
       updateSong();
        //  getArtists();
     //   songService.getFeaturedPlayList(() -> {
       //     List<Playlist> p =  songService.getFeaturedPlayList(() -> {

         //   });
           // songService.getAPlayListByRef(() -> {
           // },"https://api.spotify.com/v1/playlists/37i9dQZF1DXdPec7aLTmlC");
       // });
        //pl.getUserPlayLists(()->{},10,0);
    };
}