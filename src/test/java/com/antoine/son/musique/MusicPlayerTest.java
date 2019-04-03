package com.antoine.son.musique;

import org.junit.Test;

import static org.junit.Assert.*;

public class MusicPlayerTest {

    String path= "/ressources/sons/bruitage/trotDur.wav";

    @Test
    public void run() {
    }

    @Test
    public void play() {
        MusicPlayer player= new MusicPlayer(path, 2f);
        player.play();
    }

    @Test
    public void pause() {
    }

    @Test
    public void arret() {
    }
}