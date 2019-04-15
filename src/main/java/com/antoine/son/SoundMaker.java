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
        thread.setDaemon(true);
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
        URL url = this.getClass().getResource(musicPath);

        try {

            AudioFileFormat format = AudioSystem.getAudioFileFormat(url);

            ais = AudioSystem.getAudioInputStream(url);

            AudioFormat audioFormat = ais.getFormat();

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);

            if (AudioSystem.isLineSupported(info)) {

                line = (SourceDataLine) AudioSystem.getLine(info);

            }else{

                AudioFormat targetAudioFormat;

                targetAudioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                        audioFormat.getSampleRate(),
                        audioFormat.getSampleSizeInBits(),
                        audioFormat.getChannels(),
                        audioFormat.getFrameSize(),
                        audioFormat.getFrameRate(),
                        false);

                ais = AudioSystem.getAudioInputStream(targetAudioFormat, ais);

                info = new DataLine.Info(SourceDataLine.class, targetAudioFormat);

                line = (SourceDataLine) AudioSystem.getLine(info);



                audioFormat = targetAudioFormat;
            }

            line.open(audioFormat);

            line.start();

        }catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("erreur de lecture du fichier de musique");
        }
    }


}

