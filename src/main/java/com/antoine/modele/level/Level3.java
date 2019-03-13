package com.antoine.modele.level;

import com.antoine.contracts.ILevel;
import com.antoine.contracts.IMap;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.DoubleBoxes;
import com.antoine.geometry.Rectangle;

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

	protected void initBoxes() {
		Rectangle screen= new Rectangle(0, 20*tile_width, 20*tile_height,
				40* tile_height);
		Rectangle scrollBox= new Rectangle(5*tile_width, 15*tile_width,
				25*tile_height, 35*tile_height);
		this.boxes= new DoubleBoxes(screen, scrollBox);
	}

	 @Override
	 public boolean playerMovesUp() {
		Coordinates vector;

		player.movesUp();
		vector= player.memorizeMoves(map);

		 if(!screenOnTop() && 
				 boxes.isPlayerOnTopScroll(player.getY()+ vector.getY()))
			 boxes.scroll(0, vector.getY() );

		 return vector.isZero();
	 }
	 
	 @Override
	 public boolean playerMovesDown() {
		Coordinates vector;

		player.movesDown();
		vector= player.memorizeMoves(map);

		 if(!screenOnBottom() &&
				 boxes.isPlayerOnBottomScroll(player.getY()+
						 player.getHeight() + vector.getY()))
			 boxes.scroll(0, vector.getY());

		 return vector.isZero();
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
	 public boolean playerMovesLeft() {
		Coordinates vector;

		 player.movesLeft();
		 vector= player.memorizeMoves(map);

		 if(!screenOnLeft() && 
				 boxes.isPlayerOnLeftScroll(player.getX() + vector.getX()))
			 boxes.scroll(vector.getX(), 0);

		 return vector.isZero();
	 }
	 
	 @Override
	 public boolean playerMovesRight() {
		Coordinates vector;

		player.movesRight();
		vector= player.memorizeMoves(map);

		 if(!screenOnRight() &&
				 boxes.isPlayerOnRightScroll(player.getX() + player.getWidth() + vector.getX()))
			 boxes.scroll(vector.getX(), 0);

		 return vector.isZero();
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
}
