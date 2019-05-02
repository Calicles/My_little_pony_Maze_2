package com.antoine.transfert_strategy;

import com.antoine.contracts.IMap;
import com.antoine.contracts.ITransfert_strategy;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;

import java.util.Stack;

/**
 * <b>Implémente une stratégie de déplacement pour un personnage non joueur.</b>
 *
 * @author Antoine
 */
public class IA_transfertStrategy_std extends AbstractTransfer implements ITransfert_strategy {

	/**Le Thread qui prend en charge les calcules de trajectoire,
	 *  afin de soulager le temps de calcule du Thread qui gère la boucle de jeu*/
	protected Thread greyCell;

	/**Position de ce personnage et du joueur*/
	protected Rectangle ownPosition, player1;

	/**Le dernier vecteur de déplacement différent du vecteur nulle est enregistré*/
	protected Coordinates lastVector;

	/**La carte de jeu*/
	protected IMap map;

	/**True si le personnage n'a pas fini son cycle de calcule dans la recherche d'un chemin (dans ce cas pas de déplacement,
	 * false si calcul terminé.
	 */
	protected boolean thinking;

	//==============================    Constructeur   ========================================

	public IA_transfertStrategy_std(Rectangle ownPosition, Rectangle player1, IMap map) {
		super();
	}
	
	public IA_transfertStrategy_std() {
		super();
	}

	//=========================================================================================

	@Override
	public void setVector(Coordinates vector){
		super.setVector(vector);
		this.xDirection= 0;
		this.yDirection= vector.getY();
		this.lastVector= new Coordinates();
	}

	public void setOwnPosition(Rectangle ownPosition){
		this.ownPosition= ownPosition;
	}

	public void setPlayer1(Rectangle player1){
		this.player1= player1;
	}

	public void setAttributes(Rectangle ownPosition, Rectangle player1, IMap map) {
		this.map= map;
		this.ownPosition= ownPosition;
		this.player1= player1;
	}

	public void startThinking(){
		buildThinkPattern();
		greyCell.start();
	}

	//TODO Remove after test
	@Override
	public Stack<Coordinates> getPath() {
		return null;
	}

	/**
	 * <p>Réveille le Thread de calcule, mis en pause après avoir trouvé un chemin ou un vecteur de déplacement</p>
	 * */
	@Override
	public void think() {

		synchronized(this) {
			notify();
			thinking= true;
		}
	}

	/**
	 * <p>Construit les instructions du Thread greyCell.</p>
	 */
	private void buildThinkPattern() {
		greyCell= new Thread(()->{
			thinking= true;
			while(true){
				search();
				pause();
			}
		});
		
	}

	/**
	 * <p></p>
	 */
	private void search(){

		if (isPlayerNext(300)){

			released();

			manHuntPlayer();

		}else {
			findWay();
		}
		this.adaptVectors(ownPosition, map);
	}
	
	private void findWay() {
		if(xDirection == 0 && yDirection == 0) {
			checkLastVectors();
		}
	}

	protected void checkLastVectors() {

		Tile tile;
		if(lastVector.getX() < 0 || lastVector.getX() > 0) {
			if(ownPosition.getBeginY() != 0 && (tile= this.checkOnUpTiles(this.ownPosition, map)) == null) {
				yDirection= -vector.getY(); xDirection= 0;
			}else if(ownPosition.getEndY() != map.getHeight() && (tile= this.checkOnDownTiles(ownPosition, map)) == null) {
				yDirection= vector.getY(); xDirection= 0;
			}else {
				//GO back
				if(vector.getX() < 0)
					xDirection= vector.getX();
				else 
					xDirection= -vector.getX();
				yDirection= 0;
			}
		}else {
			if(ownPosition.getBeginX() != 0 && (tile= this.checkLeftTiles(ownPosition, map)) == null) {
				xDirection= -vector.getX(); yDirection= 0;
			}else if(ownPosition.getEndX() != map.getWidth() && (tile= this.checkRightTiles(ownPosition, map)) == null) {
				xDirection= vector.getX(); yDirection= 0;
			}else {
				if(vector.getY() < 0)
					yDirection= vector.getY();
				else
					yDirection= -vector.getY();
				xDirection= 0;
			}
		}

	}

	protected void manHuntPlayer() {

		Coordinates bossMidle= Rectangle.findMiddleCoor(ownPosition);
		Coordinates playerMidle= Rectangle.findMiddleCoor(player1);

			//Player in height bounds
			if ((playerMidle.getY() > ownPosition.getBeginY() && playerMidle.getY() < ownPosition.getEndY())) {


				//player on right side
				if (bossMidle.getX() < playerMidle.getX()) {

					if (checkRightTiles(ownPosition, map) == null)
					xDirection = vector.getX();

				} else {

					if (checkLeftTiles(ownPosition, map) == null)
					xDirection = -vector.getX();
				}
				yDirection = 0;



			} else {

				if (bossMidle.getY() < playerMidle.getY()) {

					if (checkOnUpTiles(ownPosition, map) == null)
					yDirection = vector.getY();

				} else {

					if (checkOnDownTiles(ownPosition, map) == null)
					yDirection = -vector.getY();
				}
				xDirection = 0;


			}

		adaptVectors(ownPosition, map);

		//blocked
		if (directionIsNull()) {

			//take last direction
			xDirection = lastVector.getX();
			yDirection = lastVector.getY();

			adaptVectors(ownPosition, map);

			//again blocked
			if (directionIsNull()) {

				findWay();
			}
		}

	}

	protected boolean isPlayerNext(int radius){
		return Rectangle.isNext(ownPosition, player1, radius);
	}

	private void pause() {
		try {
			synchronized(this) {
				thinking= false;
				wait();
			}
		}catch(InterruptedException ignored) {}
	}

	@Override
	public void released() {
		xDirection= 0;
		yDirection= 0;
	}

	@Override
	public void movesLeft() {

		xDirection = -vector.getX();
		yDirection = 0;
	}

	@Override
	public void movesRight() {

		xDirection = vector.getX();
		yDirection = 0;
	}

	@Override
	public void movesUp() {

		yDirection = -vector.getY();
		xDirection = 0;
	}

	@Override
	public void movesDown() {
		yDirection= vector.getY();
		xDirection= 0;
	}

	@Override
	public Coordinates memorizeMoves(Rectangle position, IMap map) {

		return null;
	}

	public Coordinates memorizeMoves() {
		if(!thinking) {
			// finished thinking
			if(xDirection != 0 || yDirection != 0){
				lastVector.setCoordinates(xDirection, yDirection);
			}
			return new Coordinates(xDirection, yDirection);
		}else									
			// don't moves
			return new Coordinates(0, 0);
	}

	protected boolean directionIsNull(){
		return xDirection == 0 && yDirection == 0;
	}

}
