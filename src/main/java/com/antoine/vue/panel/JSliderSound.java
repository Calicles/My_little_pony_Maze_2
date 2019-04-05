package com.antoine.vue.panel;

import com.antoine.contracts.Presentateur;
import com.antoine.manager.musique.Jukebox;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JSliderSound extends JSliderPanel {

    public JSliderSound(Presentateur presentateur) {
        super(presentateur, 0, 100, true);
    }

    @Override
    protected void setListener(Jukebox jukebox) {
        addChangeListener(new InnerChangeSoundListener(jukebox));
        setValue((int) (jukebox.getSoundVolume() * 100));
    }

    private class InnerChangeSoundListener implements ChangeListener{

        private Jukebox jukebox;

        private InnerChangeSoundListener(Jukebox jukebox){
            this.jukebox = jukebox;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            jukebox.setSoundVolume(((float) getValue()) / 100);
        }
    }
}
