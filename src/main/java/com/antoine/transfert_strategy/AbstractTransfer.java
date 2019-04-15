package com.antoine.transfert_strategy;

import com.antoine.contracts.IMap;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;

public abstract class AbstractTransfer {

	protected Coordinates vector;
	protected int xDirection, yDirection;


	public void setVector(Coordinates vector){
		this.vector= vector;
	}
	
	abstract Coordinates memorizeMoves(Rectangle position, IMap map);


	protected void adaptVectors(Rectangle position, IMap map) {
		
		//On cherche si vecteur null pas de calcul
		if(xDirection != 0 || yDirection !=0) {
			
			//On cherche la direction
			if(xDirection < 0) {
				
				checkLeft(position, map);
				
			}else if(xDirection > 0) {

				checkRight(position, map);
				
			}else if(yDirection < 0) {
				
				checkUp(position, map);
				
			}else if(yDirection > 0) {
				
				checkDown(position, map);
			}
		}
		
	}
	
	private void checkLeft(Rectangle position, IMap map) {
		
		Tile tile;

		int playerX= position.getBeginX();
		
		if(playerX <= map.getTile_width()){
			
			if(playerX < vector.getX()) {
				xDirection= 0 - playerX;
			}
			
		}else if((tile= checkLeftTiles(position, map)) != null) {
			
			if((tile.getX() + map.getTile_width()) - playerX >= -vector.getX())
				xDirection= (tile.getX() + map.getTile_width() + 1) - playerX;
		}
		yDirection= 0;
	}
	
	private void checkRight(Rectangle position, IMap map) {
		
		Tile tile;
		int playerEndX= position.getEndX();

		if(playerEndX >= (map.getWidth() - map.getTile_width())){
			if(playerEndX >= (map.getWidth() - vector.getX())) {
				xDirection= map.getWidth() - playerEndX;
			}

		}else if((tile= checkRightTiles(position, map)) != null) {
			if(tile.getX() - position.getEndX() <= vector.getX())
				xDirection= tile.getX() - (position.getEndX() + 1);

		}
		yDirection= 0;
	}
	
	private void checkUp(Rectangle position, IMap map) {
		
		Tile tile;
		int playerY= position.getBeginY();
		
		if(playerY <= map.getTile_height()){
			
			if(playerY < vector.getY()) {
				yDirection= 0 - playerY;
			}
			
		}else if((tile= checkOnUpTiles(position, map)) != null){
			
			if((tile.getY() + map.getTile_height()) - playerY >= -4)
				yDirection= (tile.getY() + map.getTile_height()+1) - playerY;
			
		}
		xDirection= 0;
	}
	
	private void checkDown(Rectangle position, IMap map) {
		
		Tile tile;
		int playerEndY= position.getEndY();

		if(playerEndY >= (map.getHeight() - map.getTile_height())) {
				if(playerEndY > (map.getHeight() - vector.getY())) {
					yDirection= map.getHeight() - position.getEndY();
				}
		}else if((tile= checkOnDownTiles(position, map)) != null) {
			if(tile.getY() - playerEndY <= vector.getY())
				yDirection= tile.getY() - (playerEndY+1);
		}
		xDirection= 0;
	}
	
	protected Tile checkOnDownTiles(Rectangle position, IMap map) {

		int x, y, endX;
		
		x= position.getBeginX() / map.getTile_width();
		endX= position.getEndX() / map.getTile_width();
		y= position.getEndY() / map.getTile_height() +1;

		return map.isSolidTileOnRoad(new Rectangle(new Coordinates(x, y), endX, y));
	}
	protected Tile checkOnUpTiles(Rectangle position, IMap map) {

		int x, y, endX;
		
		x= position.getBeginX() / map.getTile_width();
		endX= position.getEndX() / map.getTile_width();
		y= position.getBeginY() / map.getTile_height() -1;
		
		return map.isSolidTileOnRoad(new Rectangle(new Coordinates(x, y), endX, y));
	}
	protected Tile checkRightTiles(Rectangle position, IMap map) {
		
		int x, y, endY;
		
		x= position.getEndX() / map.getTile_width() +1;
		y= position.getBeginY() / map.getTile_height();
		endY= position.getEndY() / map.getTile_height();

		return map.isSolidTileOnRoad(new Rectangle(new Coordinates(x, y), x, endY));
	}
	
	protected Tile checkLeftTiles(Rectangle position, IMap map) {
		
		int x, y, endY;
		
		x= position.getBeginX() / map.getTile_width() -1;
		y= position.getBeginY() / map.getTile_height();
		endY= position.getEndY() / map.getTile_height();
		
		return map.isSolidTileOnRoad(new Rectangle(new Coordinates(x, y), x, endY));
	}
	
}
