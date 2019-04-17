package com.antoine.transfert_strategy;

import com.antoine.contracts.IMap;
import com.antoine.contracts.ITransfert_strategy;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;

public class IA_transfertStrategy_std extends AbstractTransfer implements ITransfert_strategy {
	
	private Thread greyCell;
	private Rectangle ownPosition, player1;
	private Coordinates lastVector;
	private IMap map;
	private boolean thinking, blocked;
	
	public IA_transfertStrategy_std(Rectangle ownPosition, Rectangle player1, IMap map) {
		super();
	}
	
	public IA_transfertStrategy_std() {
		super();
	}

	@Override
	public void setVector(Coordinates vector){
		super.setVector(vector);
		this.xDirection= 0;
		this.yDirection= vector.getY();
		this.lastVector= new Coordinates();
	}

	public void setOwnPosition(Rectangle ownPosition){
		this.ownPosition= ownPosition;
	}

	public void setPlayer1(Rectangle player1){
		this.player1= player1;
	}

	public void setAttributes(Rectangle ownPosition, Rectangle player1, IMap map) {
		this.map= map;
		this.ownPosition= ownPosition;
		this.player1= player1;
	}

	public void startThinking(){
		buildThinkPattern();
		greyCell.start();
	}

	@Override
	public void think() {

		synchronized(this) {
			notify();
			thinking= true;
		}
	}

	private void buildThinkPattern() {
		greyCell= new Thread(()->{
			thinking= true;
			while(true){
				move();
				pause();
			}
		});
		
	}

	private void move(){

		if (isPlayerNext()){

			manHuntPlayer();

		}else {
			findWay();
		}
		this.adaptVectors(ownPosition, map);
	}
	
	private void findWay() {
		if(xDirection == 0 && yDirection == 0) {
			checkLastVectors();
		}
	}

	private void checkLastVectors() {

		Tile tile;
		if(lastVector.getX() < 0 || lastVector.getX() > 0) {
			if(ownPosition.getBeginY() != 0 && (tile= this.checkOnUpTiles(this.ownPosition, map)) == null) {
				yDirection= -vector.getY(); xDirection= 0;
			}else if(ownPosition.getEndY() != map.getHeight() && (tile= this.checkOnDownTiles(ownPosition, map)) == null) {
				yDirection= vector.getY(); xDirection= 0;
			}else {
				//GO back
				if(vector.getX() < 0)
					xDirection= vector.getX();
				else 
					xDirection= -vector.getX();
				yDirection= 0;
			}
		}else {
			if(ownPosition.getBeginX() != 0 && (tile= this.checkLeftTiles(ownPosition, map)) == null) {
				xDirection= -vector.getX(); yDirection= 0;
			}else if(ownPosition.getEndX() != map.getWidth() && (tile= this.checkRightTiles(ownPosition, map)) == null) {
				xDirection= vector.getX(); yDirection= 0;
			}else {
				if(vector.getY() < 0)
					yDirection= vector.getY();
				else
					yDirection= -vector.getY();
				xDirection= 0;
			}
		}

	}

	private void manHuntPlayer() {

		Coordinates bossMidle= Rectangle.findMiddleCoor(ownPosition);
		Coordinates playerMidle= Rectangle.findMiddleCoor(player1);


		//Player in height bounds
		if ((playerMidle.getY() > ownPosition.getBeginY() && playerMidle.getY() < ownPosition.getEndY()) &&
				//player not in width bounds
					!(playerMidle.getX() > ownPosition.getBeginX() && playerMidle.getX() < ownPosition.getEndX())) {

			//player on right side
			if (bossMidle.getX() < playerMidle.getX()) {

					xDirection = vector.getX();

			} else {

				xDirection = -vector.getX();
			}

			yDirection = 0;

		//player in width bounds
		} else {

			//player on down side
			if (bossMidle.getY() < playerMidle.getY()) {

				yDirection = vector.getY();

			} else {

				yDirection = -vector.getY();
			}

			xDirection = 0;
		}

		adaptVectors(ownPosition, map);

		//blocked
		if (directionIsNull()) {

			//take last direction
			xDirection = lastVector.getX();
			yDirection = lastVector.getY();

			adaptVectors(ownPosition, map);

			//again blocked
			if (directionIsNull()) {

				findWay();
			}
		}

	}

	private boolean isPlayerNext(){
		return Rectangle.isNext(ownPosition, player1, 300);
	}

	private void pause() {
		try {
			synchronized(this) {
				thinking= false;
				wait();
			}
		}catch(InterruptedException ignored) {}
	}

	@Override
	public void released() {
		xDirection= 0;
		yDirection= 0;
	}

	@Override
	public void movesLeft() {

	}

	@Override
	public void movesRight() {

	}

	@Override
	public void movesUp() {

	}

	@Override
	public void movesDown() {
		yDirection= vector.getY();
		xDirection= 0;
	}

	@Override
	public Coordinates memorizeMoves(Rectangle position, IMap map) {

		return null;
	}

	public Coordinates memorizeMoves() {
		if(!thinking) {
			// finished thinking
			if(xDirection != 0 || yDirection != 0){
				lastVector.setCoordinates(xDirection, yDirection);
			}
			return new Coordinates(xDirection, yDirection);
		}else									
			// don't moves
			return new Coordinates(0, 0);
	}

	private boolean directionIsNull(){
		return xDirection == 0 && yDirection == 0;
	}

}
