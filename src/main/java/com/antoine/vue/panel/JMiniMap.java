package com.antoine.vue.panel;

import java.awt.*;
import javax.swing.JPanel;

import com.antoine.afficheur.AfficheurMiniMap;
import com.antoine.contracts.IAfficheur;
import com.antoine.contracts.Presentateur;
import com.antoine.contracts.LevelListener;
import com.antoine.events.LevelChangeEvent;

public class JMiniMap extends JPanel implements LevelListener {
	
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
		int x, y;

		x = (this.getWidth() / 2);
		y = this.getY();

		g.translate(x, y);

		afficheurMiniMap.setGraphics(g);
		presentateur.accept(afficheurMiniMap);
		afficheurMiniMap.freeGraphics();

		g.translate(-x, -y);
	}

	@Override
	public void update(LevelChangeEvent lve) {
		this.repaint();
	}
	
	private void setListener() {
		this.presentateur.AddListener(this);
	}

}
