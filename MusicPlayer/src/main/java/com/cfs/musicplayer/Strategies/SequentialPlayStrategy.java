package com.cfs.musicplayer.Strategies;

import com.cfs.musicplayer.models.Playlist;
import com.cfs.musicplayer.models.Song;

public class SequentialPlayStrategy implements PlayStrategy {

    private Playlist currentPlaylist;
    private int index;

    public SequentialPlayStrategy() {
        currentPlaylist = null;
        index = -1;
    }

    @Override
    public void SetPlayList(Playlist playlist) {
        this.currentPlaylist = playlist;
    }

    @Override
    public Song next() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Song prev() {
        return null;
    }

    @Override
    public boolean hasPrev() {
        return false;
    }
}
