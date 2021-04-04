package com.example.meowtify.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.meowtify.models.Song;

import java.io.IOException;

public class MediaPlayerService extends Service implements MediaPlayer.OnCompletionListener {
  MediaPlayer mediaPlayer;

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


  public void  pause(){
    mediaPlayer.pause();

  }
  public void changeSong(Song song){
    start(song.getPreview_url());

  }
  public   void start(){
    mediaPlayer.start();
    System.out.println("started playing");
  } public   void start(String url){
    if (mediaPlayer.isPlaying())mediaPlayer.stop();
    mediaPlayer.release();
    mediaPlayer = new MediaPlayer();
    try {
      mediaPlayer.setDataSource(url);
      mediaPlayer.prepare();
    } catch (IOException e) {
      e.printStackTrace();
    }
    mediaPlayer.setOnCompletionListener(this);
    mediaPlayer.start();
    System.out.println("started playing");
  }
  public void changeProgress(int newProgress){
    mediaPlayer.stop();
    mediaPlayer.seekTo(newProgress   );
    mediaPlayer.start();
  }
  public int getCurrentPositionInMillisecons(){
  return  mediaPlayer.getCurrentPosition();
  }
  public int getMaxDurationInMillisecons(){
  return  mediaPlayer.getDuration();
  }

  public void onCompletion(MediaPlayer _mediaPlayer) {
    stopSelf();
  }
  public class LocalBinder extends Binder {
    public MediaPlayerService getServerInstance() {
      return MediaPlayerService.this;
    }
  }
}

