package com.antoine.jeu;

import com.antoine.contracts.*;
import com.antoine.services.Assembler;
import com.antoine.geometry.Rectangle;
import com.antoine.contracts.IJeu;

import java.awt.Dimension;
import java.util.ArrayList;

public class Game implements IJeu{
    private ILevel levelApple;
    private ILevel levelRarity;
    private ILevel levelRainbow;
    private ILevel levelFlutter;
    private ILevel levelPinky;
    private ILevel levelRunning;
    private ArrayList<LevelListener> listeners;
    private Assembler assembler;

    public Game()  {

        listeners= new ArrayList<>();
        assembler= new Assembler("./config/conf.xml");
        levelApple= (ILevel) assembler.newInstance("levelApple");
        levelRarity= (ILevel) assembler.newInstance("levelRarity");
        levelRainbow= (ILevel) assembler.newInstance("levelRainbow");
        levelRunning= levelApple;
        levelApple.selected();
        levelFlutter= null;
        levelPinky= null;
    }


    @Override
    public int getMapWidth() {return levelRunning.getMapWidth();}

    @Override
    public int getMapHeight() {return levelRunning.getMapHeight();}

    private void switchLevel4(){

        levelPinky= null;
        levelRunning= (ILevel) assembler.newInstance("levelTwilight");
    }

    private void switchLevel3() {

        levelFlutter= null;
        levelRunning= levelPinky= (ILevel) assembler.newInstance("levelPinky");
    }

    private void switchLevel2() {

        levelApple = levelRarity = levelRainbow = null;
        levelRunning= levelFlutter= (ILevel) assembler.newInstance("levelFlutter");
    }

    @Override
    public void switchLeveApple() {
        levelRunning= levelApple;
        levelApple.selected();
        levelRarity.deselected();
        levelRainbow.deselected();
        this.fireUpdate();
    }

    @Override
    public void switchLevelRarity() {
        levelRunning= levelRarity;
        levelRarity.selected();
        levelApple.deselected();
        levelRainbow.deselected();
        this.fireUpdate();
    }

    @Override
    public void switchLevelRainbow() {
        levelRunning= levelRainbow;
        levelRainbow.selected();
        levelApple.deselected();
        levelRarity.deselected();
        this.fireUpdate();
    }

    @Override
    public boolean isAppleSelectedAndRunning() {
        return !levelApple.isSelected() && levelApple.isRunning();
    }

    @Override
    public boolean isRaritySelectedAndRunning() {
        return !levelRarity.isSelected() && levelRarity.isRunning();
    }

    @Override
    public boolean isRainbowSelectedAndRunning() {
        return !levelRainbow.isSelected() && levelRainbow.isRunning();
    }

    @Override
    public void playerMovesLeft(){
        if(isLevelRunning()){
            levelRunning.playerMovesLeft();
        }
        this.fireUpdate();
    }

    @Override
    public void playerMovesRight(){
        if(isLevelRunning()){
            levelRunning.playerMovesRight();
        }
        this.fireUpdate();
    }

    @Override
    public void playerMovesUp(){
        if(isLevelRunning()){
            levelRunning.playerMovesUp();
        }
        this.fireUpdate();
    }

    @Override
    public void playerMovesDown(){
        if(isLevelRunning()){
            levelRunning.playerMovesDown();
        }
        this.fireUpdate();
    }

    private boolean isLevelRunning(){

        if(levelPinky== null && levelFlutter== null &&
                !levelApple.isRunning()
                && !levelRarity.isRunning()
                && !levelRainbow.isRunning()) {
            switchLevel2();
            return false;

        }else if(levelApple== null && levelFlutter!= null &&
                !levelFlutter.isRunning()) {
            switchLevel3();
            return false;
        }else if(levelApple == null && null == levelFlutter && levelPinky != null && !levelPinky.isRunning()){
            switchLevel4();
        }

        return true;
    }

    @Override
    public void playerMovesReleased(){
        levelRunning.playerMovesReleased();
        this.fireUpdate();
    }

    @Override
    public Dimension getDimension() {
        return levelRunning.getDimension();
    }

    @Override
    public void AddListener(LevelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(LevelListener listener) {
        listeners.remove(listener);
    }


    private void fireUpdate() {
        for(LevelListener l:listeners)
            l.update();
    }

    @Override
    public boolean isLevelsNull() {
        return levelApple == null;
    }

    @Override
    public boolean isLevelFlutterNull() {
        return levelFlutter == null;
    }

    @Override
    public boolean isLevelPinkyNull() {
        return levelPinky == null;
    }

    @Override
    public int getScreenX() {
        return levelPinky.getScreenX();
    }

    @Override
    public int getScreenY() {
        return levelPinky.getScreenY();
    }

    @Override
    public int getScreenWidth() {
        return levelPinky.getScreenWidth();
    }

    @Override
    public int getScreenHeight() {
        return levelPinky.getScreenHeight();
    }

    @Override
    public int getPlayerX() {
        return levelPinky.getPlayerX();
    }

    @Override
    public int getPlayerY() {
        return levelPinky.getPlayerY();
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

}

