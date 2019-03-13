package com.antoine.vue.frame;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import com.antoine.contracts.IPanel;
import com.antoine.contracts.Presentateur;
import com.antoine.manager.LevelManager;
import com.antoine.vue.panel.ButtonPanel;
import com.antoine.vue.panel.JCardPane;
import com.antoine.vue.panel.SpecialPanel;

public class Frame extends JFrame {

	private SpecialPanel panel;
	
	public Frame() {

		Presentateur presentateur= new LevelManager();
		ButtonPanel buttons= new ButtonPanel(presentateur);
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
