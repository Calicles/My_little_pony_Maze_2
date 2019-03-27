package com.antoine.modele.level;

import com.antoine.contracts.IEntity;
import com.antoine.contracts.ILevel;
import com.antoine.contracts.IMap;
import com.antoine.contracts.LevelListener;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.DoubleBoxes;
import com.antoine.geometry.Rectangle;

import java.util.List;

public class Level3 extends AbstractLevel implements ILevel {
	
	protected DoubleBoxes boxes;


	public Level3(){
		super();
	}

	@Override
	public void setMap(IMap map){
		super.setMap(map);
		initBoxes();
	}

	private void initBoxes() {
		Rectangle screen= new Rectangle(0, 20*tile_width, 20*tile_height,
				40* tile_height);
		Rectangle scrollBox= new Rectangle(5*tile_width, 15*tile_width,
				25*tile_height, 35*tile_height);
		this.boxes= new DoubleBoxes(screen, scrollBox);
	}

	 @Override
	 public void playerMovesUp() {
		Coordinates vector;

		checkRunning();
		player.movesUp();
		vector= player.memorizeMoves(map);

		scrollUp(vector);
	 }

	 protected void scrollUp(Coordinates vector){
		 if(!screenOnTop() &&
				 boxes.isPlayerOnTopScroll(player.getY()+ vector.getY()))
			 boxes.scroll(0, vector.getY() );
	 }

	 protected void scrollDown(Coordinates vector){
		 if(!screenOnBottom() &&
				 boxes.isPlayerOnBottomScroll(player.getY()+
						 player.getHeight() + vector.getY()))
			 boxes.scroll(0, vector.getY());
	 }

	 protected void scrollLeft(Coordinates vector){
		 if(!screenOnLeft() &&
				 boxes.isPlayerOnLeftScroll(player.getX() + vector.getX()))
			 boxes.scroll(vector.getX(), 0);

	 }

	 protected void scrollRight(Coordinates vector){
		 if(!screenOnRight() &&
				 boxes.isPlayerOnRightScroll(player.getX() + player.getWidth() + vector.getX()))
			 boxes.scroll(vector.getX(), 0);
	 }
	 
	 @Override
	 public void playerMovesDown() {
		Coordinates vector;

		checkRunning();
		player.movesDown();
		vector= player.memorizeMoves(map);

		scrollDown(vector);
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
	 public void playerMovesLeft() {
		 Coordinates vector;

		 checkRunning();
		 player.movesLeft();
		 vector= player.memorizeMoves(map);

		 scrollLeft(vector);
	 }
	 
	 @Override
	 public void playerMovesRight() {
		Coordinates vector;

		checkRunning();
		player.movesRight();
		vector= player.memorizeMoves(map);

   		scrollRight(vector);
	 }
	 
	 private boolean screenOnRight() {
		 return boxes.getScreenEndX() >= mapSize.getEndX();
	 }
	 
	 private boolean screenOnLeft() {
		 return boxes.getScreenBeginX() <= 0;
	 }

	private boolean screenOnBottom() {
		return boxes.getScreenEndY() >= mapSize.getEndY();
	}

	private boolean screenOnTop() {
		return boxes.getScreenBeginY() <= 0;
	}

	public int getScreenX() {
		return boxes.getScreenBeginX();
	}

	public int getScreenY() {
		return boxes.getScreenBeginY();
	}

	public int getScreenWidth() {
		return boxes.getScreenWidth();
	}

	public int getScreenHeight() {
		return boxes.getScreenHeight();
	}

	public int getPlayerX() {
		return player.getX();
	}

	public int getPlayerY() {
		return player.getY();
	}

	@Override
	public Rectangle getScreen() {
		return boxes.getScreen();
	}

	@Override
	public IEntity getBoss() {
		return null;
	}

	@Override
	public void setListeners(List<LevelListener> listeners) {

	}

	@Override
	public void start() {

	}

}
