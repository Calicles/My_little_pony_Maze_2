package com.antoine.entity;

import com.antoine.contracts.IEntity;
import com.antoine.contracts.IMap;
import com.antoine.geometry.Coordinates;

public class Player extends AbstractCharacter implements IEntity {
	
	@Override
	public void movesLeft() {deplacement.movesLeft();}

	@Override
	public void movesRight() {deplacement.movesRight();}

	@Override
	public void movesUp() {deplacement.movesUp();}

	@Override
	public void movesDown() {deplacement.movesDown();}

	@Override
	public Coordinates memorizeMoves(IMap map) {
		Coordinates vector= deplacement.memorizeMoves(this.position, map);
		
		//Change les coordonnes du perso
		position.setCoordinates(position.getBeginX() + vector.getX(), position.getBeginY() + vector.getY());
		
		//change la sprite
		changeSprite(vector);

		return vector;
	}

	public void animationStoped() {
		animIndex= 0;
		image= animation.get(direction)[1];
	}

	public void movesReleased() {
		animationStoped();
		deplacement.released();
	}

	@Override
	public void translate(Coordinates vector) {
		position.translate(vector.getX(), vector.getY());
	}


}
