package com.antoine.son.bruitage;


import com.sun.scenario.Settings;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * <b>Classe de gestion des effets sonore</b>
 * <p>utilisée via méthode statique</p>
 *
 * @author antoine
 */
public class SoundPlayer {


    private Clip clip;

    /**
     * <p>ouvre le flux de la ressource.</p>
     * @param audioPath path de la ressource audio en .wav
     */
    public SoundPlayer(String audioPath, final float soundLevel) {

        try (AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(audioPath))) {

            clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(soundLevel);
            System.out.println(clip.getLevel()+"   "+volume);

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de lecture d'un fichier audio");
        }

    }

    public float getVolume(){
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20);
    }

    public void setVolume(float volume){
        if (volume < 0f || volume > 2f)
            throw new IllegalArgumentException("volume invalide "+volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    public void play(){
        clip.start();
    }

    public void stop(){
        clip.stop();
        clip.flush();
    }

    /**
     *<p>Créer la ligne et lit le son
     * pour fichier court.</p>
     * @param soundPath de la ressource audio en .wav
     */
    public static void startSound(String soundPath, final float soundLevel){
        SoundPlayer player = new SoundPlayer(soundPath, soundLevel);
        player.play();
    }
}
