package com.antoine.vue.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import com.antoine.afficheur.AfficheurMiniMap;
import com.antoine.contracts.IPanel;
import com.antoine.contracts.IAfficheur;
import com.antoine.contracts.Presentateur;
import com.antoine.contracts.LevelListener;

public class JMiniMap extends JPanel implements LevelListener, IPanel {
	
	private Presentateur presentateur;
	private IAfficheur afficheurMiniMap;

	public JMiniMap(Presentateur presentateur) {
		this.presentateur= presentateur;
		this.setListener();
		this.afficheurMiniMap= new AfficheurMiniMap();
	}

	public JMiniMap(){

	}

	public int getHeight() {return presentateur.getMapHeight() / AfficheurMiniMap.SCALE;}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawLevel(g);
	}
	
	private void drawLevel(Graphics g) {
		afficheurMiniMap.setGraphics(g);
		presentateur.accept(afficheurMiniMap);
		afficheurMiniMap.freeGraphics();
	}

	public void update() {
		this.repaint();
	}
	
	private void setListener() {
		this.presentateur.AddListener(this);
	}

}
