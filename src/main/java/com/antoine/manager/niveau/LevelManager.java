package com.antoine.manager.niveau;

import java.awt.Dimension;
import java.util.ArrayList;

import com.antoine.contracts.*;
import com.antoine.geometry.Rectangle;
import com.antoine.services.Assembler;
import com.antoine.son.bruitage.SoundPlayer;


public class LevelManager implements Presentateur, IStructure {


	private ILevel levelApple;
	private ILevel levelRarity;
	private ILevel levelRainbow;
	private ILevel levelFlutter;
	private ILevel levelPinky;
	private ILevel levelTwilight;
	private ILevel levelRunning;
	private ArrayList<LevelListener> listeners;
	private  Assembler assembler;
	private SoundPlayer walkSound;

	public LevelManager()  {

		String configPath= String.valueOf(getClass().getResource("/config/conf.xml"));
		System.out.println(configPath);

		listeners= new ArrayList<>();
		assembler= new Assembler(configPath);
		levelApple= (ILevel) assembler.newInstance("levelApple");
		levelRarity= (ILevel) assembler.newInstance("levelRarity");
		levelRainbow= (ILevel) assembler.newInstance("levelRainbow");
		levelRunning= levelApple;
		levelApple.selected();
		levelFlutter= null;
		levelPinky= null;
		walkSound= new SoundPlayer("/ressources/sons/bruitage/trotDur.wav");
	}


	public int getMapWidth() {return levelRunning.getMapWidth();}
	public int getMapHeight() {return levelRunning.getMapHeight();}

	private void switchLevel4(){

		levelPinky= null;
		levelRunning= levelTwilight= (ILevel) assembler.newInstance("levelTwilight");
		levelTwilight.setListeners(listeners);
		levelTwilight.start();
	}

	private void switchLevel3() {

		levelFlutter= null;
		levelRunning= levelPinky= (ILevel) assembler.newInstance("levelPinky");
	}
	
	private void switchLevel2() {

		levelApple=null; levelRarity= null; levelRainbow= null;
		levelRunning= levelFlutter= (ILevel) assembler.newInstance("levelFlutter");
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
		if(isLevelRunning()){
			levelRunning.playerMovesLeft();
			walkSound.play();
		}
		this.fireUpdate();
	}

	public void playerMovesRight(){
		if(isLevelRunning()){
			levelRunning.playerMovesRight();
			walkSound.play();
		}
		this.fireUpdate();
	}

	public void playerMovesUp(){
		if(isLevelRunning()){
			levelRunning.playerMovesUp();
			walkSound.play();
		}
		this.fireUpdate();
	}

	public void playerMovesDown(){
		if(isLevelRunning()){
			levelRunning.playerMovesDown();
			walkSound.play();
		}
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
		walkSound.stop();
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
}
