package com.antoine.son.bruitage;

import org.junit.Test;

import static org.junit.Assert.*;

public class SoundPlayerTest {

    String path= "/ressources/sons/bruitage/trotDur.wav";

    @Test
    public void startSound() {
        SoundPlayer player= new SoundPlayer("/ressources/sons/bruitage/trotDur.wav",1f);
        player.play();
        for(int i = 0; i <= 6 ; i++){
            SoundPlayer.startSound(path, i  / 10);
        }
        SoundPlayer.startSound(path, 2f);
    }

    @Test
    public void play() {
    }

    @Test
    public void startSound1() {
    }
}