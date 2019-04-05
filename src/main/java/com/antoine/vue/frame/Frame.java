package com.antoine.vue.frame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import com.antoine.contracts.IPanel;
import com.antoine.contracts.Presentateur;
import com.antoine.manager.niveau.LevelManager;
import com.antoine.vue.panel.*;

public class Frame extends JFrame {

	private SpecialPanel panel;
	
	public Frame() {

		Presentateur presentateur= new LevelManager();
		ButtonPanel buttons= new ButtonPanel(presentateur);
		JPanel panelBas = new JPanel(new BorderLayout());
		JPanel innerBas = new JPanel(new BorderLayout());
		JPanel innerBas2 = new JPanel(new BorderLayout());
		JPanel innerBas3 = new JPanel(new BorderLayout());
		JLabel music = new JLabel("music");
		music.setBackground(Color.PINK);
		JLabel bruitage = new JLabel("bruitage");
		bruitage.setBackground(Color.PINK);
		innerBas.add(music, BorderLayout.NORTH);
		innerBas.add(bruitage, BorderLayout.CENTER);

		innerBas.setBackground(Color.PINK);

		innerBas2.add(new JSliderMusic(presentateur), BorderLayout.NORTH);
		innerBas2.add(new JSliderSound(presentateur), BorderLayout.SOUTH);

		innerBas3.add(innerBas, BorderLayout.WEST);
		innerBas3.add(innerBas2, BorderLayout.CENTER);
		innerBas3.setBackground(Color.PINK);

		panelBas.add(buttons, BorderLayout.CENTER);
		panelBas.add(innerBas3, BorderLayout.SOUTH);
		panel= new SpecialPanel(presentateur);
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);
		this.add(panelBas, BorderLayout.SOUTH);
		this.addKeyListener(new InternImageListener());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setTitle("My Little Pony");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
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
