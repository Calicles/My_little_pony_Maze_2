package com.antoine.son.musique;
import java.io.*;
import javax.sound.sampled.*;
import java.io.IOException;


/**
 * <b>Classe de lecture de musique</b>
 * <p>au format .wav</p>
 *
 * @author antoine
 */
public class MusicPlayer extends Thread{
  
  AudioInputStream ais=null;
  SourceDataLine line;
  
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
  public MusicPlayer(String musicPath){
    playing=true;
    levelRunning= true;
    source= musicPath;
    this.setDaemon(true);
    init();
  }
  
  @SuppressWarnings("unused")
  /**
   * <p>lance la lecture</p>
   */
  public void run(){
    try{
      byte bytes[]= new byte[1024];
      int bytesRead=0;
      while(((bytesRead= ais.read(bytes, 0, bytes.length)) != -1) && levelRunning){
        synchronized(this){
          if(!playing){
            try {

              wait();

            }catch (InterruptedException ignored){}
          }
        }
        line.write(bytes,0, bytesRead);
      }
    }catch(IOException i){
      i.printStackTrace();
    }
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

  private void init(){
    File fichier= new File(source);

    try {

      AudioFileFormat format = AudioSystem.getAudioFileFormat(fichier);

      ais = AudioSystem.getAudioInputStream(fichier);

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
  
}