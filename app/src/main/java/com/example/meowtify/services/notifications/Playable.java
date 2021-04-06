package com.example.meowtify.services.notifications;

public interface Playable {
    void onTrackPrevious();
    void onTrackPlay();
    void onTrackPause();
    void onTrackNext();
    void onTrackEnd();
}