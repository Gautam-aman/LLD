package com.cfs.musicplayer.Strategies;

import com.cfs.musicplayer.models.Playlist;
import com.cfs.musicplayer.models.Song;

public interface PlayStrategy {
    public void SetPlayList(Playlist playlist);
    public Song next();
    public boolean hasNext();
    public Song prev();
    public boolean hasPrev();
    public default void addToNext(Song song){};
}
