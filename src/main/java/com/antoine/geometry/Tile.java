package com.antoine.geometry;

public class Tile {
	
	private static final int SOLID= 6;
	private static final int EXIT= -1;

	private int tile_num;
	private int x;
	private int y;
	
	public Tile(int tile_num, int x, int y)
	{
		this.tile_num= tile_num;
		this.x= x;
		this.y= y;
	}
	
	public int getTile_num() {return tile_num;}
	public int getX() {return x;}
	public int getY() {return y;}
	public boolean isSolid() {return tile_num >= SOLID;}
	public boolean isExit() {return tile_num == EXIT;}

	public static int getSolidNum(){return SOLID;}
}
