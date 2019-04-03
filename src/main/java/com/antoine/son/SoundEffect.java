package com.antoine.son;

import java.io.ByteArrayOutputStream;

public class SoundEffect extends SoundMaker {

    private byte[] samples;


    /**
     * <p>Initialise les états</p>
     *
     * @param musicPath du fichier .wav
     * @param volume
     */
    public SoundEffect(String musicPath, float volume) {
        super(musicPath, volume);
        samples = getAudioFileData();
    }



    @Override
    protected Thread implementRun() {
        return new Thread(()->{
            while (true) {
                samples = adjustVolume(samples, 0, samples.length);
                line.write(samples, 0, samples.length);
                sleep();
            }
        });
    }

    private synchronized void sleep() {
        try{
            wait();
        }catch (InterruptedException ignored){}
    }

    private byte[] getAudioFileData() {
        byte[] data = null;
        try {
            final ByteArrayOutputStream baout = new ByteArrayOutputStream();

            byte[] buffer = new byte[4096];
            int c;
            while ((c = ais.read(buffer, 0, buffer.length)) != -1) {
                baout.write(buffer, 0, c);
            }
            ais.close();
            baout.close();
            data = baout.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}
