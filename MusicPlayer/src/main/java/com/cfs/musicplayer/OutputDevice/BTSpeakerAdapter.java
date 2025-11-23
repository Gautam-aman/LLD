package com.cfs.musicplayer.OutputDevice;

import com.cfs.musicplayer.external.BTSpeakerApi;
import com.cfs.musicplayer.models.Song;

public class BTSpeakerAdapter implements IOAudioDevice {

    BTSpeakerApi btSpeakerApi;

    public BTSpeakerAdapter(BTSpeakerApi btSpeakerApi){
        this.btSpeakerApi = btSpeakerApi;
    }

    @Override
    public void play(Song song) {
        String payload = "Playing " + song.getTitle() + "by " + song.getArtist();
        btSpeakerApi.playSound(payload);
    }
}
