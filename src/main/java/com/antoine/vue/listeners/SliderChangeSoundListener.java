package com.antoine.vue.listeners;

import com.antoine.manager.musique.Jukebox;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderChangeSoundListener implements ChangeListener {


        private Jukebox jukebox;

        public SliderChangeSoundListener(Jukebox jukebox){
            this.jukebox = jukebox;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            jukebox.setSoundVolume(((float) source.getValue()) / 100);
        }

}
