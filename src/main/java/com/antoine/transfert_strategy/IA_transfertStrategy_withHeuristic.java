package com.antoine.transfert_strategy;

import com.antoine.contracts.IPathfinding;
import com.antoine.geometry.Coordinates;
import com.antoine.structure_donnee.pathfinding.Dijkstra_impl;

public class IA_transfertStrategy_withHeuristic extends IA_transfertStrategy_std {

    private IPathfinding pathfinder;
    private Coordinates precPlayerPosition;
    private Coordinates currentGoal;

    public IA_transfertStrategy_withHeuristic() {
        super();
        pathfinder = new Dijkstra_impl();
    }

    @Override
    protected void manHuntPlayer(){

        if (precPlayerPosition == null){
            precPlayerPosition = new Coordinates(player1.getBeginX(), player1.getBeginY());
        }

        if (!precPlayerPosition.equals(new Coordinates(player1.getBeginX(), player1.getBeginY()))){
            pathfinder.init(new Coordinates(player1.getBeginX(), player1.getBeginY()), map);
            currentGoal = pathfinder.getNextStep();
        }

        if (ownPosition.equalsCoordinates(currentGoal)) {
            currentGoal = pathfinder.getNextStep();
        }

        if (currentGoal == null)
            goToPlayer();
        else
            go();


    }

    private void goToPlayer() {
        super.manHuntPlayer();
    }

    /**
     * <p>Calcul du vecteur en fonction de la prochaine position à atteindre</p>
     */
    private void go() {

        int deltaX, deltaY;

        //Si joueur trop haut
        if (ownPosition.getBeginY() < currentGoal.getY()){
            deltaY = currentGoal.getY() - ownPosition.getBeginY();

            //Ajuste le vecteur pour atteindre précisément le point
            if (deltaY < vector.getY()){
                yDirection = deltaY;

            //Bouge normalement
            }else
                yDirection = vector.getY();

        //Si trop bas
        }else if (ownPosition.getBeginY() > currentGoal.getY()) {
            deltaY = ownPosition.getBeginY() - currentGoal.getY();

            if (deltaY < vector.getY()){
                yDirection = -deltaY;

            }else
                yDirection = -vector.getY();

        //Si trop à gauche
        }else if (ownPosition.getBeginX() < currentGoal.getX()) {

            deltaX = currentGoal.getX() - ownPosition.getBeginX();

            if (deltaX < vector.getX()){
                xDirection = deltaX;

            }else
                xDirection = vector.getX();

        //Trop à droite
        }else {
            deltaX = ownPosition.getBeginX() - currentGoal.getX();

            if (deltaX < vector.getX()){
                xDirection = deltaX;
            }else
                xDirection = -vector.getX();
        }
    }

}
