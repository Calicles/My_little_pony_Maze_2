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

    private float MusicVolume, soundVolume;

    public Jukebox(){
        MusicVolume = 0.2f;
        soundVolume = 0.5f;
        musics = new HashMap<>();
        sounds = new HashMap<>();
    }

    public void setMusicVolume(final float volume){
        this.MusicVolume = volume;
        Set<String > musicSet = musics.keySet();
        for (String s: musicSet){
            musics.get(s).setVolume(volume);
        }
    }

    public void setSoundVolume(final float volume){
        this.soundVolume = volume;
        Set<String> soundSet = sounds.keySet();
        for (String s: soundSet){
            sounds.get(s).setVolume(volume);
        }
    }

    public void setMusic(String idMusicPath){
        String[] buf = idMusicPath.split(",");
        System.out.println(buf[1]);
        musics.put(buf[0], new MusicPlayer(buf[1], MusicVolume));
        currentPlaying = buf[0];
    }

    public void setSound(String idSoundPath){
        String[] buf = idSoundPath.split(",");
        sounds.put(buf[0], new SoundEffect(buf[1], MusicVolume));
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

    public float getMusicVolume() {
        return MusicVolume;
    }

    public float getSoundVolume(){
        return soundVolume;
    }
}
