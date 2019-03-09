package com.antoine.geometry;


/**
 *
 * @author antoine
 *
 */
public class DoubleBoxes {

	Rectangle screen;
	Rectangle scrollBox;

	public DoubleBoxes(Rectangle screen, Rectangle scrollBox) {
		this.screen= screen;
		this.scrollBox= scrollBox;
	}

	public int getScreenBeginX() {return screen.getBeginX();}
	public int getScreenEndX() {return screen.getEndX();}
	public int getScreenBeginY() {return screen.getBeginY();}
	public int getScreenEndY() {return screen.getEndY();}
	public int getScreenWidth(){return screen.getWidth();}
	public int getScreenHeight(){return screen.getHeight();}
	public Rectangle getScrollBox() {return scrollBox;}// to remove

	public boolean isPlayerOnTopScroll(int yPosition) {
		return yPosition <= scrollBox.getBeginY();
	}
	public boolean isPlayerOnBottomScroll(int yPosition) {
		return yPosition >= scrollBox.getEndY();
	}
	public boolean isPlayerOnLeftScroll(int xPosition) {
		return xPosition <= scrollBox.getBeginX();
	}
	public boolean isPlayerOnRightScroll(int xPosition) {
		return xPosition >= scrollBox.getEndX();
	}

	public void scroll(int xVector, int yVector) {
		screen.translate(xVector, yVector);
		scrollBox.translate(xVector, yVector);
	}

}
