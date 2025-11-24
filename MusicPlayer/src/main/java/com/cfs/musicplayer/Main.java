package com.cfs.musicplayer;

import com.cfs.musicplayer.Enums.DeviceType;
import com.cfs.musicplayer.Enums.PlayStrategyType;

public class Main {
    public static void main(String[] args) {
            var application = MusicPlayer.getInstance();
            application.CreateSongInLibrary("Kesariya","Arijit Singh","music/kesariyaSong");
            application.CreateSongInLibrary("Pal Pal" , "Talwinder"  , "music/pal pal");

            application.createPlaylist("Bollywood Masti");
            application.addSongToPlaylist("Bollywood Masti","Kesariya");
            application.ConnectAudioDevice(DeviceType.BLUETOOTH);

            application.playSingleSong("Kesariya");
            application.pauseCurrentSong("Kesariya");


            application.selectPlayStrategy(PlayStrategyType.RANDOM);
            application.loadPlaylist("Bollywood Masti");
            application.playAllTracks();

    }
}
