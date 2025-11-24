package com.cfs.musicplayer.Strategies;

import com.cfs.musicplayer.models.Playlist;
import com.cfs.musicplayer.models.Song;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class CustomPlayStrategy implements PlayStrategy {


    private Playlist currentPlaylist;
    Stack<Song> prevSong;
    Queue<Song> nextSong;
    int currentIndex;
    @Override

    public void SetPlayList(Playlist playlist) {
        this.currentPlaylist = playlist;
        this.prevSong = new Stack<>();
        this.nextSong = new LinkedList<>();
        this.currentIndex = -1;
    }

    public Song nextSequence(){
        if (currentPlaylist.getSongs().size()==0){
            throw new RuntimeException("No songs in playlist");
        }
        currentIndex++;
        return currentPlaylist.getSongs().get(currentIndex);
    }

    public Song previousSequence(){
        if (currentPlaylist.getSongs().size()==0){
            throw new RuntimeException("No songs in playlist");
        }

        currentIndex--;
        return currentPlaylist.getSongs().get(currentIndex);

    }

    @Override
    public Song next() {
        if (currentPlaylist.getSongs().size()==0) {
            throw new RuntimeException("No more songs to play");
        }

        if(nextSong != null) {
            Song song = nextSong.peek();
            nextSong.poll();
            prevSong.add(song);
           var list = currentPlaylist.getSongs();
           for(int i=0;i<list.size();i++){
               if (list.get(i).equals(song)){
                   currentIndex = i;
                   break;
               }
           }
        }


        return nextSequence();

    }

    @Override
    public boolean hasNext() {
        return currentIndex+1<currentPlaylist.getSongs().size();
    }

    @Override
    public Song prev() {
       if (currentPlaylist.getSongs().size()==0){
           throw new RuntimeException("No more songs to play");
       }
       if (prevSong != null) {
           Song song = prevSong.peek();
           prevSong.pop();
          // prevSong.add(song);
           var list = currentPlaylist.getSongs();
           for(int i=0;i<list.size();i++){
               if (list.get(i).equals(song)){
                   currentIndex = i;
                   break;
               }
           }
       }

       return previousSequence();


    }

    @Override
    public boolean hasPrev() {
        return currentIndex -1 > 0;
    }

    @Override
    public void addToNext(Song song) {
        if (song==null){
            throw new NullPointerException("Song is null");
        }
        nextSong.add(song);
    }
}
