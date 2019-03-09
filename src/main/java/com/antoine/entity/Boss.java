package com.antoine.entity;

import contracts.Charac_withTransfert;
import contracts.IA;
import type.AbstractCharacter_with_transfer;
import type.AbstractMap;

public class Boss extends AbstractCharacter_with_transfer implements Charac_withTransfert {
	
	private IA transfer_strategy;

	public Boss(String setUrl, String coorDepart) {
		super(setUrl, coorDepart);
	}
	
	public Boss() {
		super();
	}
	
	public void setTransfert(IA t) {
		this.transfer_strategy= t;
	}
	
	public void setUrlImg(String url) {
		super.setUrlImage(url);
	}
	
	public void setPosition(String coorDepart) {
		super.setPosition(coorDepart);
	}
	
	public void setAttributes(Rectangle player1, AbstractMap map) {
		transfer_strategy.setAttributes(this.position, player1, map);System.out.println("in BossSeter  position: "+this.position+"     player1:  "+player1+"    map:   "+map);
	}
	
	@Override
	public void memorizeBossMoves() {
		Coordinates vectors= transfer_strategy.memorizeBossMoves();	
		this.changeSprite(vectors);
		position.translate(vectors);
	}
	
	@Override
	public void think(Coordinates player1, Coordinates player2) {
		this.transfer_strategy.think(position, player1, player2);
	}

	@Override
	public void movesReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movesLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movesRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movesUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movesDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Coordinates memorizePlayerMoves(Coordinates coor, AbstractMap map) {
		// TODO Auto-generated method stub
		return null;
	}
}
