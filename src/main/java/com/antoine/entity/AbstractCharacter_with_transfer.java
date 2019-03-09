package com.antoine.entity;

import model.Coordinates;
import model.Rectangle;

public abstract class AbstractCharacter_with_transfer extends AbstractCharacter {
	

	public AbstractCharacter_with_transfer(String setUrl, String coorDepart) {
		super(setUrl, coorDepart);
	}
	
	public AbstractCharacter_with_transfer() {
		super();
	}

	public Coordinates memorizePlayerMoves(Rectangle player2Position, AbstractMap map) {return null;}
	public void memorizeBossMoves() {}	
	public void think(Coordinates player1, Coordinates player2) {}

	protected void changeSprite(Coordinates vectors) {
		if (vectors.getX() != 0 || vectors.getY() != 0) {
			if (vectors.getX() != 0) {
				if (vectors.getX() < 0) {
					direction = 1;
				} else {
					direction = 2;
				}
			} else {
				if (vectors.getY() < 0) {
					direction = 3;
				} else {
					direction = 0;
				}
			}
			updateIndex();
			currentImage = animation.get(direction)[animIndex];

		}
	}

	
	public void updateIndex() {
		tempo++;
		if(tempo % 4 == 0) {
			animIndex++;
			if(tempo >= 100)
				tempo= 0;
		}
		if(animIndex >= animation.get(0).length)
			animIndex= 0;
	}

	public void animationStoped() {
		animIndex= 0;
		image= animation.get(direction)[1];
	}
}
