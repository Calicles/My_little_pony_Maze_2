package com.antoine.son;

import java.io.IOException;

public class MusicPlayer extends SoundMaker {

    protected Thread thread;
    boolean playing;
    boolean levelRunning;

    /**
     * <p>Initialise les états</p>
     *
     * @param musicPath du fichier .wav
     * @param volume
     */
    public MusicPlayer(String musicPath, float volume) {
        super(musicPath, volume);

        thread = implementRun();
        thread.setDaemon(true);
        playing=true;
        levelRunning= true;
    }

    protected void testSleep() {
        synchronized (this) {
            if (!playing) {
                try {
                    wait();
                } catch (InterruptedException ie) {
                }
            }
        }
    }

    @Override
    protected Thread implementRun(){
        return new Thread(()-> {
            try {
                int totalRead = 0;
                byte bytes[] = new byte[1042];

                while ((totalRead = ais.read(bytes, 0, bytes.length)) != -1 && levelRunning) {

                    testSleep();
                    bytes = adjustVolume(bytes, 0, totalRead);
                    //SoundAdjuster.normalizeVolume(bytes, 0, totalRead, volume);

                    line.write(bytes, 0, totalRead);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });
    }

    /**
     * <p>met sur pause pour reprise ultérieure.</p>
     */
    public void pause(){
        synchronized(this){
            playing= false;
        }
    }

    /**
     * <p>arrêt définitif.</p>
     */
    public void arret(){
        levelRunning= false;
    }

    @Override
    public void play(){
        super.play();
        playing = true;
    }
}
