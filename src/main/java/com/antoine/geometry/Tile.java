package com.antoine.geometry;


/**
 * <b>Représente les tuiles de décors</b>
 *
 * @author antoine
 */
public class Tile {

	/**
	 * si tuile traversable son chiffre sera enssous
	 */
	private static final int SOLID= 6;

	/**
	 * si la tuile est la sortie
	 */
	private static final int EXIT= -1;

	private static int width, height;

	private int tile_num;
	private int x;
	private int y;


	public Tile(int tile_num, int x, int y)
	{
		this.tile_num= tile_num;
		this.x= x;
		this.y= y;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}


	public int getTile_num() {return tile_num;}
	public int getX() {return x;}
	public int getY() {return y;}

	public static void setWidth(int width){
		Tile.width = width;
	}

	public static void setHeight(int height){
		Tile.height = height;
	}


	/**
	 * <p>Test la solidité</p>
	 * @return true si tuile solide, false sinon
	 */
	public boolean isSolid() {return tile_num >= SOLID;}


	/**
	 * <p>Test si la tuile est une sortie</p>
	 * @return true si son numéro correspond à EXIT, false sinon
	 */
	public boolean isExit() {return tile_num == EXIT;}

	public static int getSolidNum(){return SOLID;}

    public boolean contains(Coordinates start) {
		Rectangle rec = new Rectangle(x, x + width, y, y + height);
		return Rectangle.isInBox(rec, new Rectangle(start, 0, 0));
    }

    public Rectangle toRectangle(){
		return new Rectangle(x, width, y, height);
	}

	public Coordinates toCoordinates(){
		return new Coordinates(x, y);
	}
}
