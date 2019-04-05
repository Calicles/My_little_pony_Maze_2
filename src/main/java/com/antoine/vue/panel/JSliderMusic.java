package com.antoine.vue.panel;

import com.antoine.contracts.Presentateur;
import com.antoine.manager.musique.Jukebox;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JSliderMusic extends JSliderPanel {
    public JSliderMusic(Presentateur presentateur) {
        super(presentateur, 0, 100, false);
    }

    @Override
    protected void setListener(Jukebox jukebox) {
        super.addChangeListener(new InnerChangeMusicListener(jukebox));
        setValue((int) (jukebox.getMusicVolume() * 100));
    }


    private class InnerChangeMusicListener implements ChangeListener {

        private Jukebox jukebox;

        private InnerChangeMusicListener(Jukebox jukebox){
            this.jukebox = jukebox;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            jukebox.setMusicVolume( ((float) getValue() / 100));
        }
    }
}
