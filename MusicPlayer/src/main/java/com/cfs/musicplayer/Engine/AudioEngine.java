package com.cfs.musicplayer.Engine;

import com.cfs.musicplayer.OutputDevice.IOAudioDevice;
import com.cfs.musicplayer.models.Song;

public class AudioEngine {
    private final Song currentsong;
    boolean isSongPaused;

    public AudioEngine(){
        this.currentsong = null;
        isSongPaused = false;
    }

    public String getCurrentSong(){
        if (currentsong != null){
            return currentsong.getTitle();
        }
        return "";
    }

    public void playSong(IOAudioDevice ioAudioDevice , Song song){
        if (song == null){
            throw  new RuntimeException("No song is Selected");
        }
        // Resume is song is paused
        if(isSongPaused && currentsong == song){
            isSongPaused = false;
            System.out.println( "Playing" + currentsong.getTitle());
            ioAudioDevice.play(currentsong);
            return;
        }

        isSongPaused = true;
        System.out.println( "Playing" + currentsong.getTitle());
        ioAudioDevice.play(currentsong);
        return;

    }

    public void pauseSong(){
        if (currentsong != null){
            throw  new RuntimeException("Song is Not selected");
        }
        if (isSongPaused){
            throw new RuntimeException("Song is  Already Paused");
        }
        isSongPaused = true;
        System.out.println( "Paused" + currentsong.getTitle());
    }



}
