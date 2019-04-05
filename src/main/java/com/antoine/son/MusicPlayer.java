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
                    bytes = adjustVolume(bytes);
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

    protected byte[] adjustVolume(byte[] audioSamples) {
        byte[] array = new byte[audioSamples.length];

        for (int i = 0; i < audioSamples.length; i+=2) {
            // convert byte pair to int
            short buf1 = audioSamples[i+1];
            short buf2 = audioSamples[i];

            buf1 = (short) ((buf1 & 0xff) << 8);
            buf2 = (short) (buf2 & 0xff);

            short res= (short) (buf1 | buf2);
            res = (short) (res * volume);

            // convert back
            array[i] = (byte) res;
            array[i+1] = (byte) (res >> 8);

        }
        return array;
    }
}
