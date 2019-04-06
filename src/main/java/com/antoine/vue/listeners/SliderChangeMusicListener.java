package com.antoine.vue.listeners;

import com.antoine.manager.musique.Jukebox;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderChangeMusicListener implements ChangeListener {

    private Jukebox jukebox;

    public SliderChangeMusicListener(Jukebox jukebox){
        this.jukebox = jukebox;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        jukebox.setMusicVolume( ((float) source.getValue() / 100));
    }

}
