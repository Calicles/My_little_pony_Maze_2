package com.antoine.manager;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.antoine.contracts.ILevel;
import com.antoine.contracts.Presentateur;
import com.antoine.contracts.LevelListener;
import com.antoine.services.Assembler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

public class LevelManager implements Presentateur {

	private ILevel levelApple;
	private ILevel levelRarity;
	private ILevel levelRainbow;
	private ILevel levelFlutter;
	private ILevel levelPinky;
	private ILevel levelRunning;
	private ArrayList<LevelListener> listeners;
	private  Assembler assembler;
	
	public LevelManager() throws IOException, ParserConfigurationException, SAXException,
			ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException,
			IllegalAccessException {

		listeners= new ArrayList<>();
		assembler= new Assembler();
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

	private void switchLevel3() throws IOException, ClassNotFoundException, NoSuchMethodException,
			InvocationTargetException, InstantiationException, IllegalAccessException {

		levelRunning= levelPinky= (ILevel) assembler.newInstance("levelPinky");
		levelFlutter= null;
	}
	
	private void switchLevel2() throws IOException, ClassNotFoundException, NoSuchMethodException,
			InvocationTargetException, InstantiationException, IllegalAccessException {

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

	@Override
	public void playerMoves(int xVector, int yVector) throws IOException, ClassNotFoundException,
			NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

		if(levelPinky== null && levelFlutter== null &&
				!levelApple.isRunning()
				&& !levelRarity.isRunning() 
				&& !levelRainbow.isRunning()) {
			switchLevel2();
		}else if(levelApple== null && levelFlutter!= null &&
				!levelFlutter.isRunning()) {
			switchLevel3();
		}else {
			if(xVector == 0) {
				if(yVector < 0) {
					this.playerMovesUp();
				}else {
					this.playerMovesDown();
				}
			}else {
				if(xVector < 0) {
					this.playerMovesLeft();
				}else {
					this.playerMovesRight();
				}
			}
		}	
		this.fireUpdate();
	}

	public void playerMovesLeft() {
		levelRunning.playerMovesLeft();
	}
	public void playerMovesRight() {
		levelRunning.playerMovesRight();
	}
	public void playerMovesUp() {
		levelRunning.playerMovesUp();
	}
	public void playerMovesDown() {
		levelRunning.playerMovesDown();
	}

	public void playerMovesReleased(){
		levelRunning.playerMovesReleased();
		this.fireUpdate();
	}

	public void draw(Graphics g) throws IOException {
		levelRunning.drawLevel(g);
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

	public void drawMiniMap(Graphics g, int ECHELLE) {
		levelPinky.drawMiniMap(g, ECHELLE);
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
	
}
