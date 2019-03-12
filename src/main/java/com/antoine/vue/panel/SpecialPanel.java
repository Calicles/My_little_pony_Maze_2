package com.antoine.vue.panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JPanel;

import com.antoine.contracts.IPanel;
import com.antoine.contracts.Presentateur;
import com.antoine.contracts.LevelListener;

public class SpecialPanel extends JPanel implements LevelListener, IPanel {
	
	private Presentateur presentateur;
	
	public SpecialPanel(Presentateur model) {
		this.presentateur= model;
		this.presentateur.AddListener(this);
	}

	public SpecialPanel(){

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
	
	public void playerMovesLeft(){
		presentateur.playerMovesLeft();
	}

	public void playerMovesRight(){
		presentateur.playerMovesRight();
	}

	public void playerMovesUp(){
		presentateur.playerMovesUp();
	}

	public void playerMovesDown(){
		presentateur.playerMovesDown();
	}

	public void playerMovesReleased(){
		presentateur.playerMovesReleased();
	}
	
}
