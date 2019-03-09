package com.antoine.manager.minotaure;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import model.Coordinates;
import model.NetEvent;
import model.TransferEvent;
import network.NetWork;
import services.Assembler;
import services.LevelFactory;
import type.AbstractLevel;
import type.AbstractMap;
import type.Controller;
import type.LevelListener;
import type.NetworkListener;

public class LevelManager implements Controller, NetworkListener {
	
	private AbstractLevel level;
	private NetWork server;
	
	public LevelManager() {
		level= null;
		server= null;
	}
	
	
	public Coordinates getBossCoordinates() {
		// TODO Auto-generated method stub
		return null;
	}

	public Coordinates getPlayerCoordinates() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void startGame() {
		level.start();
	}
	
	@Override
	public void createLevel(String levelType, Dimension screenSize) throws SAXException,
	IOException, ParserConfigurationException, ClassNotFoundException, NoSuchMethodException,
	SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
	InvocationTargetException {
		Assembler assembler= new Assembler();
		this.level= (AbstractLevel) assembler.newInstance(levelType);
		this.server= new NetWork();
		server.initLine(levelType);
		server.addListener(this);
		this.addListener(server.getLine());
		server.run(level.getPlayerCoordinates(), level.getPlayerImages(), level.getPlayerImages(), level.getBossImages()); 
	}
	

	@Override
	public void drawLevel(Graphics g) {
		level.drawLevel(g);
	}

	@Override
	public void released() {level.released();}
	
	@Override
	public void playerMovesLeft() {level.playerMovesLeft();}

	@Override
	public void playerMovesRight() {level.playerMovesRight();}

	@Override
	public void playerMovesUp() {level.playerMovesUp();}
	
	@Override
	public void playerMovesDown() {level.playerMovesDown();}

	public void addListener(LevelListener listener) {
		level.addListener(listener);
	}
	
	@Override
	public void addNetWorkListener(NetworkListener listener) {
		server.addListener(listener);
	}


	@Override
	public void updateState(NetEvent ne) {
		if(ne.getLineState()) level.start();
		
	}


	@Override
	public void update(TransferEvent te) {
		level.update(te);
		
	}


	@Override
	public void updateEvent(TransferEvent te) {
		// TODO Auto-generated method stub
		
	}


}
