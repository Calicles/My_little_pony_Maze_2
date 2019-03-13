package com.antoine.modele.level;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.antoine.contracts.IEntity;
import com.antoine.contracts.IMap;
import com.antoine.contracts.IStructure;
import com.antoine.contracts.IVisiteur;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;

public abstract class AbstractLevel implements IStructure {

	protected IMap map;
	protected IEntity player;
	protected Rectangle mapSize;
	protected Rectangle exit;
	protected BufferedImage endImage;
	
	protected String endImageUrl;
	protected boolean running;
	protected int tile_width, tile_height;
	

	public AbstractLevel(){
		running= true;
	}

	public void setMap(IMap map){
		this.map= map;
		tile_width= map.getTile_width();
		tile_height= map.getTile_height();
		exit= tileToRectangle(map.findExit());
		setMapSize();
	}

	/*
	pour test
	 */
	public IEntity getPlayer(){return player;}
	public String getEndImageUrl(){return endImageUrl;}
	public IMap getMap(){return map;}
	/*
	fin pour test
	 */

	public void setEndImageUrl(String endImageUrl){
		this.endImageUrl= endImageUrl;
	}

	public void setPlayer(IEntity player){
		this.player= player;
	}
	
	public int getMapWidth(){return mapSize.getWidth();}
	public int getMapHeight(){return mapSize.getHeight();}

	public Dimension getDimension() {return mapSize.getDimension();}
	public boolean isRunning() {return running;}

	public Rectangle getScreen(){ return null;}

	public void setMapSize() {
		int[] tab= map.getDimension();
		mapSize= new Rectangle(tab[0], tab[1]);
	}

	public void playerMovesReleased(){
		player.movesReleased();
	}
	
	public boolean playerMovesLeft() {
		if(isPlayerOnExit()) {
			running= false;
			return false;
		}

		player.movesLeft();

		return player.memorizeMoves(map).isZero();
	}

	public boolean playerMovesRight() {
		if(isPlayerOnExit()) {
			running= false;
			return false;
		}

		player.movesRight();

		return player.memorizeMoves(map).isZero();
	}

	public boolean playerMovesUp() {
		if (isPlayerOnExit()) {
			running = false;
			return false;
		}

		player.movesUp();

		return player.memorizeMoves(map).isZero();

	}

	public boolean playerMovesDown() {
		if(isPlayerOnExit()) {
			running= false;
			return false;
		}

		player.movesDown();
		return player.memorizeMoves(map).isZero();

	}
	

	public boolean isOnLeft(int toTest) {
		return mapSize.isOnLeft(player.getX() + toTest);
	}

	public boolean isOnRight(int toTest) {
		int x= player.getX() + player.getWidth();
		return mapSize.isOnRight(x + toTest);
	}

	public boolean isOnTop(int toTest) {
		return mapSize.isOnTop(player.getY() + toTest);
	}

	public boolean isOnBottom(int toTest) {
		int y= player.getY() + player.getHeight();
		return mapSize.isOnBottom(y + toTest);
	}

	public boolean isIEntityInBox(Rectangle entity, Rectangle rec) {
		return Rectangle.isInBox(rec, entity);
	}

	public boolean isPlayerOnExit() {
		return isIEntityInBox(player.toRectangle(), exit);
	}

	private Rectangle tileToRectangle(Tile tile) {
		int endX= tile.getX() + tile_width * 2;
		int endY= tile.getY() + tile_height * 2;
		return new Rectangle(tile.getX(), endX, tile.getY(), endY);

	}

	public void accept(IVisiteur visiteur){
		visiteur.visit(this);
	}


}
