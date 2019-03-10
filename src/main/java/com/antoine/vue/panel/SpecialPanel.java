package com.antoine.vue.panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JPanel;

import com.antoine.contracts.Presentateur;
import com.antoine.contracts.LevelListener;

public class SpecialPanel extends JPanel implements LevelListener{
	
	private Presentateur presentateur;
	
	public SpecialPanel(Presentateur model) {
		this.presentateur= model;
		this.presentateur.AddListener(this);
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return presentateur.getDimension();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			presentateur.draw(g);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		this.repaint();
	}
	
	public void playerMoves(int xVector, int yVector) throws IOException, ClassNotFoundException,
			NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

		presentateur.playerMoves(xVector, yVector);
	}

	public void playerMovesReleased(){
		presentateur.playerMovesReleased();
	}
	
}
