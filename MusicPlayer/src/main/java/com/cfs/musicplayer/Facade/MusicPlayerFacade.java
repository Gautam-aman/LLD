package com.cfs.musicplayer.Facade;

import com.cfs.musicplayer.DeviceManagers.DeviceManager;
import com.cfs.musicplayer.DeviceManagers.PlayLIstManager;
import com.cfs.musicplayer.DeviceManagers.StrategyManager;
import com.cfs.musicplayer.Engine.AudioEngine;
import com.cfs.musicplayer.Enums.DeviceType;
import com.cfs.musicplayer.Enums.PlayStrategyType;
import com.cfs.musicplayer.OutputDevice.IOAudioDevice;
import com.cfs.musicplayer.Strategies.PlayStrategy;
import com.cfs.musicplayer.models.Playlist;
import com.cfs.musicplayer.models.Song;

import java.security.spec.RSAOtherPrimeInfo;

public class MusicPlayerFacade {

    private static MusicPlayerFacade instance = null;
    private StrategyManager strategyManager;
    private AudioEngine audioEngine;
    private PlayLIstManager playLIstManager;
    private Playlist loadPlaylist;
    private PlayStrategy playStrategy;

    public static MusicPlayerFacade getInstance() {
        if (instance == null) {
            instance = new MusicPlayerFacade();
        }
        return instance;
    }

    private MusicPlayerFacade() {
        loadPlaylist = null;
        playStrategy = null;
        audioEngine = new AudioEngine();
    }

    public void connectDevice(DeviceType deviceType) {
        DeviceManager deviceManager = DeviceManager.getInstance();
        deviceManager.connetDevice(deviceType);
    }

    public void setPlayStrategy(PlayStrategyType playStrategy) {
        this.playStrategy = strategyManager.getInstance().getCurrentPlayStrategy(playStrategy);
    }

    public void loadPlaylist(String playlistName) {
        loadPlaylist = playLIstManager.getInstance().GetPlaylist(playlistName);
        if(playStrategy == null){
            throw new RuntimeException("Playstartegy not set");
        }
        playStrategy.SetPlayList(loadPlaylist);
    }

    public void playSong(Song song){
        if (!DeviceManager.getInstance().OutputDeviceConnected()){
            throw new RuntimeException("Device not connected");
        }
        IOAudioDevice device = DeviceManager.getInstance().GetCurrentOutputDevice();
        audioEngine.playSong(device, song);
    }

    public void pause(Song song){
        if(!audioEngine.getCurrentSong().equals(song.getTitle())){
            throw new RuntimeException("Song not paused");
        }
        audioEngine.pauseSong();
    }

    public void playAllTracks(){
        if (loadPlaylist==null){
            throw new RuntimeException("Playlist is null");
        }
        while(playStrategy.hasNext()){
            Song song = playStrategy.next();
            IOAudioDevice device = DeviceManager.getInstance().GetCurrentOutputDevice();
            audioEngine.playSong(device, song);
        }
        System.out.println("PlayList Finished");
    }

    public void playNextTrack(){
        if (loadPlaylist==null){
            throw new RuntimeException("Playlist is null");
        }
        if (playStrategy.hasNext()){
            Song song = playStrategy.next();
            IOAudioDevice device = DeviceManager.getInstance().GetCurrentOutputDevice();
            audioEngine.playSong(device, song);
        }
    }


    public void playPreviousTrack(){
        if (loadPlaylist==null){
            throw new RuntimeException("Playlist is null");
        }
        if (playStrategy.hasPrev()){
            Song song = playStrategy.prev();
            IOAudioDevice device = DeviceManager.getInstance().GetCurrentOutputDevice();
            audioEngine.playSong(device, song);

        }
    }




}
