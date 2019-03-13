package com.antoine.manager;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import com.antoine.contracts.*;
import com.antoine.geometry.Rectangle;
import com.antoine.services.Assembler;


public class LevelManager implements Presentateur, IStructure {

	private ILevel levelApple;
	private ILevel levelRarity;
	private ILevel levelRainbow;
	private ILevel levelFlutter;
	private ILevel levelPinky;
	private ILevel levelRunning;
	private ArrayList<LevelListener> listeners;
	private  Assembler assembler;
	
	public LevelManager()  {

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


	public int getMapWidth() {return levelRunning.getMapWidth();}
	public int getMapHeight() {return levelRunning.getMapHeight();}

	private void switchLevel3() {

		levelRunning= levelPinky= (ILevel) assembler.newInstance("levelPinky");
		levelFlutter= null;
	}
	
	private void switchLevel2() {

		levelRunning= levelFlutter= (ILevel) assembler.newInstance("levelFlutter");
		levelApple=null; levelRarity= null; levelRainbow= null;
	}
	
	public void switchLeveApple() {
		levelRunning= levelApple;
		levelApple.selected();
		levelRarity.deselected();
		levelRainbow.deselected();
		this.fireUpdate();
	}
	public void switchLevelRarity() {
		levelRunning= levelRarity;
		levelRarity.selected();
		levelApple.deselected();
		levelRainbow.deselected();
		this.fireUpdate();
	}
	public void switchLevelRainbow() {
		levelRunning= levelRainbow;
		levelRainbow.selected();
		levelApple.deselected();
		levelRarity.deselected();
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
		if(!isLevelOver()){
			levelRunning.playerMovesLeft();
		}
		this.fireUpdate();
	}

	public void playerMovesRight(){
		if(!isLevelOver()){
			levelRunning.playerMovesRight();
		}
		this.fireUpdate();
	}

	public void playerMovesUp(){
		if(!isLevelOver()){
			levelRunning.playerMovesUp();
		}
		this.fireUpdate();
	}

	public void playerMovesDown(){
		if(!isLevelOver()){
			levelRunning.playerMovesDown();
		}
		this.fireUpdate();
	}

	private boolean isLevelOver(){

		if(levelPinky== null && levelFlutter== null &&
				!levelApple.isRunning()
				&& !levelRarity.isRunning()
				&& !levelRainbow.isRunning()) {
			switchLevel2();
			return true;

		}else if(levelApple== null && levelFlutter!= null &&
				!levelFlutter.isRunning()) {
			switchLevel3();
			return true;
		}
		return false;
	}

	public void playerMovesReleased(){
		levelRunning.playerMovesReleased();
		this.fireUpdate();
	}

	public Dimension getDimension() {
		return levelRunning.getDimension();
	}
	
	public void AddListener(LevelListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(LevelListener listener) {
		listeners.remove(listener);
	}
	

	private void fireUpdate() {
		for(LevelListener l:listeners)
			l.update();
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
		return levelPinky.getScreenX();
	}

	public int getScreenY() {
		return levelPinky.getScreenY();
	}

	public int getScreenWidth() {
		return levelPinky.getScreenWidth();
	}

	public int getScreenHeight() {
		return levelPinky.getScreenHeight();
	}

	public int getPlayerX() {
		return levelPinky.getPlayerX();
	}

	public int getPlayerY() {
		return levelPinky.getPlayerY();
	}

	@Override
	public void accept(IVisiteur visiteur) {
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
