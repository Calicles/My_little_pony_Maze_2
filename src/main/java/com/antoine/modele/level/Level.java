package com.antoine.modele.level;

import com.antoine.contracts.IEntity;
import com.antoine.contracts.ILevel;
import com.antoine.contracts.LevelListener;
import com.antoine.events.LevelChangeEvent;

import java.util.List;

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

	@Override
	public void setListeners(List<LevelListener> listeners) {

	}

	@Override
	public void start() {

	}

	@Override
	public IEntity getBoss() {
		return null;
	}

	@Override
	public void setEvent(LevelChangeEvent event) {

	}
}
