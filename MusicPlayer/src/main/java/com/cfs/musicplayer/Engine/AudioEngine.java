package com.cfs.musicplayer.Engine;

import com.cfs.musicplayer.OutputDevice.IOAudioDevice;
import com.cfs.musicplayer.models.Song;

public class AudioEngine {

    private Song currentSong;
    private boolean isSongPaused;

    public AudioEngine() {
        this.currentSong = null;
        this.isSongPaused = false;
    }

    public String getCurrentSong() {
        if (currentSong != null) {
            return currentSong.getTitle();
        }
        return "";
    }

    public void playSong(IOAudioDevice ioAudioDevice, Song song) {

        if (song == null) {
            throw new RuntimeException("No song selected");
        }

        // If same song and paused → resume
        if (currentSong == song && isSongPaused) {
            isSongPaused = false;
            System.out.println("Resuming: " + currentSong.getTitle());
            ioAudioDevice.play(currentSong);
            return;
        }

        // Otherwise → play new song
        this.currentSong = song;
        this.isSongPaused = false;

        System.out.println("Playing: " + currentSong.getTitle());
        ioAudioDevice.play(currentSong);
    }

    public void pauseSong() {

        if (currentSong == null) {
            throw new RuntimeException("No song selected");
        }

        if (isSongPaused) {
            throw new RuntimeException("Song is already paused");
        }

        isSongPaused = true;
        System.out.println("Paused: " + currentSong.getTitle());
    }
}
