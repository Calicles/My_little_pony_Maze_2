package com.antoine.entity;

import com.antoine.contracts.IEnnemi;
import com.antoine.contracts.IMap;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;


/**
 * <b>Classe qui représente un ennemi</b>
 * <p>possède une Intelligence Artificielle
 * pour choix de déplacement</p>
 *
 * @author antoine
 */
public class Boss extends AbstractCharacter implements IEnnemi {


	public Boss() {
		super();
	}


	public Coordinates memorizeMoves() {
		Coordinates vectors= deplacement.memorizMoves();
		this.changeSprite(vectors);
		position.translate(vectors);
		return vectors;
	}



	@Override
	public void movesLeft() {

	}

	@Override
	public void movesRight() {

	}

	@Override
	public void movesUp() {

	}

	@Override
	public void movesDown() {

	}

	@Override
	public Coordinates memorizeMoves(IMap map) {
		Coordinates vectors= deplacement.memorizMoves();
		this.changeSprite(vectors);
		position.translate(vectors);
		return vectors;
	}

	@Override
	public void movesReleased() {

	}

	@Override
	public void translate(Coordinates verector) {

	}

	@Override
	public void think(Rectangle playerPosition) {
		this.deplacement.think(position, playerPosition);
	}

	@Override
	public void memorizeMozes() {
		Coordinates vectors= deplacement.memorizMoves();
		this.changeSprite(vectors);
		position.translate(vectors);
	}
}
