package com.antoine.son;

import java.io.ByteArrayOutputStream;

public class SoundEffect extends SoundMaker {

    private byte[] samples;

    public SoundEffect(String musicPath, float volume) {
        super(musicPath, volume);
        samples = getAudioFileData();
    }

    @Override
    protected Thread implementRun() {
        return new Thread(()->{
            int bytesRead;
            byte[] buf;
            while (true) {
                bytesRead = 0;
                while ((bytesRead <= samples.length)) {
                    buf = adjustVolume(samples, bytesRead, 128);
                    bytesRead += buf.length;
                    line.write(buf, 0, buf.length);
                }
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
