package com.example.meowtify.fragments;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
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
import com.example.meowtify.models.GeneralItem;
import com.example.meowtify.models.Song;
import com.example.meowtify.services.MediaPlayerService;
import com.example.meowtify.services.SongService;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.content.Context.BIND_AUTO_CREATE;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReproductorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReproductorFragment extends Fragment {
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
    @Override
    public void onStart() {
        super.onStart();

        Intent mIntent = new Intent(  getContext(), MediaPlayerService.class);
        getActivity().bindService(mIntent, mConnection, BIND_AUTO_CREATE);
    };
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
        v.getContext().bindService(mediaPlayerServiceIntent, mConnection, BIND_AUTO_CREATE);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (mBounded)
                    System.out.println("Current progress" + progress + "of " + mediaPlayerService.getCurrentPositionInMillisecons());
                 mediaPlayerService.changeProgress(progress );

            }
        });
        /* if(!isServiceRunning(MediaPlayerService.class))
       // {
              // }
    */
        v.getContext().startService(mediaPlayerServiceIntent);

        return v;
    }

    private void updateSongByAPI() {
        Song s = songService.getLastSearchedSong();
        if (mBounded)
        seekBar.setMax(30000);
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleWithFixedDelay(new Runnable()
        {
            @Override
            public void run()
            {
                seekBar.setProgress(mediaPlayerService.getCurrentPositionInMillisecons());
            }
        }, 1, 1, TimeUnit.MICROSECONDS);
        seekBar.setProgress(0);
        Picasso.with(getContext()).load(s.getAlbum().getImages().get(0).url).into(songImage);
        if (mBounded)
        mediaPlayerService.changeSong(s);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBounded) {
             getContext().unbindService(mConnection);
            mBounded = false;

        }
    }

    ;
   /* private boolean isServiceRunning(Class<?> serviceClass) {
        @SuppressLint("ServiceCast") ActivityManager manager = (ActivityManager) getSystemService( getContext());
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }*/
}