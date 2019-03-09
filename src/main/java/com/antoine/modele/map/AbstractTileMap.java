package com.antoine.modele.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;
import com.antoine.services.Map_reader;

public abstract class AbstractTileMap {
	
	protected HashMap<Integer, BufferedImage> tileSet;
	protected Tile[][] map;
	
	protected int tile_width, tile_height;
	
	public AbstractTileMap(HashMap<Integer, BufferedImage> tileSet, int[][] map)
	{
		this.tileSet= tileSet;
		this.tile_width= tileSet.get(0).getWidth();
		this.tile_height= tileSet.get(0).getHeight();
		initMap(map);
	}

	public AbstractTileMap(){

	}

	public void setTileSet(String fileTileSetUrl){
		tileSet= Map_reader.readTileSet(fileTileSetUrl);
	}

	public void setMap(String fileMapUrl){
		int[][] buffer;
		buffer = Map_reader.readMap(fileMapUrl);
		initMap(buffer);
	}
	
	public int getTile_width() {return tile_width;}
	public int getTile_height() {return tile_height;}
	public Tile[][] getMap(){return map;}
	public HashMap<Integer, BufferedImage> getTileSet() {return tileSet;}
	
	public int[] getDimension() {
		int[] tab= new int[2];
		tab[0]= tileSet.get(0).getWidth() * map[0].length;
		tab[1]= tileSet.get(0).getHeight() * map.length;
		return tab;
	}

	public void drawMap(Graphics g)
	{
		Tile tile= null;
		for(int i=0;i<map.length;i++)
		{
			for(int j=0;j<map[0].length;j++)
			{
				tile= map[i][j];
				g.drawImage(tileSet.get(tile.getTile_num()),
						tile.getX(), tile.getY(), null);
			}
		}
	}
	
	private void initMap(int[][] map)
	{
		this.map= new Tile[map.length][map[0].length];
		int tile_num= 0;
		for(int i=0;i<map.length;i++)
		{
			for(int j=0;j<map[0].length;j++)
			{
				tile_num= map[i][j];
				this.map[i][j]= new Tile(tile_num, j * tile_width, 
						i * tile_height);
			}
		}
		
	}

	public Tile isSolidTileOnRoad(Rectangle board) {
		for(int i= board.getBeginY(); i<=board.getEndY();i++) {
			for(int j= board.getBeginX(); j<= board.getEndX();j++) {
				if(map[i][j].isSolid()) {
					return map[i][j];
				}
			}
		}
		return null;
	}
	
	public Tile findExit() {
		for(int i= 0; i< map.length;i++) {
			for(int j= 0; j< map[0].length;j++) {
				if(map[i][j].isExit()) {
					return map[i][j];
				}
			}
		}
		return null;
	}
	
	public Tile findTile(int row, int col) {
		return map[row][col];
	}

	

}
