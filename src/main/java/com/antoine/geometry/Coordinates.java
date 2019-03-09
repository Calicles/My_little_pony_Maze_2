package com.antoine.geometry;

public class Coordinates {
	
	protected int x, y;
	
	public Coordinates(int x, int y) {
		this.x= x;
		this.y= y;
	}

	public int getX() {return x;}
	public int getY() {return y;}
	
	public void setCoordinates(Coordinates newPosition) {
		this.x= newPosition.getX();
		this.y= newPosition.getY();
	}
	
	public void translate(Coordinates vectors) {
		this.x += vectors.getX();
		this.y += vectors.getY();
	}
	
	public void setCoordinates(int x, int y) {this.x= x; this.y= y;}

	public boolean isZero() {
		return x != 0 && y != 0;
	}

}
