package com.example.meowtify.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.meowtify.R;
import com.example.meowtify.Utilitis;
import com.example.meowtify.fragments.MainFragment;
import com.example.meowtify.fragments.OnFragmentChanged;
import com.example.meowtify.fragments.ReproductorFragment;
import com.example.meowtify.fragments.SearchFragment;
import com.example.meowtify.fragments.YourLibraryFragment;
import com.example.meowtify.models.Album;
import com.example.meowtify.models.Artist;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Song;
import com.example.meowtify.models.Track;
import com.example.meowtify.models.Type;
import com.example.meowtify.services.AlbumService;
import com.example.meowtify.services.ArtistService;
import com.example.meowtify.services.MediaPlayerService;
import com.example.meowtify.services.SongService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnFragmentChanged {
    private static final String CLIENT_ID = "8175f0284ba94a128cca4b9d788449a6";
    private static final String REDIRECT_URI = "http://com.example.meowtify/callback";
    // private static final String REDIRECT_URI = " com.example.meowtify://callback";
    private static final int REQUEST_CODE = 1337;
    private static final String SCOPES = "user-read-playback-position,user-read-private,user-read-email,playlist-read-private,user-library-read,user-library-modify,user-top-read,playlist-read-collaborative,ugc-image-upload,user-follow-read,user-follow-modify,user-read-playback-state,user-modify-playback-state,user-read-currently-playing,user-read-recently-played";
    public static boolean inReproductorForFirstTime = false;
    public static Fragment currentFragment;
    public static OnFragmentChanged onFragmentChanged;
    BottomNavigationView navigationMenu;
    ImageView bottomSheetImage;
    ReproductorFragment viewer = null;
    private SharedPreferences.Editor editor;
    private TextView userView;
    private TextView songView;
   String testAlbum="019Bh0y5hMxnvTqL1PXDFx";
    private CoordinatorLayout fragmentCordinator;
    private TextView songTitle;
    private TextView subtitel;
    private RelativeLayout relativeLayoutBottomSheet;
    private Button addBtn;
    private ImageButton playButton;
    private ImageButton favButton;
    private Song song;
    private SongService songService;
    private AlbumService albumService;
    private ArrayList<Song> recentlyPlayedTracks;


    //bottomSheetVariables
    private Type songType;
    private String idListSong;
    private int posSong;

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

    private void getTracks() {
        songService.getRecentlyPlayedTracks(() -> {
            recentlyPlayedTracks = songService.getSongs();
            updateSong();
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
    static  Album a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle b = getIntent().getExtras();
        relativeLayoutBottomSheet = findViewById(R.id.bottomSheetLayout);
        songTitle = findViewById(R.id.songTitle);
        subtitel = findViewById(R.id.subtitle);
        playButton = findViewById(R.id.playSong);
        bottomSheetImage = findViewById(R.id.currentSongImage);
        fragmentCordinator = findViewById(R.id.coordinatorLayout);
        onFragmentChanged = this;
        albumService= new AlbumService(getApplicationContext());

        albumService.getAlbumByRef(()->{
              a = albumService.getLastAlbum();
            albumService.saveAlbumToUserLibrary(a);
        },"07bYtmE3bPsLB6ZbmmFi8d");
        if (savedInstanceState == null) {
            currentFragment = new MainFragment();
            changeFragment(currentFragment, "Home");
        }
        apiStuff();
        navigationMenu = findViewById(R.id.bottomNavigation);
        navigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        changeFragment(new MainFragment(), "Home");
                        System.out.println("home");
                        return true;
                    case R.id.sheare:
                        albumService.unsaveAnAlbum(a);
                        changeFragment(new SearchFragment(), "Share");

                        System.out.println("share");
                        return true;
                    case R.id.library:
                        changeFragment(new YourLibraryFragment(), "Library");
                        System.out.println("library");
                        return true;
                }
                System.out.println("null");
                return false;
            }
        });
        relativeLayoutBottomSheet.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                GeneralItem gi = ReproductorFragment.songService.lastSearchedSong.toGeneralItem();

                gi.setId(idListSong);
                gi.setExtra1(String.valueOf(posSong));
                gi.setExtra2(songType.toString());

                Utilitis.navigationToAAP(gi, v.getContext());
            }
        });
    playButton.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (MediaPlayerService.isPlaying()){
            MediaPlayerService.pause();
            playButton.setImageDrawable(getDrawable(android.R.drawable.ic_media_play));

        }else {
        MediaPlayerService.resume();
            playButton.setImageDrawable(getDrawable(android.R.drawable.ic_media_pause));


        }}
});
        /*songService = new SongService(getApplicationContext());

        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        userView.setText(sharedPreferences.getString("userid", "No User"));

        getTracks();

        addBtn.setOnClickListener(addListener);*/


    }


    private Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    private void apiStuff() {
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

    private void changeFragment(Fragment currentFragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentFragment, tag).commit();
        MainActivity.currentFragment = currentFragment;
        if (tag != null)
            onFragmentChanged.OnFragmentChanged();

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void OnFragmentChanged() {
        System.out.println("reproductorforfirsttime" + inReproductorForFirstTime);
        if (currentFragment.getTag().equals("Reproductor") || !inReproductorForFirstTime) {
            relativeLayoutBottomSheet.setVisibility(View.INVISIBLE);

        } else if (inReproductorForFirstTime && ReproductorFragment.songService != null) {
            songType = ReproductorFragment.type;
            idListSong = ReproductorFragment.idList;
            posSong = ReproductorFragment.posList;

            Song s = ReproductorFragment.songService.lastSearchedSong;
            songTitle.setText(s.getName());
            songTitle.setSelected(true);
            subtitel.setText(s.getArtists().get(0).getName());
            Picasso.with(getApplicationContext()).load(s.getAlbum().getImages().get(0).url).into(bottomSheetImage);

            relativeLayoutBottomSheet.setVisibility(View.VISIBLE);
            fragmentCordinator.setPadding(0,0,0,120);
            if (MediaPlayerService.isPlaying()){
                playButton.setImageDrawable(getDrawable(android.R.drawable.ic_media_pause));

            }else {
                playButton.setImageDrawable(getDrawable(android.R.drawable.ic_media_play));


            }
        }

    }
}