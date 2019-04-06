package com.antoine.vue.frame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.border.Border;

import com.antoine.contracts.IPanel;
import com.antoine.contracts.Presentateur;
import com.antoine.manager.niveau.LevelManager;
import com.antoine.vue.listeners.SliderChangeMusicListener;
import com.antoine.vue.listeners.SliderChangeSoundListener;
import com.antoine.vue.panel.*;

public class Frame extends JFrame {

	private SpecialPanel panel;
	
	public Frame() {
		init();
	}

	public void setPrincipalPanel(IPanel principalPanel){

	}

	public void setButtonPanel(IPanel buttonPanel){

	}

	public void setMiniMap(IPanel miniMap){

		Presentateur presentateur= new LevelManager();
		ButtonPanel buttons= new ButtonPanel(presentateur);
		//JPanel downPane= new JCardPane();

		panel= new SpecialPanel(presentateur);
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);
		this.add(buttons, BorderLayout.SOUTH);
		this.addKeyListener(new InternImageListener());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setTitle("My Little Pony");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	private void init(){

		Border lowered, raised;
		lowered = BorderFactory.createLoweredBevelBorder();
		raised = BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder());

		Presentateur presentateur= new LevelManager();

		ButtonPanel buttons= new ButtonPanel(presentateur);

		JPanel panelBas = new JPanel(new BorderLayout());

		JPanel innerGauche = new JPanel(new BorderLayout());

		JPanel innerDroit = new JPanel(new BorderLayout());

		JLabel music = new JLabel("music");
		music.setHorizontalAlignment(JLabel.CENTER);
		music.setVerticalAlignment(JLabel.CENTER);
		music.setBackground(Color.PINK);
		JLabel bruitage = new JLabel("bruitage");
		bruitage.setHorizontalAlignment(JLabel.CENTER);
		bruitage.setVerticalAlignment(JLabel.CENTER);
		bruitage.setBackground(Color.PINK);
		innerGauche.add(music, BorderLayout.SOUTH);
		JSliderPanel musicSlider = new JSliderPanel(presentateur.getJukebox(), "/ressources/images/slide/lunaSlide.png",
				0, 10, true);
		musicSlider.addChangeListener(new SliderChangeMusicListener(presentateur.getJukebox()));

		JSliderPanel soundSlider = new JSliderPanel(presentateur.getJukebox(), "/ressources/images/slide/lunaSlide.png",
				0, 10, true);
		soundSlider.addChangeListener(new SliderChangeSoundListener(presentateur.getJukebox()));
		innerGauche.add(musicSlider, BorderLayout.CENTER);


		innerGauche.setBackground(Color.PINK);

		innerDroit.add(soundSlider, BorderLayout.CENTER);
		innerDroit.add(bruitage, BorderLayout.SOUTH);
		innerDroit.setBackground(Color.PINK);


		panelBas.add(buttons, BorderLayout.CENTER);
		panelBas.setBorder(raised);

		panel= new SpecialPanel(presentateur);
		panel.setBorder(lowered);
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);
		this.add(panelBas, BorderLayout.SOUTH);

		this.add(innerGauche, BorderLayout.WEST);
		this.add(innerDroit, BorderLayout.EAST);

		this.addKeyListener(new InternImageListener());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setTitle("My Little Pony");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private class InternImageListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent keyEvent) {

		}

		@Override
		public void keyPressed(KeyEvent e) {

			this.moves(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if ((e.getKeyCode() == KeyEvent.VK_RIGHT) || (e.getKeyCode() == KeyEvent.VK_LEFT)||
					(e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_DOWN))
				panel.playerMovesReleased();
		}

		private void moves(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				panel.playerMovesRight();
			else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				panel.playerMovesLeft();
			} else if (e.getKeyCode() == KeyEvent.VK_UP)
				panel.playerMovesUp();
			else if (e.getKeyCode() == KeyEvent.VK_DOWN)
				panel.playerMovesDown();

		}
		
	}
	
}
