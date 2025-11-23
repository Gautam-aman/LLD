package com.cfs.musicplayer.OutputDevice;

import com.cfs.musicplayer.external.HeadPhoneApi;
import com.cfs.musicplayer.models.Song;

public class HeadPhoneAdapter implements IOAudioDevice {

    HeadPhoneApi headPhoneApi;

    public HeadPhoneAdapter(HeadPhoneApi headPhoneApi){
        this.headPhoneApi = headPhoneApi;
    }

    @Override
    public void play(Song song) {
        String payload = "Playing " + song.getTitle() + "by " + song.getArtist();
        headPhoneApi.playSound(payload);
    }

}
