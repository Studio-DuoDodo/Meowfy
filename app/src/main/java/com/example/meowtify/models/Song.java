package com.example.meowtify.models;

public class Song {
    public String getIdSong() {
        return "  notimplemented";
    }
    public String name;
    public String artista;
    public String grupo;
    //bottom fragment sheet
    //musica youtube

    private String id;


    public Song(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", artista='" + artista + '\'' +
                ", grupo='" + grupo + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
