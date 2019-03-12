package com.antoine.entity;


import com.antoine.contracts.IA;
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

	
	private IA transfer_strategy;


	public Boss() {
		super();
	}

	public void setTransfert(IA t) {
		this.transfer_strategy= t;
	}
	
	public void setPosition(String coorDepart) {
	}
	
	public void setAttributes(Rectangle player1, IMap map) {
		transfer_strategy.setAttributes(this.position, player1, map);
	}
	
	public Coordinates memorizeMoves() {
		Coordinates vectors= transfer_strategy.memorizMoves();
		this.changeSprite(vectors);
		position.translate(vectors);
		return vectors;
	}


	/**
	 * <p>Implémente le choix de direction pour prochain mouvement</p>
	 * @param player les coordonnes du joueur (choix de le pourchasser ou non)
	 */
	public void think(Coordinates player) {
		this.transfer_strategy.think(position, player);
	}

}
