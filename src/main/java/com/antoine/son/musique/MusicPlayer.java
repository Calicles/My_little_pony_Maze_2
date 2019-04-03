package com.antoine.son.musique;
import java.io.*;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;


/**
 * <b>Classe de lecture de musique</b>
 * <p>au format .wav</p>
 *
 * @author antoine
 */
public class MusicPlayer extends Thread{
  
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
  public MusicPlayer(String musicPath, float volume){
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
  
  @SuppressWarnings("unused")
  /**
   * <p>lance la lecture</p>
   */
  public void run(){
    try{
      byte bytes[]= new byte[1024];
      int bytesRead=0;
/*
      while ((bytesRead = ais.read(bytes, 0, bytes.length)) != 1){

        System.out.println(bytesRead);

      }

      bytes= adjustVolume(bytes, 2.5f);

      while(((bytesRead= line.write(bytes, 0, bytesRead)) != -1) && levelRunning){
        System.out.println(bytesRead+"    "+2);
        synchronized(this){
          if(!playing){
            try {

              wait();

            }catch (InterruptedException ignored){}
          }
        }
       // line.write(bytes,0, bytesRead);
     }
     */
System.out.println("begin");
    while ((bytesRead= ais.read(bytes, bytesRead, bytes.length)) != -1){
    }
System.out.println("fin");
    byte[] updated= adjustVolume(bytes, 1f);

    while ((bytesRead = line.write(updated, 0, updated.length)) != -1){
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
}