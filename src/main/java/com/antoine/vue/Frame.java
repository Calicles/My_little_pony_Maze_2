package com.antoine.vue;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import com.antoine.contracts.Presentateur;
import com.antoine.manager.LevelManager;
import org.xml.sax.SAXException;

public class Frame extends JFrame {

	private SpecialPanel panel;
	
	public Frame() throws IOException, NoSuchMethodException, IllegalAccessException,
			SAXException, InstantiationException, ParserConfigurationException,
			InvocationTargetException, ClassNotFoundException {

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
	
	private class InternImageListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			this.moves(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				panel.playerMovesReleased();
			else if (e.getKeyCode() == KeyEvent.VK_LEFT)
				panel.playerMovesReleased();
			else if (e.getKeyCode() == KeyEvent.VK_UP)
				panel.playerMovesReleased();
			else if (e.getKeyCode() == KeyEvent.VK_DOWN)
				panel.playerMovesReleased();
		}

		@Override
		public void keyTyped(KeyEvent e) {
			this.moves(e);
		}

		private void moves(KeyEvent e) {

			try{

				if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					panel.playerMoves(4, 0);
				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					panel.playerMoves(-4, 0);
				} else if (e.getKeyCode() == KeyEvent.VK_UP)
					panel.playerMoves(0, -4);
				else if (e.getKeyCode() == KeyEvent.VK_DOWN)
					panel.playerMoves(0, 4);
			}catch(IOException ioe){
				ioe.printStackTrace();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

		}
		
	}
	
}
