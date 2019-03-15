package com.antoine.transfert_strategy;

import com.antoine.contracts.IMap;
import com.antoine.contracts.ITransfert_strategy;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;

public class IA_transfertStrategy_std extends AbstractTransfer implements ITransfert_strategy {
	
	private Thread greyCell;
	private Rectangle ownPosition, player1;
	private IMap map;
	private boolean thinking;
	
	public IA_transfertStrategy_std(Rectangle ownPosition, Rectangle player1, IMap map) {
		super();
		this.xDirection= 0;
		this.yDirection= -4;
	}
	
	public IA_transfertStrategy_std() {
		super();
		this.xDirection= 0;
		this.yDirection= -4;
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
		/*	if(Rectangle.isNext(ownPosition, player1, ownPosition.getDiagonal()))
				manHuntPlayer1();
			else if(Rectangle.isNext(ownPosition, player2, ownPosition.getDiagonal()))
				manHuntPlayer2();
			else {	
				findWay();
			}*/ findWay(); //TODO Remove
				pause();
			}
		});
		
	}
	
	private void findWay() {
		if(xDirection == 0 && yDirection == 0) {
			checkLastVectors();
		}
		this.adaptVectors(ownPosition, map);
	}

	private void checkLastVectors() {

		Tile tile;
		if(vector.getX() < 0 || vector.getX() > 0) {
			if(ownPosition.getBeginY() != 0 && (tile= this.checkOnUpTiles(this.ownPosition, map)) == null) {
				yDirection= -4; xDirection= 0;
			}else if(ownPosition.getEndY() != map.getHeight() && (tile= this.checkOnDownTiles(ownPosition, map)) == null) {
				yDirection= 4; xDirection= 0;
			}else {
				//GO back
				if(vector.getX() < 0)
					xDirection= 4;
				else 
					xDirection= -4;
				yDirection= 0;
			}
		}else {
			if(ownPosition.getBeginX() != 0 && (tile= this.checkLeftTiles(ownPosition, map)) == null) {
				xDirection= -4; yDirection= 0;
			}else if(ownPosition.getEndX() != map.getWidth() && (tile= this.checkRightTiles(ownPosition, map)) == null) {
				xDirection= 4; yDirection= 0;
			}else {
				if(vector.getY() < 0)
					yDirection= 4;
				else
					yDirection= -4;
				xDirection= 0;
			}
		}

	}

	private void manHuntPlayer() {
		// TODO Auto-generated method stub
		
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

	}

	@Override
	public Coordinates memorizeMoves(Rectangle position, IMap map) {
		return null;
	}

	public Coordinates memorizeMoves() {
		if(!thinking) {
			// finished thinking
			if(xDirection != 0 || yDirection != 0)
				vector.setCoordinates(xDirection, yDirection);
			return new Coordinates(xDirection, yDirection);
		}else									
			// don't moves
			return new Coordinates(0, 0);
	}

}
