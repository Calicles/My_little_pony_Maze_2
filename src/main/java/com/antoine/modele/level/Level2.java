package com.antoine.modele.level;

import com.antoine.contracts.IEntity;
import com.antoine.contracts.ILevel;
import com.antoine.contracts.LevelListener;
import com.antoine.events.LevelChangeEvent;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;

import java.util.List;

public class Level2 extends AbstractLevel implements ILevel {
	
	private Rectangle screen;


	public Level2(){
		super();
		screen= new Rectangle(0, 20, 20, 40);

	}

	@Override
	public Rectangle getScreen(){
		return screen;
	}

	@Override
	public IEntity getBoss() {
		return null;
	}

	@Override
	public void setEvent(LevelChangeEvent event) {

	}

	@Override
	public void setListeners(List<LevelListener> listeners) {

	}

	@Override
	public void start() {

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
		return 0;
	}

	@Override
	public int getPlayerY() {
		return 0;
	}

	@Override
	public void selected() {

	}

	@Override
	public void deselected() {

	}

	@Override
	public boolean isSelected() {
		return false;
	}

	@Override
	public void playerMovesUp() {
		if(!isOnTop(-8) && isOnTopScreen())
			loadMap(0, -20, -player.getHeight());
		super.playerMovesUp();
	}
	@Override
	public void playerMovesDown() {
		if(!isOnBottom(4) && isOnBottomScreen())
			loadMap(0, 20, player.getHeight());
		super.playerMovesDown();
	}
	
	private boolean isOnBottomScreen() {
		return (playerScreenPositionY() + player.getHeight()) > 
			(20 * tile_height - 4);
	}

	private boolean isOnTopScreen() {
		return playerScreenPositionY() <= 4;
	}
	
	private void loadMap(int xVector, int yVector, int playerVector) {
		screen.translate(xVector, yVector);
		player.translate(new Coordinates(0, playerVector));
	}

	private int playerScreenPositionY() {
		int coef=0;
		coef= player.getY() / (tile_height * 20);
		return player.getY() - (coef * (tile_height * 20));
	}

}
