package com.antoine.geometry;


import java.awt.*;

/**
 * 
 * @author antoine
 *
 */
public class Rectangle {


	private int beginX, beginY;
	private int endX;
	private int endY;


	public Rectangle(int endX, int endY) {
		beginX= 0; beginY= 0;
		this.endX= endX;
		this.endY= endY;
	}

	public Rectangle(int beginX, int endX, int beginY, int endY) {
		this.beginX= beginX;
		this.beginY= beginY;
		this.endX= endX;
		this.endY= endY;
	}

	public Rectangle(Coordinates coordinates, int endX, int endY) {
		this.beginX= coordinates.getX();
		this.beginY= coordinates.getY();
		this.endX= endX;
		this.endY= endY;
	}

	public int getEndX() {return endX;}
	public int getEndY() {return endY;}
	public int getBeginX() {return beginX;}
	public int getBeginY() {return beginY;}
	public int getWidth() {return endX - beginX;}
	public int getHeight() {return endY - beginY;}
	public Dimension getDimension() {
		int width= endX - beginY;
		int height= endY - beginY;
		return new Dimension(width, height);
	}

	public void translate(int x, int y) {
		beginX += x; endX += x;
		beginY += y; endY += y;
	}

	public boolean isOnLeft(int toTest) {return toTest < beginX;}
	public boolean isOnRight(int toTest) {return toTest > endX;}
	public boolean isOnTop(int toTest) {return toTest < beginY;}
	public boolean isOnBottom(int toTest) {return toTest >= endY;}

	public static boolean isOver(int x, int position) {return x>position;}
	public static boolean isBefore(int x, int position) {return x<position;}
	public static  boolean isInBox(Rectangle box, Rectangle position) {
		return box.getBeginX() <= position.getBeginX() &&
				box.getEndX() >= position.getEndX() &&
				box.getBeginY() <= position.getBeginY() &&
				box.getEndY() >= position.getEndY();
	}

    public void setCoordinates(int x, int y) {
		this.beginX= x;
		this.beginY= y;
    }

    public void setCoordinates(Coordinates position) {
		this.beginX= position.getX();
		this.beginY= position.getY();

    }

    public void translate(Coordinates vectors) {
		beginX += vectors.getX(); endX += vectors.getX();
		beginY += vectors.getY(); endY += vectors.getY();

    }
}
