package com.cfs.musicplayer.Strategies;

import com.cfs.musicplayer.models.Playlist;
import com.cfs.musicplayer.models.Song;

import java.util.List;
import java.util.Random;
import java.util.Stack;

public class RandomPlayStrategy implements PlayStrategy {

    private List<Song> remainingSong;
   private Playlist currentPlaylist;
    private int index;
    Stack<Song> history;

    Random random = new Random();
    public RandomPlayStrategy() {
        this.currentPlaylist = null;
        this.index = -1;
    }

    @Override
    public void SetPlayList(Playlist playlist) {
        this.currentPlaylist = playlist;
        if (currentPlaylist == null || currentPlaylist.getSongs().size() == 0) {
            return;
        }

    }

    @Override
    public Song next() {
        if(currentPlaylist.getSongs().isEmpty() || currentPlaylist == null){
            throw new RuntimeException("No more songs to play");
        }
        int index =  random.nextInt(currentPlaylist.getSongs().size());
        Song song = remainingSong.get(index);
        remainingSong.remove(index);
        history.push(song);
        return song;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Song prev() {
       if (history.isEmpty()) {
           return null;
       }
       Song song = history.pop();
       return song;
    }

    @Override
    public boolean hasPrev() {
        return history.size()>0;
    }
}
