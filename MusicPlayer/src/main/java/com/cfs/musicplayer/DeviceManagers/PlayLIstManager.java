package com.cfs.musicplayer.DeviceManagers;

import com.cfs.musicplayer.models.Playlist;
import com.cfs.musicplayer.models.Song;

import java.util.HashMap;
import java.util.Map;

public class PlayLIstManager {
    public static PlayLIstManager instance = null;
    private Map<String, Playlist> mpp = new HashMap<>();
    public static PlayLIstManager getInstance() {
        if (instance == null) {
            instance = new PlayLIstManager();
        }
        return instance;
    }

    public void CreatePlaylist(String name){
        if(mpp.containsKey(name)){
            throw new IllegalArgumentException("Playlist already exists");
        }
        mpp.put(name, new Playlist(name));
    }

    public void addSongtoPlaylist(String playlistName , Song song){
        if (mpp.containsKey(playlistName)){
            mpp.get(playlistName).AddSong(song);
        }
        else{
            throw new IllegalArgumentException("Playlist does not exist");
        }
    }

    public Playlist GetPlaylist(String name){
        if(mpp.containsKey(name)){
            return mpp.get(name);
        }
        return null;
    }

}
