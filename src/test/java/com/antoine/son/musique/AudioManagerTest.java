package com.antoine.son.musique;

import org.junit.Test;

public class AudioManagerTest {

    @Test
    public void play() {
        AudioManager player= new AudioManager("/ressources/sons/bruitage/Open Up Your Eyes.wav");
    }

    @Test
    public void pause() {
    }
}