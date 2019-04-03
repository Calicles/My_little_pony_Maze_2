package com.antoine.manager.musique;

import com.antoine.son.musique.MusicPlayer;

import java.util.HashMap;

public class Jukebox {

    private HashMap<String, MusicPlayer> musics;
    private String currentPlaying;

    public Jukebox(String[] musicPaths){
        init(musicPaths);

    }

    public void switchTo(String name){
        musics.get(currentPlaying).pause();
        musics.get(name).play();
        currentPlaying= name;
    }

    public void stop(String name){
        musics.get(name).arret();
        free(name);
    }

    public void free(String name){
        musics.remove(name);
    }

    private void init(String[] paths){
        for (String path : paths) {
            musics.put(path, new MusicPlayer(path, 1));
        }

    }

}
