package com.antoine.son.bruitage;


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
    private SoundPlayer(String audioPath) {

        try (AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource(audioPath))) {

            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de lecture d'un fichier audio");
        }

    }

    private void play(){
        clip.start();
    }

    private void stop(){
        clip.stop();
    }

    /**
     *<p>Créer la ligne et lit le son
     * pour fichier court.</p>
     * @param soundPath de la ressource audio en .wav
     */
    public static void startSound(String soundPath){
        SoundPlayer player= new SoundPlayer(soundPath);
        player.play();
    }
}
