package com.antoine.modele.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.antoine.contracts.ILevel;
import com.antoine.contracts.IMap;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.DoubleBoxes;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;

public class Level3 extends AbstractLevel implements ILevel {
	
	protected DoubleBoxes boxes;


	public Level3(){
		super();
	}

	@Override
	public void setMap(IMap map){
		super.setMap(map);
		initBoxes();
	}

	protected void initBoxes() {
		Rectangle screen= new Rectangle(0, 20*tile_width, 20*tile_height,
				40* tile_height);
		Rectangle scrollBox= new Rectangle(5*tile_width, 15*tile_width,
				25*tile_height, 35*tile_height);
		this.boxes= new DoubleBoxes(screen, scrollBox);
	}
	
	@Override
	public void drawLevel(Graphics g) {
		if(running) {
			drawScreen(g);
			drawPlayer(g);
		} else
			try {
				g.drawImage(ImageIO.read(new File(endImageUrl)), 0, 0, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	private void drawScreen(Graphics g) {
		Tile tile= null;
		Tile[][] map= this.map.getMap();
		HashMap<Integer, BufferedImage> set= this.map.getTileSet();
		int row= boxes.getScreenBeginY() / tile_height;
		int col= boxes.getScreenBeginX() / tile_width;
		int rowMax= boxes.getScreenEndY() / tile_height;
		int colMax= boxes.getScreenEndX() / tile_width;
		int x= 0, y= 0;

		if(boxes.getScreenEndY() % 32 != 0) rowMax ++;
		for(int i= row; i<rowMax; i++) {
			for(int j= col; j<colMax; j++) {
				tile= map[i][j];
				y= tile.getY() - boxes.getScreenBeginY();
				x= tile.getX() - boxes.getScreenBeginX();
				g.drawImage(set.get(tile.getTile_num()), x, 
						y, null);
			}
		}
	}

	protected void drawPlayer(Graphics g) {
		int screenPosY= playerScreenPositionY();
		int screenPosX= playerScreenPositionX();
		g.drawImage(player.getImage(), screenPosX, screenPosY, null);
	}
	
	public void drawMiniMap(Graphics g, int ECHELLE) {
		Color old= g.getColor();
		Tile[][] map= this.map.getMap();
		int num= 0, x, y;
		int width= tile_width / ECHELLE;
		int height= tile_height / ECHELLE;
		g.setColor(Color.ORANGE);
		for(int i=0; i<map.length; i++) {
			for(int j=0;j<map[0].length;j++) {
				num= map[i][j].getTile_num();
				x= map[i][j].getX() / ECHELLE;
				y= map[i][j].getY() / ECHELLE;
				if(num > Tile.SOLID) {
					g.fillRect(x, y, width, height);
				}
			}
		}
		g.setColor(old);
	}
	
	protected int playerScreenPositionY() {
		int posY= player.getY() - boxes.getScreenBeginY();
		return posY;
	}
	protected int playerScreenPositionX() {
		int posX= player.getX() - boxes.getScreenBeginX();
		return posX;
	}
	
	 @Override
	 public boolean playerMovesUp() {
		Coordinates vector;

		player.movesUp();
		vector= player.memorizeMoves(map);

		 if(!screenOnTop() && 
				 boxes.isPlayerOnTopScroll(player.getY()+ vector.getY()))
			 boxes.scroll(0, vector.getY() );

		 return vector.isZero();
	 }
	 
	 @Override
	 public boolean playerMovesDown() {
		Coordinates vector;

		player.movesDown();
		vector= player.memorizeMoves(map);

		 if(!screenOnBottom() &&
				 boxes.isPlayerOnBottomScroll(player.getY()+
						 player.getHeight() + vector.getY()))
			 boxes.scroll(0, vector.getY());

		 return vector.isZero();
	 }

	@Override
	public void selected() {

	}

	@Override
	public void deselected() {

	}

	@Override
	public boolean isSelected() {
		return false;
	}

	@Override
	 public boolean playerMovesLeft() {
		Coordinates vector;

		 player.movesLeft();
		 vector= player.memorizeMoves(map);

		 if(!screenOnLeft() && 
				 boxes.isPlayerOnLeftScroll(player.getX() + vector.getX()))
			 boxes.scroll(vector.getX(), 0);

		 return vector.isZero();
	 }
	 
	 @Override
	 public boolean playerMovesRight() {
		Coordinates vector;

		player.movesRight();
		vector= player.memorizeMoves(map);

		 if(!screenOnRight() &&
				 boxes.isPlayerOnRightScroll(player.getX() + player.getWidth() + vector.getX()))
			 boxes.scroll(vector.getX(), 0);

		 return vector.isZero();
	 }
	 
	 private boolean screenOnRight() {
		 return boxes.getScreenEndX() >= mapSize.getEndX();
	 }
	 
	 private boolean screenOnLeft() {
		 return boxes.getScreenBeginX() <= 0;
	 }

	private boolean screenOnBottom() {
		return boxes.getScreenEndY() >= mapSize.getEndY();
	}

	private boolean screenOnTop() {
		return boxes.getScreenBeginY() <= 0;
	}

	public int getScreenX() {
		return boxes.getScreenBeginX();
	}

	public int getScreenY() {
		return boxes.getScreenBeginY();
	}

	public int getScreenWidth() {
		return boxes.getScreenWidth();
	}

	public int getScreenHeight() {
		return boxes.getScreenHeight();
	}

	public int getPlayerX() {
		return player.getX();
	}

	public int getPlayerY() {
		return player.getY();
	}

}
