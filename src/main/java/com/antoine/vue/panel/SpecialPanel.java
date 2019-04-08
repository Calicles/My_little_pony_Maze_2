package com.antoine.vue.panel;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.antoine.afficheur.AfficheurLevel;
import com.antoine.contracts.IAfficheur;
import com.antoine.contracts.Presentateur;
import com.antoine.contracts.LevelListener;
import com.antoine.events.LevelChangeEvent;

public class SpecialPanel extends JPanel implements LevelListener {
	
	private Presentateur presentateur;
	private IAfficheur afficheur;
	
	public SpecialPanel(Presentateur model) {
		this.presentateur= model;
		this.presentateur.AddListener(this);
		this.afficheur= new AfficheurLevel();
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
		afficheur.setGraphics(g);
		presentateur.accept(afficheur);
		afficheur.freeGraphics();
	}

	@Override
	public void update(LevelChangeEvent lve) {
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
