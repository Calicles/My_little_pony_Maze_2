package com.antoine.vue.panel;

import java.awt.*;
import javax.swing.*;

import com.antoine.contracts.IPanel;
import com.antoine.contracts.Presentateur;
import com.antoine.contracts.LevelListener;

public class ButtonPanel extends JPanel implements LevelListener, IPanel {

	private Presentateur presentateur;
	private JButton appleButton, rarityButton, rainbowButton;
	private JMiniMap map;
	
	private boolean mapAdded;
	
	public ButtonPanel(Presentateur presentateur) {
		this.presentateur= presentateur;
		map= new JMiniMap(presentateur);
		mapAdded= false;
		init();
	}

	public ButtonPanel(){

	}

	public void setPresentateur(Presentateur presentateur){
		this.presentateur= presentateur;
		init();
		map= new JMiniMap(presentateur);
		mapAdded= false;
	}

	public void setAppleButton(String appleButtonImagePath){
		appleButton= new JButton(new ImageIcon(appleButtonImagePath));
	}

	public void setRarityButton(String rarityButtonImagePath){
		rarityButton= new JButton(new ImageIcon(rarityButtonImagePath));
	}

	public void setRainbowButton(String rainbowButtonImagePath){
		rainbowButton= new JButton(new ImageIcon(rainbowButtonImagePath));
	}

	@Override 
	public Dimension getPreferredSize() {
		int width=(int) presentateur.getDimension().getWidth();
		int height=(int) presentateur.getDimension().getHeight() * 2 /map.ECHELLE;
		if(!mapAdded) {
		int d= map.getHeight();
		return new Dimension(width, height);
		}else
			return new Dimension(width, height/2+5);
	}
	@Override
	public void update() {
		if (!presentateur.isLevelsNull()) {
			appleButton.setEnabled(presentateur.isAppleSelectedAndRunning());
			rarityButton.setEnabled(presentateur.isRaritySelectedAndRunning());
			rainbowButton.setEnabled(presentateur.isRainbowSelectedAndRunning());
		} else if (this.isLevelsNull() && !mapAdded) {
			this.hideButton();
			this.map.setVisible(true);
			this.mapAdded = true;
		}
		this.repaint();
	}

	private void hideButton() {
		this.remove(appleButton);
		this.remove(rarityButton);
		this.remove(rainbowButton);
	}

	private void init() {
		appleButton= new JButton(new ImageIcon(("./ressources/images/boutons/apple.png")));
		rarityButton= new JButton(new ImageIcon("./ressources/images/boutons/rarity.png"));
		rainbowButton= new JButton(new ImageIcon("./ressources/images/boutons/rainbow.png"));
		appleButton.setPreferredSize(new Dimension(75, 75));
		rarityButton.setPreferredSize(new Dimension(75, 75));
		rainbowButton.setPreferredSize(new Dimension(75, 75));
		appleButton.setFocusable(false);
		rarityButton.setFocusable(false);
		rainbowButton.setFocusable(false);
		this.setLayout(new FlowLayout());
		this.add(appleButton);
		this.add(rarityButton);
		this.add(rainbowButton);
		this.add(map);
		this.map.setVisible(false);
		appleButton.addActionListener(e->presentateur.switchLeveApple());
		rarityButton.addActionListener(e->presentateur.switchLevelRarity());
		rainbowButton.addActionListener(e->presentateur.switchLevelRainbow());
		presentateur.AddListener(this);
		this.setBackground(Color.PINK);
		update();
	}
	
	private boolean isLevelsNull() {
		return presentateur.isLevelsNull() && presentateur.isLevelFlutterNull()
				&& !presentateur.isLevelPinkyNull();
	}


}
