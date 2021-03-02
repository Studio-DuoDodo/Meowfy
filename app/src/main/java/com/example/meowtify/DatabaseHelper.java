package com.example.meowtify;

import com.example.meowtify.models.Playlist;
import com.example.meowtify.models.Song;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHelper {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    public DatabaseHelper(String myRef) {
        this.myRef = database.getReference(myRef);
    }
    public DatabaseReference getMyRef() {
        return myRef;
    }
    public String getKey() {
        return this.myRef.push().getKey();

    }
    public void updateSong(Song novaSong) {
        myRef.child(novaSong.getIdSong()).setValue(novaSong);
    }
    public void addSong(Song value) {
        myRef.child(getKey()).setValue(value);
    }
    public void updatePlaylist(Playlist novaPlaylist) {
        myRef.child(novaPlaylist.getIdPlaylist()).setValue(novaPlaylist);
    }
    public void addPlaylist(Playlist value) {
        myRef.child(getKey()).setValue(value);
    }
}