package com.antoine.son.bruitage;

import org.junit.Test;

import static org.junit.Assert.*;

public class SoundPlayerTest {

    @Test
    public void startSound() {
        SoundPlayer player= new SoundPlayer("./ressources/sons/bruitage/trotDur.wav");
        player.play();
    }
}