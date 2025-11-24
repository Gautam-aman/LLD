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
        index = -1;
    }

    @Override
    public Song next() {
        if (currentPlaylist == null || currentPlaylist.getSongs().isEmpty()) {
            throw new IllegalStateException("No more songs to play");
        }
        index++;
        return currentPlaylist.getSongs().get(index);
    }

    @Override
    public boolean hasNext() {
        return index+1 < currentPlaylist.getSongs().size();
    }

    @Override
    public Song prev() {
        if (currentPlaylist == null || currentPlaylist.getSongs().isEmpty()) {
            throw new IllegalStateException("No more songs to play");
        };
        index--;
        return currentPlaylist.getSongs().get(index);
    }

    @Override
    public boolean hasPrev() {
        return index-1 > 0;
    }
}
