package com.antoine.vue.frame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.border.Border;

import com.antoine.contracts.Presentateur;
import com.antoine.manager.niveau.LevelManager;
import com.antoine.vue.ProgressBar;
import com.antoine.vue.listeners.SliderChangeMusicListener;
import com.antoine.vue.listeners.SliderChangeSoundListener;
import com.antoine.vue.panel.*;

public class Frame extends JFrame {

	private SpecialPanel panel;
	
	public Frame() {
		init();
	}

	private void init(){

		Border lowered, raised, bevel;
		lowered = BorderFactory.createLoweredBevelBorder();
		raised = BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder());
		bevel = BorderFactory.createRaisedBevelBorder();

		Presentateur presentateur= new LevelManager();

		ButtonPanel buttons= new ButtonPanel(presentateur);

		JMiniMap miniMapPane = new JMiniMap(presentateur);
		miniMapPane.setBackground(Color.PINK);

		JCardPane panelBas = new JCardPane("boutons", buttons, "miniMap", miniMapPane);
		presentateur.AddListener(panelBas);

		ProgressBar barre = new ProgressBar(0, 6);
		barre.setBorder(bevel);

		presentateur.AddListener(barre);



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

		JSliderPanel musicSlider = new JSliderPanel("/ressources/images/slide/celestiaSlide.png",
				0, 50, (int) (presentateur.getJukebox().getMusicVolume() *100), true);
		musicSlider.addChangeListener(new SliderChangeMusicListener(presentateur.getJukebox()));


		JSliderPanel soundSlider = new JSliderPanel("/ressources/images/slide/lunaSlide.png",
				0, 50, (int) (presentateur.getJukebox().getSoundVolume() *100), true);
		soundSlider.addChangeListener(new SliderChangeSoundListener(presentateur.getJukebox()));


		innerGauche.add(musicSlider, BorderLayout.CENTER);


		innerGauche.setBackground(Color.PINK);

		innerDroit.add(soundSlider, BorderLayout.CENTER);
		innerDroit.add(bruitage, BorderLayout.SOUTH);
		innerDroit.setBackground(Color.PINK);

		panelBas.setBorder(raised);

		panel= new SpecialPanel(presentateur);
		panel.setBorder(lowered);
		this.setLayout(new BorderLayout());
		this.add(barre, BorderLayout.NORTH);
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
