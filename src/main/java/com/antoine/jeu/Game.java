package com.antoine.jeu;

import com.antoine.contracts.*;
import com.antoine.events.LevelChangeEvent;
import com.antoine.manager.musique.Jukebox;
import com.antoine.services.Assembler;
import com.antoine.geometry.Rectangle;
import com.antoine.contracts.IJeu;
import com.antoine.structure_donnee.LevelState;

import java.awt.Dimension;
import java.util.ArrayList;

public class Game implements IJeu {

    private ILevel levelApple;
    private ILevel levelRarity;
    private ILevel levelRainbow;
    private ILevel levelFlutter;
    private ILevel levelPinky;
    private ILevel levelTwilight;
    private ILevel levelRunning;
    private ArrayList<LevelListener> listeners;
    private Assembler assembler;
    private Jukebox jukebox;
    private LevelChangeEvent event;

    public Game()  {
        String configPath= String.valueOf(getClass().getResource("/config/conf.xml"));

        listeners= new ArrayList<>();
        assembler= new Assembler(configPath);
        levelApple= (ILevel) assembler.newInstance("levelApple");
        levelRarity= (ILevel) assembler.newInstance("levelRarity");
        levelRainbow= (ILevel) assembler.newInstance("levelRainbow");
        levelRunning= levelApple;
        levelApple.selected();
        levelFlutter= null;
        levelPinky= null;
        jukebox = (Jukebox) assembler.newInstance("jukebox");
        jukebox.switchTo("apple");
        event = new LevelChangeEvent();
        setEventSelected(true, false, false);
        setEventRunning(true, true, true);
    }


    public int getMapWidth() {return levelRunning.getMapWidth();}
    public int getMapHeight() {return levelRunning.getMapHeight();}

    private void switchLevel4(){

        levelPinky= null;
        levelRunning= levelTwilight= (ILevel) assembler.newInstance("levelTwilight");

        event.setBooleanTable(LevelState.PINKY_RUNNING, false);
        event.setBooleanTable(LevelState.TWILIGHT_RUNNING, true);

        levelTwilight.setListeners(listeners);
        levelTwilight.setEvent(event);
        levelTwilight.start();
    }

    private void switchLevel3() {

        levelFlutter= null;
        levelRunning= levelPinky= (ILevel) assembler.newInstance("levelPinky");

        event.setBooleanTable(LevelState.FLUTTER_RUNNING, false);
        event.setBooleanTable(LevelState.PINKY_RUNNING, true);
    }

    private void switchLevel2() {

        levelApple = levelRarity = levelRainbow = null;
        levelRunning= levelFlutter= (ILevel) assembler.newInstance("levelFlutter");

        event.setBooleanTable(LevelState.FLUTTER_RUNNING, true);
    }

    public void switchLeveApple() {
        levelRunning= levelApple;
        levelApple.selected();
        levelRarity.deselected();
        levelRainbow.deselected();
        jukebox.switchTo("apple");
        setEventSelected(true, false, false);
        this.fireUpdate();
    }
    public void switchLevelRarity() {
        levelRunning= levelRarity;
        levelRarity.selected();
        levelApple.deselected();
        levelRainbow.deselected();
        jukebox.switchTo("rarity");
        setEventSelected(false, true, false);
        this.fireUpdate();

    }
    public void switchLevelRainbow() {
        levelRunning= levelRainbow;
        levelRainbow.selected();
        levelApple.deselected();
        levelRarity.deselected();
        jukebox.switchTo("apple");
        setEventSelected(false, false, true);
        this.fireUpdate();
    }

    public boolean isAppleSelectedAndRunning() {
        return !levelApple.isSelected() && levelApple.isRunning();
    }
    public boolean isRaritySelectedAndRunning() {
        return !levelRarity.isSelected() && levelRarity.isRunning();
    }
    public boolean isRainbowSelectedAndRunning() {
        return !levelRainbow.isSelected() && levelRainbow.isRunning();
    }

    public void playerMovesLeft(){
        if(isLevelRunning()){
            levelRunning.playerMovesLeft();
            jukebox.makeSound();
        }
        event.setBooleanTable(LevelState.get(levelRunning.getId()), levelRunning.isRunning());
        this.fireUpdate();
    }

    public void playerMovesRight(){
        if(isLevelRunning()){
            levelRunning.playerMovesRight();
            jukebox.makeSound();
        }
        event.setBooleanTable(LevelState.get(levelRunning.getId()), levelRunning.isRunning());
        this.fireUpdate();
    }

    public void playerMovesUp(){
        if(isLevelRunning()){
            levelRunning.playerMovesUp();
            jukebox.makeSound();
        }
        event.setBooleanTable(LevelState.get(levelRunning.getId()), levelRunning.isRunning());
        this.fireUpdate();
    }

    public void playerMovesDown(){
        if(isLevelRunning()){
            levelRunning.playerMovesDown();
            jukebox.makeSound();
        }
        event.setBooleanTable(LevelState.get(levelRunning.getId()), levelRunning.isRunning());
        this.fireUpdate();
    }

    private boolean isLevelRunning(){

        if(isLevelPinkyNull() && isLevelFlutterNull() && levelTwilight == null &&
                !levelApple.isRunning()
                && !levelRarity.isRunning()
                && !levelRainbow.isRunning()) {
            switchLevel2();
            return false;

        }else if(isLevelsNull() && !isLevelFlutterNull() &&
                !levelFlutter.isRunning()) {
            switchLevel3();
            return false;
        }else if(isLevelsNull() && isLevelFlutterNull() && !isLevelPinkyNull() && !levelPinky.isRunning()){
            switchLevel4();
            return false;
        }
        return true;
    }

    public void playerMovesReleased(){
        levelRunning.playerMovesReleased();
        this.fireUpdate();
    }

    public Dimension getDimension() {
        return levelRunning.getDimension();
    }

    @Override
    public void addListener(LevelListener listener) {
        listeners.add(listener);
        fireUpdate();
    }

    @Override
    public void removeListener(LevelListener listener) {
        listeners.remove(listener);
    }


    private void fireUpdate() {
        for(LevelListener l:listeners)
            l.update(event);
    }

    public boolean isLevelsNull() {
        return levelApple == null;
    }

    public boolean isLevelFlutterNull() {
        return levelFlutter == null;
    }

    public boolean isLevelPinkyNull() {
        return levelPinky == null;
    }

    public int getScreenX() {
        return levelRunning.getScreenX();
    }

    public int getScreenY() {
        return levelRunning.getScreenY();
    }

    public int getScreenWidth() {
        return levelRunning.getScreenWidth();
    }

    public int getScreenHeight() {
        return levelRunning.getScreenHeight();
    }

    public int getPlayerX() {
        return levelRunning.getPlayerX();
    }

    public int getPlayerY() {
        return levelRunning.getPlayerY();
    }

    @Override
    public void accept(IAfficheur visiteur) {
        visiteur.visit((IStructure) this.levelRunning);
    }

    @Override
    public IMap getMap() {
        return levelRunning.getMap();
    }

    @Override
    public IEntity getPlayer() {
        return levelRunning.getPlayer();
    }

    @Override
    public boolean isRunning() {
        return levelRunning.isRunning();
    }

    @Override
    public String getEndImageUrl() {
        return levelRunning.getEndImageUrl();
    }

    @Override
    public Rectangle getScreen() {
        return levelRunning.getScreen();
    }

    @Override
    public IEntity getBoss() {
        return levelRunning.getBoss();
    }

    @Override
    public Jukebox getJukebox() {
        return jukebox;
    }

    private void setEventSelected(boolean level1Selected, boolean level2Selected, boolean level3Selected){
        event.setBooleanTable(LevelState.APPLE_SELECTED, level1Selected);
        event.setBooleanTable(LevelState.RARITY_SELECTED, level2Selected);
        event.setBooleanTable(LevelState.RAINBOW_SELECTED, level3Selected);
    }

    private void setEventRunning(boolean level1Running, boolean level2Running, boolean level3Running){
        event.setBooleanTable(LevelState.APPLE_RUNNING, level1Running);
        event.setBooleanTable(LevelState.RARITY_RUNNING, level2Running);
        event.setBooleanTable(LevelState.RAINBOW_RUNNING, level3Running);
    }
}

