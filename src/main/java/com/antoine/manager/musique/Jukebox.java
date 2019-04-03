package com.antoine.manager.musique;

import com.antoine.son.MusicPlayer;
import com.antoine.son.SoundEffect;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Jukebox {

    private Map<String, MusicPlayer> musics;
    private Map<String, SoundEffect> sounds;
    private String currentPlaying = "";

    private float volume;

    public Jukebox(){
        volume = 0.1f;
        musics = new HashMap<>();
        sounds = new HashMap<>();
    }

    public void setVolume(final float volume){
        this.volume = volume;
        Set<String > musicSet = musics.keySet();
        for (String s: musicSet){
            musics.get(s).setVolume(volume);
            sounds.get(s).setVolume(volume);
        }
    }

    public void setMusic(String idMusicPath){
        String[] buf = idMusicPath.split(",");
        musics.put(buf[0], new MusicPlayer(buf[1], volume));
        currentPlaying = buf[0];
    }

    public void setSound(String idSoundPath){
        String[] buf = idSoundPath.split(",");
        sounds.put(buf[0], new SoundEffect(buf[1], volume));
    }

    public void switchTo(String name){
        if (!currentPlaying.equals(name)) {
            musics.get(currentPlaying).pause();
            musics.get(name).play();
            currentPlaying = name;
        }
    }

    public void makeSound(){
        sounds.get(currentPlaying).play();
    }

    public void stop(String name){
        musics.get(name).arret();
        free(name);
    }

    public void free(String name){
        musics.remove(name);
        sounds.remove(name);
    }

}
