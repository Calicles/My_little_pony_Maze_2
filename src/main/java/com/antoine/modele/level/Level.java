package com.antoine.modele.level;

import com.antoine.contracts.ILevel;
import com.antoine.contracts.IVisiteur;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Level extends AbstractLevel implements ILevel {
	
	protected boolean selected;

	public Level(){
		super();
	}


	public boolean isSelected() {return selected;}
	public void selected() {selected= true;}
	public void deselected() {selected= false;}
	
	@Override
	public void drawLevel(Graphics g) {
		if(!running) {
			try {
				endImage= ImageIO.read(new File(endImageUrl));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(endImage, 0, 0, null);
		}else
			super.drawLevel(g);
	}

	@Override
	public void drawMiniMap(Graphics g, int echelle) {

	}

	@Override
	public int getScreenX() {
		return 0;
	}

	@Override
	public int getScreenY() {
		return 0;
	}

	@Override
	public int getScreenWidth() {
		return 0;
	}

	@Override
	public int getScreenHeight() {
		return 0;
	}

	@Override
	public int getPlayerX() {
		return player.getX();
	}

	@Override
	public int getPlayerY() {
		return player.getY();
	}

}
