package com.antoine.son;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public abstract class SoundMaker {

    private Thread thread;
    AudioInputStream ais=null;
    SourceDataLine line;
    float volume;


    /**
     * <p>Initialise les états</p>
     * @param musicPath du fichier .wav
     */
    public SoundMaker(String musicPath, float volume){
        checkVolumeinRange(volume);
        thread = implementRun();
        this.volume= volume;
        init(musicPath);
    }

    protected abstract Thread implementRun();

    public void setVolume(float volume){
        checkVolumeinRange(volume);
        this.volume = volume;
    }

    private void checkVolumeinRange(float volume){
        if(volume < 0 && volume > 1)
            throw new IllegalArgumentException("volume doit être compris entre 0 et 1 :"+volume);
    }

    public void play(){
        if (!thread.isAlive()){
            thread.start();
        }else{
            synchronized(this) {
                notify();
            }
        }
    }

    private void init(String musicPath){
        URL url= getClass().getResource(musicPath);

        try {

            AudioFileFormat format = AudioSystem.getAudioFileFormat(url);

            ais = AudioSystem.getAudioInputStream(url);

            AudioFormat audioFormat = ais.getFormat();

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);

            line = (SourceDataLine) AudioSystem.getLine(info);

            line.open(audioFormat);

            line.start();

        }catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("erreur de lecture du fichier de musique");
        }
    }

    protected byte[] adjustVolume(byte[] audioSamples, int start, int len) {
        byte[] array = new byte[len];
        len = start + len;
        if (len >= audioSamples.length)
            len = audioSamples.length ;
        for (int i = start; i < len; i+=2) {
            // convert byte pair to int
            short buf1 = audioSamples[i+1];
            short buf2 = audioSamples[i];

            buf1 = (short) ((buf1 & 0xff) << 8);
            buf2 = (short) (buf2 & 0xff);

            short res= (short) (buf1 | buf2);
            res = (short) (res * volume);

            // convert back
            array[i - start] = (byte) res;
            array[(i+1) - start] = (byte) (res >> 8);

        }
        return array;
    }

}

