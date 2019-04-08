package com.antoine.vue.panel;

import java.awt.*;
import javax.swing.*;

import com.antoine.contracts.Presentateur;
import com.antoine.contracts.LevelListener;
import com.antoine.events.LevelChangeEvent;

public class ButtonPanel extends JPanel implements LevelListener {

	private JButton appleButton, rarityButton, rainbowButton;


	public ButtonPanel(Presentateur presentateur) {
		init(presentateur);
	}

	public ButtonPanel(){

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
	public Dimension getPreferredSize(){
		return new Dimension(75 * 3, 100);
	}
	@Override
	public void update(LevelChangeEvent lve) {
		appleButton.setEnabled( !lve.valueOf(LevelChangeEvent.LEVEL1_SELECTED) && lve.valueOf(LevelChangeEvent.LEVEL1_RUNNING));
		rarityButton.setEnabled( !lve.valueOf(LevelChangeEvent.LEVEL2_SELECTED) && lve.valueOf(LevelChangeEvent.LEVEL2_RUNNING));
		rainbowButton.setEnabled( !lve.valueOf(LevelChangeEvent.LEVEL3_SELECTED) && lve.valueOf(LevelChangeEvent.LEVEL3_RUNNING));
		this.repaint();
	}

	private void init(Presentateur presentateur) {
		appleButton= new JButton(new ImageIcon(getClass().getResource("/ressources/images/boutons/apple.png")));
		rarityButton= new JButton(new ImageIcon(getClass().getResource("/ressources/images/boutons/rarity.png")));
		rainbowButton= new JButton(new ImageIcon(getClass().getResource("/ressources/images/boutons/rainbow.png")));
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
		appleButton.addActionListener(e->presentateur.switchLeveApple());
		rarityButton.addActionListener(e->presentateur.switchLevelRarity());
		rainbowButton.addActionListener(e->presentateur.switchLevelRainbow());
		presentateur.AddListener(this);
		this.setBackground(Color.PINK);

	}
	
}
