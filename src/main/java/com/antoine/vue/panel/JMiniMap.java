package com.antoine.vue.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import com.antoine.contracts.Presentateur;
import com.antoine.contracts.LevelListener;

public class JMiniMap extends JPanel implements LevelListener {
	
	public static final int ECHELLE= 10;
	
	private Presentateur presentateur;

	public JMiniMap(Presentateur presentateur) {
		this.presentateur= presentateur;
		this.setListener();
	}

	public int getHeight() {return presentateur.getMapHeight() / ECHELLE;}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBackGround(g);
		drawLevel(g);
		drawPlayer(g);
		drawScreen(g);
	}
	
	@Override
	public Dimension getPreferredSize() {
		Dimension d= presentateur.getDimension();
		int mapWidth= d.width / ECHELLE;
		int mapHeight= d.height / ECHELLE;
		return new Dimension(mapWidth, mapHeight);
	}
	
	private void drawScreen(Graphics g) {
		Color old= g.getColor();
		int x, y, width, height;
		g.setColor(Color.YELLOW);
		x= presentateur.getScreenX() / ECHELLE;
		y= presentateur.getScreenY() / ECHELLE;
		width= presentateur.getScreenWidth() / ECHELLE;
		height= presentateur.getScreenHeight() / ECHELLE;
		
		g.drawRect(x, y, width, height);
		g.setColor(old);
	}

	private void drawPlayer(Graphics g) {
		Color old= g.getColor();
		int x, y;
		x= presentateur.getPlayerX() / ECHELLE;
		y= presentateur.getPlayerY() / ECHELLE;
		g.setColor(Color.RED);
		g.fillRect(x, y, 5, 5);
		g.setColor(old);
	}

	private void drawLevel(Graphics g) {
		presentateur.drawMiniMap(g, ECHELLE);
	}

	private void drawBackGround(Graphics g) {
		Color old= g.getColor();
		int mapWidth, mapHeight;
		mapWidth= presentateur.getMapWidth() / ECHELLE;
		mapHeight= presentateur.getMapHeight() / ECHELLE;
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, mapWidth, mapHeight);
		g.setColor(old);
	}

	public void update() {
		this.repaint();
	}
	
	public void setListener() {
		this.presentateur.AddListener(this);
	}

}
