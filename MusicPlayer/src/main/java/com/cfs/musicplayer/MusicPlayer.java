package com.cfs.musicplayer;

import com.cfs.musicplayer.DeviceManagers.DeviceManager;
import com.cfs.musicplayer.DeviceManagers.PlayLIstManager;
import com.cfs.musicplayer.DeviceManagers.StrategyManager;
import com.cfs.musicplayer.Enums.DeviceType;
import com.cfs.musicplayer.Enums.PlayStrategyType;
import com.cfs.musicplayer.Facade.MusicPlayerFacade;
import com.cfs.musicplayer.models.Playlist;
import com.cfs.musicplayer.models.Song;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {
    private static MusicPlayer instance = null;
   // List<Song> songLibrary;
    private List<Song> songLibrary = new ArrayList<>();
    private StrategyManager strategyManager = StrategyManager.getInstance();



    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    public void CreateSongInLibrary(String title , String artist , String path) {
            Song song = new Song(title, artist, path);
            songLibrary.add(song);
    }

    public Song FindSongByTitle(String title) {
        for (Song song : songLibrary) {
            if (song.getTitle().equals(title)) {
                return song;
            }
        }
        return null;
    }

    public void createPlaylist(String name){
        PlayLIstManager.getInstance().CreatePlaylist(name);
    }

    public void addSongToPlaylist(String playlistName , String songName) {
        Song song = FindSongByTitle(songName);

        if (song == null) {
            throw new RuntimeException("Song not found");
        }

        PlayLIstManager.getInstance().addSongtoPlaylist(playlistName, song);
    }


    public void ConnectAudioDevice(DeviceType deviceType){
        MusicPlayerFacade.getInstance().connectDevice(deviceType);
    }

    public void selectPlayStrategy(PlayStrategyType playStrategyType){
        MusicPlayerFacade.getInstance().setPlayStrategy(playStrategyType);
    }

    public void loadPlaylist(String playListName){
        MusicPlayerFacade.getInstance().loadPlaylist(playListName);
    }

    public void playSingleSong(String songName){
        Song song = FindSongByTitle(songName);
        MusicPlayerFacade.getInstance().playSong(song);
    }
    public void pauseCurrentSong(String songName){
        Song song = FindSongByTitle(songName);
        MusicPlayerFacade.getInstance().pause(song);
    }

    public void playAllTracks(){
        MusicPlayerFacade.getInstance().playAllTracks();
    }


    public void playPreviousSong(){
        MusicPlayerFacade.getInstance().playPreviousTrack();
    }


}
