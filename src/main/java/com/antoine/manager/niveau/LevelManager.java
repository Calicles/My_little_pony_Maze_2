package com.antoine.manager.niveau;

import java.awt.Dimension;

import com.antoine.contracts.*;
import com.antoine.jeu.Game;


public class LevelManager implements Presentateur {

	IJeu game;

	public LevelManager()  {
		game= new Game();
	}


	public int getMapWidth() {return game.getMapWidth();}
	public int getMapHeight() {return game.getMapHeight();}


	public void switchLeveApple() {
		game.switchLeveApple();
	}

	public void switchLevelRarity() {
		game.switchLevelRarity();
	}

	public void switchLevelRainbow() {
		game.switchLevelRainbow();
	}

	public boolean isAppleSelectedAndRunning() {
		return game.isAppleSelectedAndRunning();
	}
	public boolean isRaritySelectedAndRunning() {
		return game.isRaritySelectedAndRunning();
	}
	public boolean isRainbowSelectedAndRunning() {
		return game.isRainbowSelectedAndRunning();
	}

	public void playerMovesLeft(){
		game.playerMovesLeft();
	}

	public void playerMovesRight(){
		game.playerMovesRight();
	}

	public void playerMovesUp(){
		game.playerMovesUp();
	}

	public void playerMovesDown(){
		game.playerMovesDown();
	}

	public void playerMovesReleased(){
		game.playerMovesReleased();
	}

	public Dimension getDimension() {
		return game.getDimension();
	}
	
	public void AddListener(LevelListener listener) {
		game.addListener(listener);
	}
	
	public void removeListener(LevelListener listener) {
		game.removeListener(listener);
	}
	

	public boolean isLevelsNull() {
		return game.isLevelsNull();
	}

	public boolean isLevelFlutterNull() {
		return game.isLevelFlutterNull();
	}

	public boolean isLevelPinkyNull() {
		return game.isLevelPinkyNull();
	}


	@Override
	public void accept(IAfficheur visiteur) {
		game.accept(visiteur);
	}
}
