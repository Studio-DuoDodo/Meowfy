package com.example.meowtify.services.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.meowtify.R;
import com.example.meowtify.models.Song;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreateNotification {

    public static final String CHANNEL_ID = "channel1";

    public static final String ACTION_PREVIUOS = "actionprevious";
    public static final String ACTION_PLAY = "actionplay";
    public static final String ACTION_NEXT = "actionnext";

    public static Notification notification;

    public static void createNotification(Context context, Song song, int playbutton, int pos, int size){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat( context, "tag");

            Bitmap icon = null;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(song.getAlbum().getImages().get(0).url).openConnection();
                connection.setDoInput(true);
                connection.connect();
                icon = BitmapFactory.decodeStream(connection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            PendingIntent pendingIntentPrevious;
            int drw_previous;
            if (pos == 0){
                pendingIntentPrevious = null;
                drw_previous = 0;
            } else {
                Intent intentPrevious = new Intent(context, NotificationActionService.class)
                        .setAction(ACTION_PREVIUOS);
                pendingIntentPrevious = PendingIntent.getBroadcast(context, 0,
                        intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT);
                drw_previous = android.R.drawable.ic_media_previous;
            }

            Intent intentPlay = new Intent(context, NotificationActionService.class)
                    .setAction(ACTION_PLAY);
            PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0,
                    intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);

            PendingIntent pendingIntentNext;
            int drw_next;
            if (pos == size){
                pendingIntentNext = null;
                drw_next = 0;
            } else {
                Intent intentNext = new Intent(context, NotificationActionService.class)
                        .setAction(ACTION_NEXT);
                pendingIntentNext = PendingIntent.getBroadcast(context, 0,
                        intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
                drw_next = android.R.drawable.ic_media_next;
            }

            //create notification
            if(drw_next == 0 && drw_previous == 0){
                notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon( R.drawable.logo_meowfy)
                        .setContentTitle(song.getName())
                        .setContentText(song.getArtists().get(0).getName())
                        .setLargeIcon(icon)
                        .setOnlyAlertOnce(true)
                        .setShowWhen(false)
                        .addAction(playbutton, "Play", pendingIntentPlay)
                        .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                                .setShowActionsInCompactView(0, 1, 2)
                                .setMediaSession(mediaSessionCompat.getSessionToken()))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .build();
            }else{
                if(drw_next != 0 && drw_previous != 0){
                    notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                            .setSmallIcon( R.drawable.logo_meowfy)
                            .setContentTitle(song.getName())
                            .setContentText(song.getArtists().get(0).getName())
                            .setLargeIcon(icon)
                            .setOnlyAlertOnce(true)
                            .setShowWhen(false)
                            .addAction(drw_previous, "Previous", pendingIntentPrevious)
                            .addAction(playbutton, "Play", pendingIntentPlay)
                            .addAction(drw_next, "Next", pendingIntentNext)
                            .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                                    .setShowActionsInCompactView(0, 1, 2)
                                    .setMediaSession(mediaSessionCompat.getSessionToken()))
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .build();
                }else{
                    if(drw_next != 0){
                        notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                                .setSmallIcon( R.drawable.logo_meowfy)
                                .setContentTitle(song.getName())
                                .setContentText(song.getArtists().get(0).getName())
                                .setLargeIcon(icon)
                                .setOnlyAlertOnce(true)
                                .setShowWhen(false)
                                .addAction(playbutton, "Play", pendingIntentPlay)
                                .addAction(drw_next, "Next", pendingIntentNext)
                                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                                        .setShowActionsInCompactView(0, 1, 2)
                                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .build();
                    }else{
                        notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                                .setSmallIcon( R.drawable.logo_meowfy)
                                .setContentTitle(song.getName())
                                .setContentText(song.getArtists().get(0).getName())
                                .setLargeIcon(icon)
                                .setOnlyAlertOnce(true)
                                .setShowWhen(false)
                                .addAction(drw_previous, "Previous", pendingIntentPrevious)
                                .addAction(playbutton, "Play", pendingIntentPlay)
                                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                                        .setShowActionsInCompactView(0, 1, 2)
                                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .build();
                    }
                }
            }
            notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon( R.drawable.logo_meowfy)
                    .setContentTitle(song.getName())
                    .setContentText(song.getArtists().get(0).getName())
                    .setLargeIcon(icon)
                    .setOnlyAlertOnce(true)
                    .setShowWhen(false)
                    .addAction(drw_previous, "Previous", pendingIntentPrevious)
                    .addAction(playbutton, "Play", pendingIntentPlay)
                    .addAction(drw_next, "Next", pendingIntentNext)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0, 1, 2)
                            .setMediaSession(mediaSessionCompat.getSessionToken()))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build();

            notificationManagerCompat.notify(1, notification);

        }
    }
}