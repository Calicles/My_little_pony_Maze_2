package com.antoine.modele.level;

import com.antoine.contracts.ILevel;

public class Level extends AbstractLevel implements ILevel {
	
	private boolean selected;

	public Level(){
		super();
	}


	public boolean isSelected() {return selected;}
	public void selected() {selected= true;}
	public void deselected() {selected= false;}
	
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
