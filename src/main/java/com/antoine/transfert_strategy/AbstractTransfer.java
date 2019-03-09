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
	
	abstract Coordinates memorizeMoves();


	public void adaptVectors(Rectangle position, IMap map) {
		
		//On cherche si vecteur null pas de calcul !
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
	
	protected void checkLeft(Rectangle position, IMap map) {
		
		Rectangle tile;
		int playerX= position.getBeginX();
		
		if(playerX < map.getTile_width()){
			
			if(playerX < 4) {
				xDirection= 0 - playerX;
			}
			
		}else if((tile= checkLeftTiles(position, map)) != null) {
			
			if(tile.getEndX() - playerX >= -4)
				xDirection= (tile.getEndX()+1) - playerX;
		}
		yDirection= 0;
	}
	
	protected void checkRight(Rectangle position, IMap map) {
		
		Rectangle tile;
		int playerEndX= position.getEndX();
		
		if(position.getEndX() > (map.getWidth() - map.getTile_width())){
			if(playerEndX >= map.getWidth() - 4) {
			
				xDirection= map.getWidth() - playerEndX;
		}
			
		}else if((tile= checkRightTiles(position, map)) != null) {
			if(tile.getBeginX() - position.getEndX() <= 4)
				xDirection= tile.getBeginX() - (position.getEndX() + 1);

		}
		yDirection= 0;
	}
	
	protected void checkUp(Rectangle position, IMap map) {
		
		Rectangle tile;
		int playerY= position.getBeginY();
		
		if(playerY <= map.getTile_height()){
			
			if(playerY < 4) {
				yDirection= 0 - playerY;
			}
			
		}else if((tile= checkOnUpTiles(position, map)) != null){
			
			if(tile.getEndY() - playerY >= -4)
				yDirection= (tile.getEndY()+1) - playerY;
			
		}
		xDirection= 0;
	}
	
	protected void checkDown(Rectangle position, IMap map) {
		
		Rectangle tile;
		int playerEndY= position.getEndY();

		if(playerEndY >= (map.getHeight() - map.getHeight())) {
				if(playerEndY > map.getHeight() - 4) {
					yDirection= map.getHeight() - position.getEndY();
				}
		}else if((tile= checkOnDownTiles(position, map)) != null) {
			if(tile.getBeginY() - playerEndY <= 4)
				yDirection= tile.getBeginY() - (playerEndY+1);
		}
		xDirection= 0;
	}
	
	protected Rectangle checkOnDownTiles(Rectangle position, IMap map) {

		int x, y, endX;
		
		x= position.getBeginX() / map.getTile_width();
		endX= position.getEndX() / map.getTile_width();
		y= position.getEndY() / map.getTile_height() +1;

		return isSolidTileOnRoad(new Rectangle(new Coordinates(x, y), endX, 0), map);
	}
	protected Rectangle checkOnUpTiles(Rectangle position, IMap map) {

		int x, y, endX;
		
		x= position.getBeginX() / map.getTile_width();
		endX= position.getEndX() / map.getTile_width();
		y= position.getBeginY() / map.getTile_height() -1;
		
		return isSolidTileOnRoad(new Rectangle(new Coordinates(x, y), endX, 0), map);
	}
	protected Rectangle checkRightTiles(Rectangle position, IMap map) {
		
		int x, y, endY;
		
		x= position.getEndX() / map.getTile_width() +1;
		y= position.getBeginY() / map.getTile_height();
		endY= position.getEndY() / map.getTile_height();
		
		return isSolidTileOnRoad(new Rectangle(new Coordinates(x, y), 0, endY), map);
	}
	
	protected Rectangle checkLeftTiles(Rectangle position, IMap map) {
		
		int x, y, endY;
		
		x= position.getBeginX() / map.getTile_width() -1;
		y= position.getBeginY() / map.getTile_height();
		endY= position.getEndY() / map.getTile_height();
		
		return isSolidTileOnRoad(new Rectangle(new Coordinates(x, y), 0, endY), map);
	}
	
	protected Rectangle isSolidTileOnRoad(Rectangle surface, IMap map) {
		int[][] tiles= map.getTiles();
		for(int i= surface.getBeginY(); i <= (surface.getEndY() - surface.getEndY()); i++) {
			
			for(int j= surface.getBeginX(); j <= (surface.getEndX() - surface.getEndX()); j++) {
				
				if(Tile.isSolid(tiles[i][j])){
					int x= j * map.getTile_width();
					int y= i * map.getTile_height();
					return new Rectangle(new Coordinates(x, y),
							map.getTile_width(), map.getTile_height());
					
				}
			}
		}
		return null;
	}
	
}
