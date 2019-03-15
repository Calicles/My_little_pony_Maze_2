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
		return null;
	}

	@Override
	public void movesReleased() {

	}

	@Override
	public void translate(Coordinates verector) {

	}

	@Override
	public void think() {
		this.deplacement.think();
	}

	@Override
	public void memorizeMoves() {
		Coordinates vectors= deplacement.memorizeMoves();
		this.changeSprite(vectors);
		position.translate(vectors);
	}

	@Override
	public void setAttributes(Rectangle ownPosition, Rectangle palyer1, IMap map) {
		deplacement.setAttributes(ownPosition, palyer1, map);
	}

	@Override
	public void startThinking() {
		deplacement.startThinking();
	}
}
