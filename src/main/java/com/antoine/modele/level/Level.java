package com.antoine.modele.level;

import com.antoine.contracts.ILevel;

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
	public void drawLevel(Graphics g) throws IOException {
		if(!running) {
			endImage= ImageIO.read(new File(endImageUrl));
			g.drawImage(endImage, 0, 0, null);
		}else
			super.drawLevel(g);
	}

}
