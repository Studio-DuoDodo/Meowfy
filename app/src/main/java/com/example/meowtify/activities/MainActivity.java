package com.example.meowtify.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
 import com.example.meowtify.models.Type;
import com.example.meowtify.services.AlbumService;
import com.example.meowtify.services.ArtistService;
import com.example.meowtify.services.MediaPlayerService;
import com.example.meowtify.services.SongService;
import com.example.meowtify.services.notifications.CreateNotification;
import com.example.meowtify.services.notifications.OnClearFromRecentService;
import com.example.meowtify.services.notifications.Playable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnFragmentChanged, Playable {
    private static final String CLIENT_ID = "8175f0284ba94a128cca4b9d788449a6";
    private static final String REDIRECT_URI = "http://com.example.meowtify/callback";
    // private static final String REDIRECT_URI = " com.example.meowtify://callback";
    private static final int REQUEST_CODE = 1337;
    private static final String SCOPES = "user-read-playback-position,user-read-private,user-read-email,playlist-read-private,user-library-read,user-library-modify,user-top-read,playlist-read-collaborative,ugc-image-upload,user-follow-read,user-follow-modify,user-read-playback-state,user-modify-playback-state,user-read-currently-playing,user-read-recently-played";
    public static boolean inReproductorForFirstTime = false;
    public static Fragment currentFragment;
    public static OnFragmentChanged onFragmentChanged;
    public List<Song> songs = new ArrayList<>();
    public int position;
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
    NotificationManager notificationManager;
    public Intent mediaPlayerServiceIntent;
    public boolean mBounded;
    public MediaPlayerService mediaPlayerService;
    public ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getApplicationContext(), "Service is disconnected", Toast.LENGTH_LONG).show();
            mBounded = false;
            mediaPlayerService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(getApplicationContext(), "Service is connected", Toast.LENGTH_LONG).show();
            mBounded = true;
            MediaPlayerService.LocalBinder mLocalBinder = (MediaPlayerService.LocalBinder) service;
            mediaPlayerService = mLocalBinder.getServerInstance();
        }
    };

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

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("brodcast");
            String action = intent.getExtras().getString("actionname");
            switch (action) {
                case CreateNotification.ACTION_PREVIUOS:
                    onTrackPrevious();
                    System.out.println("previus");
                    break;
                case CreateNotification.ACTION_PLAY:
                    if (MediaPlayerService.isPlaying()) {
                        System.out.println("pause");
                        onTrackPause();
                    } else {
                        System.out.println("play");
                        onTrackPlay();
                    }
                    break;
                case CreateNotification.ACTION_NEXT:
                    System.out.println("next");
                    onTrackNext();
                    break;
            }
        }
    };

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,
                    "Meowfy", NotificationManager.IMPORTANCE_LOW);

            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
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
                gi.setExtra1(String.valueOf(position));
                gi.setExtra2(songType.toString());

                System.out.println(gi.toString());
                Utilitis.navigationToAAP(gi, v.getContext());
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (MediaPlayerService.isPlaying()){
                    onTrackPause();
                    /*MediaPlayerService.pause();
                    playButton.setImageDrawable(getDrawable(android.R.drawable.ic_media_play));*/
                }else {
                    onTrackPlay();
                    /*MediaPlayerService.resume();
                    playButton.setImageDrawable(getDrawable(android.R.drawable.ic_media_pause));*/
                }}
        });
        /*songService = new SongService(getApplicationContext());

        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        userView.setText(sharedPreferences.getString("userid", "No User"));

        getTracks();

        addBtn.setOnClickListener(addListener);*/



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
            registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
            getApplicationContext().startService(new Intent(getApplicationContext(), OnClearFromRecentService.class));
        }
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
            if(ReproductorFragment.type == Type.track) songType = Type.album;
            else songType = ReproductorFragment.type;
            idListSong = ReproductorFragment.idList;
            System.out.println("type lista "+songType+"\n id lista "+idListSong+"\nposicion lista "+ position);

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

    @Override
    public void onTrackPrevious() {
        if (position - 1 > 0) {
            position--;
            ChangeSong(songs.get(position));
            CreateNotification.createNotification(getApplicationContext(), songs.get(position), android.R.drawable.ic_media_pause, position, songs.size() - 1);
        } else {
            Toast.makeText(getApplicationContext(), "No more Songs", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onTrackPlay() {
        if(inReproductorForFirstTime){
            CreateNotification.createNotification(getApplicationContext(), songs.get(position),
                    android.R.drawable.ic_media_pause, position, songs.size() - 1);
            MediaPlayerService.resume();
            playButton.setImageDrawable(getDrawable(android.R.drawable.ic_media_pause));
        }
    }

    @Override
    public void onTrackPause() {
        if(inReproductorForFirstTime){
            CreateNotification.createNotification(getApplicationContext(), songs.get(position),
                    android.R.drawable.ic_media_play, position, songs.size() - 1);
            MediaPlayerService.pause();
            playButton.setImageDrawable(getDrawable(android.R.drawable.ic_media_play));
        }
    }

    @Override
    public void onTrackNext() {
        if (position + 1 < songs.size()) {
            position++;
            CreateNotification.createNotification(getApplicationContext(), songs.get(position),
                    android.R.drawable.ic_media_pause, position, songs.size() - 1);

            ChangeSong(songs.get(position));

        } else {
            Toast.makeText(getApplicationContext(), "No more Songs", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTrackEnd() {
        onTrackNext();
    }

    private void updateSongByAPI() {
        getApplicationContext().startService(mediaPlayerServiceIntent);
        getApplicationContext().bindService(mediaPlayerServiceIntent, mConnection, BIND_AUTO_CREATE);

        song = songs.get(position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getApplicationContext().startService(new Intent(getApplicationContext(), OnClearFromRecentService.class));
        }

        if (mBounded)
            mediaPlayerService.changeSong(song);
        onTrackPlay();

        songTitle.setText(song.getName());
        songTitle.setSelected(true);
        subtitel.setText(song.getArtists().get(0).getName());
        Picasso.with(getApplicationContext()).load(song.getAlbum().getImages().get(0).url).into(bottomSheetImage);
    }

    public void ChangeSong(Song s) {
        if(!currentFragment.getTag().equals("Reproductor")){
            songService.getASongByRef(this::updateSongByAPI, s.getId());
        }else{
            ReproductorFragment reproductor = ((ReproductorFragment) getSupportFragmentManager().findFragmentByTag("Reproductor"));

            reproductor.ChangeSong(s);
        }
    }
}