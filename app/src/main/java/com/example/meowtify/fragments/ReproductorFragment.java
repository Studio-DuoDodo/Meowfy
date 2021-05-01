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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.meowtify.R;
import com.example.meowtify.activities.MainActivity;
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Song;
import com.example.meowtify.models.Type;
import com.example.meowtify.services.AlbumService;
import com.example.meowtify.services.ArtistService;
import com.example.meowtify.services.MediaPlayerService;
import com.example.meowtify.services.PlaylistService;
import com.example.meowtify.services.SongService;
import com.example.meowtify.services.notifications.CreateNotification;
import com.example.meowtify.services.notifications.OnClearFromRecentService;
import com.example.meowtify.services.notifications.Playable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.content.Context.BIND_AUTO_CREATE;


public class ReproductorFragment extends Fragment implements Playable, MediaPlayer.OnCompletionListener {
    public static SongService songService;
    public static GeneralItem song;
    public static Type type;
    public static String idList;
    ImageButton playButton;
    ImageButton forwardButton;
    ImageButton backwardButton;
    ImageButton favoriteButton;
    ImageButton shuffelButton;
    ImageButton repeatButton;
    ImageView songImage;
    TextView titleSong;
    TextView subtitleSong;
    TextView currentDuration;
    SeekBar seekBar;
    AlbumService albumService;
    ArtistService artistService;
    PlaylistService playlistService;
    boolean isPlaying = false;
    MainActivity mainActivity;
    View v;

    public ReproductorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        Intent mIntent = new Intent(getContext(), MediaPlayerService.class);
        getActivity().bindService(mIntent, mainActivity.mConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reproductor, container, false);
        songService = new SongService(v.getContext());
        mainActivity = (MainActivity) getActivity();
        Bundle b = getArguments();
        if (b != null) {
            type = (Type) b.getSerializable("typeList");

            if (type != Type.track) {
                idList = b.getString("idList");
                mainActivity.position = b.getInt("posList");
            }
            switch (type) {
                case album:
                    albumService = new AlbumService(v.getContext());
                    albumService.getAlbumByRef(() -> {
                        if (albumService.getLastAlbum() != null)
                            mainActivity.songs = albumService.getLastAlbum().getSongs();
                        for (int i = 0; i < mainActivity.songs.size(); i++) {
                            mainActivity.songs.get(i).setAlbum(albumService.getLastAlbum());
                        }
                        updateSongByAPI();

                    },((GeneralItem)b.getSerializable("generalItem")).getId());
                    break;
                case track:
                    //refactor this
                    songService = new SongService(v.getContext());
                    songService.getASongByRef(() -> {
                        albumService = new AlbumService(v.getContext());
                        albumService.getAlbumByRef(() -> {
                            if (albumService.getLastAlbum() != null)
                                mainActivity.songs = albumService.getLastAlbum().getSongs();
                            for (int i = 0; i < mainActivity.songs.size(); i++) {
                                mainActivity.songs.get(i).setAlbum(albumService.getLastAlbum());
                            }
                            updateSongByAPI();

                        },songService.lastSearchedSong.getAlbum().getId());
                    },((GeneralItem)b.getSerializable("generalItem")).getId());
                    break;

                case artist:
                    String[] idsSongs = idList.split(" ");
                    for (int i = 0; i < idsSongs.length; i++) {
                        if (!idsSongs[i].contains(" ") && !idsSongs[i].isEmpty()) {
                            int finalI = i;
                            songService.getASongByRef(()->{
                                Song songTemp = songService.lastSearchedSong;
                                mainActivity.songs.add(songTemp);
                                if (finalI ==idsSongs.length-1){
                                  ChangeSong(mainActivity.songs.get(mainActivity.position));
                                }

                            },idsSongs[i]);
                    }


                        System.out.println("Generated songs ids" + mainActivity.songs.toString());

                    }
                    //     songs = artistService.getTopSongsOfAnArtist();
                    break;
                case playlist:
                    playlistService = new PlaylistService(v.getContext());
                    playlistService.getAPlayListByRef(() -> {
                        mainActivity.songs = playlistService.getLastSearchedPlaylist().getSongs();
                        System.out.println("songs is"
                                + mainActivity.songs.toString());
                        ChangeSong(mainActivity.songs.get(mainActivity.position));
                    },idList);
                    break;
            }
            System.out.println("type: "+type.toString()+"\nidList: "+idList+"\nposList: "+mainActivity.position);

            //  songService.getASongByRef(this::updateSongByAPI, "5aXrEHnW1oDPISMqIPJZVz");
            // songService.getASongByRef(this::updateSongByAPI, );
        }
        playButton = v.findViewById(R.id.playButton);
        seekBar = v.findViewById(R.id.seekBar);
        forwardButton = v.findViewById(R.id.nextButton);
        backwardButton = v.findViewById(R.id.prevButton);
        shuffelButton = v.findViewById(R.id.shuffleButton);
        repeatButton = v.findViewById(R.id.repeatButton);
        songImage = v.findViewById(R.id.currentSongImage);
        titleSong = v.findViewById(R.id.title);
        subtitleSong = v.findViewById(R.id.subtitle);
        currentDuration = v.findViewById(R.id.currentDuration);
        favoriteButton = v.findViewById(R.id.favButton);
        mainActivity.mediaPlayerServiceIntent = new Intent(getContext(), MediaPlayerService.class);


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

        shuffelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.shuffle(mainActivity.songs);
            }
        });
        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.songs.add(mainActivity.position+1, mainActivity.songs.get(mainActivity.position));
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

                if (mainActivity.mBounded && mainActivity.songs != null && fromUser) {
                    System.out.println("Current progress" + progress + "of " + mainActivity.mediaPlayerService.getCurrentPositionInMillisecons());
                    mainActivity.mediaPlayerService.changeProgress(progress);

                    onTrackPlay();
                }

                currentDuration.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(progress),
                        TimeUnit.MILLISECONDS.toSeconds(progress) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(progress))));
            }
        });

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favoriteButton.getTag(R.string.albunes).equals("1")) {
                    songService.addSongToLibrary(songs.get(position));
                    favoriteButton.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_favorite_24));
                    favoriteButton.setTag(R.string.albunes, "0");

                    //todo: add to the favorit list of albums
                } else {
                    songService.removeSongOfLibrary(songs.get(position));
                    favoriteButton.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                    favoriteButton.setTag(R.string.albunes, "1");

                 }
            }
        });

        this.v = v;
        return v;
    }

    public void updateSongByAPI() {
        v.getContext().startService(mainActivity.mediaPlayerServiceIntent);
        v.getContext().bindService(mainActivity.mediaPlayerServiceIntent, mainActivity.mConnection, BIND_AUTO_CREATE);

        Song s = mainActivity.songs.get(mainActivity.position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.getContext().startService(new Intent(getContext(), OnClearFromRecentService.class));
        }
        if (mainActivity.mBounded)
            seekBar.setMax(30000);
        MainActivity.inReproductorForFirstTime = true;

        //seekBar.setProgress(0);
        titleSong.setText(s.getName());
        titleSong.setSelected(true);
        if (s.getAlbum() != null) {
            subtitleSong.setText(s.getAlbum().getArtistNames());
            subtitleSong.setText(s.getAlbum().getArtistNames());
            Picasso.with(getContext()).load(s.getAlbum().getImages().get(0).url).into(songImage);
            if (mainActivity.mBounded)
                mainActivity.mediaPlayerService.changeSong(s);
            onTrackPlay();
        } else {
            System.out.println("S es " + s.toString());


        }

songService.checkIfTheUserHasASongInFavorites(()->{
    if(songService.isLastCheck()) {
        favoriteButton.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_favorite_24));
        favoriteButton.setTag(R.string.albunes, "0");
    }else {
        favoriteButton.setImageDrawable(getContext().getDrawable(R.drawable.ic_baseline_favorite_border_24));
        favoriteButton.setTag(R.string.albunes,"1");
    }
},songs.get(position).getId());


    }

    public void startSeekBar() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

        service.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {

                seekBar.setProgress(mainActivity.mediaPlayerService.getCurrentPositionInMillisecons());
                if (mainActivity.mediaPlayerService.getCurrentPositionInMillisecons() == 30000) {
                    onTrackEnd();
                }


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

    public void ChangeSong(Song s) {
        songService.getASongByRef(this::updateSongByAPI, s.getId());
    }

    @Override
    public void onTrackPrevious() {
        if (mainActivity.position - 1 > 0) {
            mainActivity.position--;
            ChangeSong(mainActivity.songs.get(mainActivity.position));
            CreateNotification.createNotification(getContext(), mainActivity.songs.get(mainActivity.position), android.R.drawable.ic_media_pause, mainActivity.position, mainActivity.songs.size() - 1);
        } else {
            Toast.makeText(mainActivity.mediaPlayerService, "No more Songs", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onTrackPlay() {
        CreateNotification.createNotification(getContext(), mainActivity.songs.get(mainActivity.position),
                android.R.drawable.ic_media_pause, mainActivity.position, mainActivity.songs.size() - 1);
        playButton.setImageResource(android.R.drawable.ic_media_pause);
        if (mainActivity.mBounded)
            MediaPlayerService.resume();
        isPlaying = true;
        startSeekBar();
    }

    @Override
    public void onTrackPause() {
        CreateNotification.createNotification(getContext(), mainActivity.songs.get(mainActivity.position),
                android.R.drawable.ic_media_play, mainActivity.position, mainActivity.songs.size() - 1);
        if (mainActivity.mBounded)
            mainActivity.mediaPlayerService.pause();
        playButton.setImageResource(android.R.drawable.ic_media_play);
        //   title.setText(tracks.get(position).getTitle());
        isPlaying = false;
    }

    @Override
    public void onTrackNext() {
        if (mainActivity.position + 1 < mainActivity.songs.size()) {
            mainActivity.position++;
            CreateNotification.createNotification(getContext(), mainActivity.songs.get(mainActivity.position),
                    android.R.drawable.ic_media_pause, mainActivity.position, mainActivity.songs.size() - 1);

            ChangeSong(mainActivity.songs.get(mainActivity.position));

        } else {
            Toast.makeText(mainActivity.mediaPlayerService, "No more Songs", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTrackEnd() {
        onTrackNext();
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


}