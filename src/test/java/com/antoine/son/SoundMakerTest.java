package com.antoine.son;

import org.junit.Test;

public class SoundMakerTest {

    String path = "/ressources/sons/bruitage/Open Up Your Eyes.wav";

    @Test
    public void play() {
        SoundMaker player= new SoundMaker(path, 1);
        player.play();
    }
}