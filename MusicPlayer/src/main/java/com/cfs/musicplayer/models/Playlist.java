package com.cfs.musicplayer.models;

import javax.management.RuntimeErrorException;
import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private final String name;
    private List<Song> songs = new ArrayList<>();


    public Playlist(String name){
        this.name = name;
    }
    public String getPlaylistName() {
        return name;
    }
    public List<Song> getSongs() {
        return songs;
    }

    public void AddSong(Song song){
        if (songs==null){
            throw new RuntimeException("Invalid Song");
        }
        this.songs.add(song);
    }

}
