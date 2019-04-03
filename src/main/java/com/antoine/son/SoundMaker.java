package com.antoine.son;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

public class SoundMaker extends Thread{


    /**
     * <b>Classe de lecture de musique</b>
     * <p>au format .wav</p>
     *
     * @author antoine
     */

        AudioInputStream ais=null;
        SourceDataLine line;
        float volume;

        String source;

        /*
        état
         */
        boolean playing;
        boolean levelRunning;

        /**
         * <p>Initialise les états</p>
         * @param musicPath du fichier .wav
         */
        public SoundMaker(String musicPath, float volume){
            playing=true;
            levelRunning= true;
            this.volume= volume;
            source= musicPath;
            this.setDaemon(true);
            init();
        }

        public void setVolume(float volume){
            FloatControl volumeControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);
        }

        /**
         * <p>lance la lecture</p>
         */
        public void run() {

                int byteWrite= 0;

                byte bytes[]= this.getAudioFileData();
                bytes= adjustVolume(bytes, 0.5f);

                while ((byteWrite = line.write(bytes, 0, bytes.length)) != -1);

        }

        /**
         * <p>joue la musique.</p>
         */
        public void play(){
            if (!this.isAlive()){
                this.start();
            }else{
                synchronized(this) {
                    notify();
                }
            }
            playing=true;
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

        private void sleep() {
            synchronized (this) {
                if (!playing) {
                    try {
                        wait();
                    } catch (InterruptedException ie) {
                    }
                }
            }
        }



        private void init(){
            URL url= getClass().getResource(source);

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

        private byte[] adjustVolume(byte[] audioSamples, float volume) {
            System.out.println("in");
            byte[] array = new byte[audioSamples.length];
            for (int i = 0; i < array.length; i+=2) {
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

