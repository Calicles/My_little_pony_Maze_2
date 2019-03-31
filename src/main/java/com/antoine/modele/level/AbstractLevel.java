package com.antoine.modele.level;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import com.antoine.contracts.IEntity;
import com.antoine.contracts.IMap;
import com.antoine.contracts.IStructure;
import com.antoine.contracts.IAfficheur;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;

public abstract class AbstractLevel implements IStructure {

	protected IMap map;
	protected IEntity player;
	protected Rectangle mapSize;
	private Rectangle exit;
	protected BufferedImage endImage;
	
	private String endImageUrl;
	protected boolean running;
	protected int tile_width, tile_height;
	

	protected AbstractLevel(){
		running= true;
	}

	public void setMap(IMap map){
		this.map= map;
		tile_width= map.getTile_width();
		tile_height= map.getTile_height();
		exit= tileToRectangle(map.findExit());
		setMapSize();
	}

	@Override
	public IEntity getPlayer(){return player;}

	@Override
	public String getEndImageUrl(){return endImageUrl;}

	@Override
	public IMap getMap(){return map;}

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

	@Override
	public Rectangle getScreen(){ return null;}

	private void setMapSize() {
		int[] tab= map.getDimension();
		mapSize= new Rectangle(tab[0], tab[1]);
	}

	public void playerMovesReleased(){
		player.movesReleased();
	}
	
	public void playerMovesLeft() {
		checkRunning();
		player.movesLeft();

		player.memorizeMoves(map);
	}

	public void playerMovesRight() {
		checkRunning();
		player.movesRight();

		player.memorizeMoves(map);
	}

	public void playerMovesUp() {
		checkRunning();
		player.movesUp();

		player.memorizeMoves(map);

	}

	public void playerMovesDown() {
		checkRunning();
		player.movesDown();
		player.memorizeMoves(map);

	}
	

	public boolean isOnLeft(int toTest) {
		return mapSize.isOnLeft(player.getX() + toTest);
	}

	public boolean isOnRight(int toTest) {
		int x= player.getX() + player.getWidth();
		return mapSize.isOnRight(x + toTest);
	}

	protected boolean isOnTop(int toTest) {
		return mapSize.isOnTop(player.getY() + toTest);
	}

	protected boolean isOnBottom(int toTest) {
		int y= player.getY() + player.getHeight();
		return mapSize.isOnBottom(y + toTest);
	}

	private boolean isIEntityInBox(Rectangle entity, Rectangle rec) {
		return Rectangle.isInBox(rec, entity);
	}

	private boolean isPlayerOnExit() {
		return isIEntityInBox(player.getPosition(), exit);
	}

	private Rectangle tileToRectangle(Tile tile) {
		int endX= tile.getX() + tile_width * 2;
		int endY= tile.getY() + tile_height * 2;
		return new Rectangle(tile.getX(), endX, tile.getY(), endY);

	}

	public void accept(IAfficheur visiteur){
		visiteur.visit(this);
	}

	protected void checkRunning(){
		if(isPlayerOnExit()){
			running= false;
		}
	}
}
