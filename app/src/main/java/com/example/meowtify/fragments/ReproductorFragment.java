package com.example.meowtify.fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.meowtify.R;
import com.example.meowtify.activities.MainActivity;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Song;
import com.example.meowtify.services.AlbumService;
import com.example.meowtify.services.MediaPlayerService;
import com.example.meowtify.services.SongService;
import com.example.meowtify.services.notifications.CreateNotification;
import com.example.meowtify.services.notifications.OnClearFromRecentService;
import com.example.meowtify.services.notifications.Playable;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.content.Context.BIND_AUTO_CREATE;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReproductorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReproductorFragment extends Fragment implements Playable, MediaPlayer.OnCompletionListener {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageButton playButton;
    ImageButton forwardButton;
    ImageButton backwardButton;
    ImageView songImage;
    SeekBar seekBar;
    boolean mBounded;
    Intent mediaPlayerServiceIntent;
    MediaPlayerService mediaPlayerService;
    SongService songService;
    AlbumService albumService;
    NotificationManager notificationManager;
    List<Song> tracks;
    int position = 0;
    boolean isPlaying = false;
    View v;
     ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getContext(), "Service is disconnected", Toast.LENGTH_LONG).show();
            mBounded = false;
            mediaPlayerService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(getContext(), "Service is connected", Toast.LENGTH_LONG).show();
            mBounded = true;
            MediaPlayerService.LocalBinder mLocalBinder = (MediaPlayerService.LocalBinder) service;
            mediaPlayerService = mLocalBinder.getServerInstance();
        }
    };

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");
            switch (action) {
                case CreateNotification.ACTION_PREVIUOS:
                    onTrackPrevious();
                    break;
                case CreateNotification.ACTION_PLAY:
                    if (isPlaying) {
                        onTrackPause();
                    } else {
                        onTrackPlay();
                    }
                    break;
                case CreateNotification.ACTION_NEXT:
                    onTrackNext();
                    break;
            }
        }
    };
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReproductorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReproductorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReproductorFragment newInstance(String param1, String param2) {
        ReproductorFragment fragment = new ReproductorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,
                    "Meowfy", NotificationManager.IMPORTANCE_LOW);

            notificationManager = getActivity().getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        Intent mIntent = new Intent(getContext(), MediaPlayerService.class);
        getActivity().bindService(mIntent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reproductor, container, false);
        songService = new SongService(v.getContext());
        Bundle b = getArguments();
        if (b != null) {
            GeneralItem generalItem = (GeneralItem) b.getSerializable("generalItem");
            songService.getASongByRef(this::updateSongByAPI, generalItem.getId());
            System.out.println(generalItem.toString());
        }
        playButton = v.findViewById(R.id.playButton);
        seekBar = v.findViewById(R.id.seekBar);
        forwardButton = v.findViewById(R.id.nextButton);
        backwardButton = v.findViewById(R.id.prevButton);
        songImage = v.findViewById(R.id.currentSongImage);
        mediaPlayerServiceIntent = new Intent(getContext(), MediaPlayerService.class);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    onTrackPause();
                } else onTrackPlay();
            }
        });
        backwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTrackPrevious();
            }
        });
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTrackNext();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (mBounded && tracks != null&&fromUser) {
                    System.out.println("Current progress" + progress + "of " + mediaPlayerService.getCurrentPositionInMillisecons());
                    mediaPlayerService.changeProgress(progress);

                    onTrackPlay();
                }

            }
        });

        this.v = v;
        return v;
    }

    private void updateSongByAPI() {
        v.getContext().startService(mediaPlayerServiceIntent);
        v.getContext().bindService(mediaPlayerServiceIntent, mConnection, BIND_AUTO_CREATE);

        Song s = songService.getLastSearchedSong();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
            v.getContext().registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
            v.getContext().startService(new Intent(getContext(), OnClearFromRecentService.class));
        }
        if (mBounded)
            seekBar.setMax(30000);
        MainActivity.inReproductorForFirstTime=true;

        //seekBar.setProgress(0);
        Picasso.with(getContext()).load(s.getAlbum().getImages().get(0).url).into(songImage);
        if (mBounded)
            mediaPlayerService.changeSong(s);
        albumService = new AlbumService(v.getContext());
        albumService.getAlbumByRef(() -> {
            tracks = albumService.getLastAlbum().getSongs();
            onTrackPlay();

        }, s.getAlbum().getId());


    }
public void startSeekBar(){
    ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    service.scheduleWithFixedDelay(new Runnable() {
        @Override
        public void run() {

            seekBar.setProgress(mediaPlayerService.getCurrentPositionInMillisecons());
            System.out.println("progress changed to" + seekBar.getProgress());


        }
    }, 1, 1, TimeUnit.MILLISECONDS);

}
    @Override
    public void onStop() {
        super.onStop();
        //  if (mBounded) {
        //       getContext().unbindService(mConnection);
        //    mBounded = false;

        //   }
    }

    @Override
    public void onTrackPrevious() {
        startSeekBar();

        //title.setText(tracks.get(position).getName());
        if (position - 1 > 0) {
            position--;
            mediaPlayerService.changeSong(tracks.get(position));
            startSeekBar();

            CreateNotification.createNotification(getContext(), tracks.get(position), android.R.drawable.ic_media_pause, position, tracks.size() - 1);
        } else {
            Toast.makeText(mediaPlayerService, "No more Songs", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onTrackPlay() {
        CreateNotification.createNotification(getContext(), tracks.get(position),
                android.R.drawable.ic_media_pause, position, tracks.size() - 1);
        playButton.setImageResource(android.R.drawable.ic_media_pause);
        if (mBounded)
            mediaPlayerService.resume();
        //title.setText(tracks.get(position).getTitle());
        isPlaying = true;
        startSeekBar();


    }

    @Override
    public void onTrackPause() {

        CreateNotification.createNotification(getContext(), tracks.get(position),
                android.R.drawable.ic_media_play, position, tracks.size() - 1);
        if (mBounded)
            mediaPlayerService.pause();
        playButton.setImageResource(android.R.drawable.ic_media_play);
        //   title.setText(tracks.get(position).getTitle());
        isPlaying = false;

    }

    @Override
    public void onTrackNext() {


        //  title.setText(tracks.get(position).getTitle());
        if (position + 1 < tracks.size()) {
            position++;
            CreateNotification.createNotification(getContext(), tracks.get(position),
                    android.R.drawable.ic_media_pause, position, tracks.size() - 1);
            mediaPlayerService.changeSong(tracks.get(position));
            startSeekBar();

        } else {
            Toast.makeText(mediaPlayerService, "No more Songs", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.cancelAll();
        }
*/
        //  getView().getContext().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        onTrackNext();
    }

    ;


}