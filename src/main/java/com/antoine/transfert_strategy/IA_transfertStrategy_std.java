package com.antoine.transfert_strategy;

import com.antoine.contracts.IA;
import com.antoine.contracts.IMap;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;

public class IA_transfertStrategy_std extends AbstractTransfer implements IA {
	
	private Thread greyCell;
	private Rectangle ownPosition, player1;
	private Coordinates lastVectors;
	private IMap map;
	private boolean thinking;
	
	public IA_transfertStrategy_std(Rectangle ownPosition, Rectangle player1, IMap map) {
		super();
		this.xDirection= 0;
		this.yDirection= -4;
		this.lastVectors= new Coordinates(0, 0);
	}
	
	public IA_transfertStrategy_std() {
		super();
		this.xDirection= 0;
		this.yDirection= -4;
		this.lastVectors= new Coordinates(0, 0);
	}
	
	public void setAttributes(Rectangle ownPosition, Rectangle player1, IMap map) {
		this.map= map;
		this.ownPosition= ownPosition;
		this.player1= player1;
		buildThinkPattern();
		greyCell.start();
	}

    @Override
    public Coordinates memorizMoves() {
        return null;
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
		if(lastVectors.getX() < 0 || lastVectors.getX() > 0) {
			if(ownPosition.getBeginY() != 0 && (tile= this.checkOnUpTiles(this.ownPosition, map)) == null) {
				yDirection= -4; xDirection= 0;
			}else if(ownPosition.getEndY() != map.getHeight() && (tile= this.checkOnDownTiles(ownPosition, map)) == null) {
				yDirection= 4; xDirection= 0;
			}else {
				//GO back
				if(lastVectors.getX() < 0)
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
				if(lastVectors.getY() < 0)
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

	public void think(Coordinates ownPosition, Coordinates player1, Coordinates player2) {
		update(ownPosition, player1);
		
		synchronized(this) {
			notify();
			thinking= true;
		}
	}

	private void update(Coordinates ownPosition, Coordinates player1) {
		this.ownPosition.setCoordinates(ownPosition);
		this.player1.setCoordinates(player1);
	}

	private void pause() {
		try {
			synchronized(this) {
				thinking= false;
				wait();
			}
		}catch(InterruptedException ie) {}
	}

	public Coordinates memorizeMoves() {
		if(!thinking) {
			// finished thinking
			if(xDirection != 0 || yDirection != 0)
				lastVectors.setCoordinates(xDirection, yDirection);
			return new Coordinates(xDirection, yDirection);
		}else									
			// don't moves
			return new Coordinates(0, 0);
	}

	public void think(Rectangle position, Coordinates player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	Coordinates memorizeMoves(Rectangle position, IMap map) {
		return null;
	}
}
