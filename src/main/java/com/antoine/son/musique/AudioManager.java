package com.antoine.son.musique;
import java.io.*;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class AudioManager extends Thread{
  
////  Cette classe lance la musique de chaque niveau, elle sera invoqu�e dans la classe JukeBox
//    elle m�me invoqu�e dans Controleur.
//    J'ai f�fini le thread en deamon pour ne pas emp�cher la fermeture de la JVM en cas de n�cessit�.
  AudioInputStream ais=null;
  SourceDataLine line;
  
  String source;
  boolean playing;
  boolean levelRunning;
  
  public AudioManager(String s){
    playing=true;
    levelRunning= true;
    source=s;
    this.setDaemon(true);
    this.start();
  }
  
  @SuppressWarnings("unused")
public void run(){
    URL path= getClass().getResource(source);
    try{
      AudioFileFormat format= AudioSystem.getAudioFileFormat(path);
    }catch(UnsupportedAudioFileException u){
      u.printStackTrace();
    }catch(IOException i){
      i.printStackTrace();
    }
    
    try{
      ais= AudioSystem.getAudioInputStream(path);
    }catch(UnsupportedAudioFileException u){
      u.printStackTrace();
    }catch(IOException i){
      i.printStackTrace();
    }
    
    AudioFormat audioFormat= ais.getFormat();
    DataLine.Info info= new DataLine.Info(SourceDataLine.class,audioFormat);
    
    try{
      line= (SourceDataLine) AudioSystem.getLine(info);
    }catch(LineUnavailableException l){
      l.printStackTrace();
      return;
    }
    
    try{
      line.open(audioFormat);
    }catch(LineUnavailableException l){
      l.printStackTrace();
      return;
    }
    
    line.start();
    try{
      byte bytes[]= new byte[1024];
      int bytesRead=0;
      while(((bytesRead= ais.read(bytes, 0, bytes.length)) != -1) && levelRunning){
        synchronized(this){
          if(!playing){
            try{
              wait();
            }catch(InterruptedException ie){}
          }
        }
        line.write(bytes,0, bytesRead);
      }
    }catch(IOException i){
      i.printStackTrace();
      return;
    }
  }
// Toutes le m�thodes sont li�es aux envents de changement de niveau.
  public void play(){
    playing=true;
    synchronized(this){
      notify();
    }
  }
  public void pause(){
    synchronized(this){
      playing= false;
    }
  }
  public void arret() {
    levelRunning= false;
  }

}