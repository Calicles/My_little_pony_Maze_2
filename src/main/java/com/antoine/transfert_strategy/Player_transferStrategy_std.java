package com.antoine.transfert_strategy;

import com.antoine.contracts.IMap;
import com.antoine.contracts.ITransfert_strategy;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;

public abstract class Player_transferStrategy_std extends AbstractTransfer implements ITransfert_strategy {
	

	public void released() {xDirection= 0; yDirection= 0;}
	public void movesLeft() {xDirection= -vector.getX();}
	public void movesRight() {xDirection= vector.getX();}
	public void movesUp() {yDirection= -vector.getY();}
	public void movesDown() {yDirection= vector.getY();}

	@Override
	public Coordinates memorizeMoves(Rectangle position, IMap map) {
		
		adaptVectors(position, map);
		
		return new Coordinates(xDirection, yDirection);
	}
	
}
