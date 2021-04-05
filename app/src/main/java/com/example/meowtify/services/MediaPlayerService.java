package com.example.meowtify.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.view.View;

import com.example.meowtify.models.Song;

import java.io.IOException;

public class MediaPlayerService extends Service implements MediaPlayer.OnCompletionListener {
     static  MediaPlayer mediaPlayer;
    Song currentSong;
    IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {

        mediaPlayer = new MediaPlayer();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        return START_STICKY;
    }

    public void onDestroy() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }
 public static  boolean isPlaying(){
        return mediaPlayer.isPlaying();
 }

    public static void pause() {
        mediaPlayer.pause();

    }

    public void changeSong(Song song) {
        if (currentSong == null || (!song.getId().equals(currentSong.getId()))) {
            start(song.getPreview_url());
            currentSong = song;
        }

    }

    public void start() {
        mediaPlayer.start();
        System.out.println("started playing");
    }
View v;
    public void start(String url) {
        if (mediaPlayer.isPlaying()) mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = new MediaPlayer();
        try {
            if (url == null)
                url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";

            mediaPlayer.setDataSource(url);

            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
            @Override
            public void onPrepared(MediaPlayer playerM){
                mediaPlayer.start();
                System.out.println("started playing");
            }
        });
        mediaPlayer.setOnCompletionListener(this);

    }

    public void changeProgress(int newProgress) {
        mediaPlayer.pause();
        mediaPlayer.seekTo(newProgress);
        mediaPlayer.start();
    }

    public static void resume() {
        if (!mediaPlayer.isPlaying())
            mediaPlayer.start();
    }

    public int getCurrentPositionInMillisecons() {
        return mediaPlayer.getCurrentPosition();
    }

    public int getMaxDurationInMillisecons() {
        return mediaPlayer.getDuration();
    }

    public void onCompletion(MediaPlayer _mediaPlayer) {
         }

    public class LocalBinder extends Binder {
        public MediaPlayerService getServerInstance() {
            return MediaPlayerService.this;
        }
    }
}

