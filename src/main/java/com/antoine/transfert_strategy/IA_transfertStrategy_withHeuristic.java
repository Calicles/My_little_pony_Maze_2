package com.antoine.transfert_strategy;

import com.antoine.contracts.IMap;
import com.antoine.contracts.IPathfinding;
import com.antoine.contracts.ITransfert_strategy;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Pythagore;
import com.antoine.geometry.Rectangle;
import com.antoine.structure_donnee.pathfinding.Dijkstra_impl;


/**
 * <b>Classe qui utilise l'algorithme passé en paramètre pour trouver le meilleur chemin</b>
 *
 * @author Antoine
 */
public class IA_transfertStrategy_withHeuristic extends IA_transfertStrategy_std implements ITransfert_strategy {

    /**
     * <p>L'algorithme qui doit trouver le chemin vers le joueur.</p>
     */
    private IPathfinding pathfinder;

    /**
     * <p>Coordonnées utilisées pour mettre à jour la nécessité de recalculée l'itinéraire</p>
     */
    private Coordinates precPlayerPosition, currentPlayerPosition, precStep;

    /**
     * <p>La direction courante à suivre pour arriver au but</p>
     */
    private Coordinates currentGoal;


    public IA_transfertStrategy_withHeuristic() {
        super();
        pathfinder = new Dijkstra_impl();
    }


    @Override
    public void setOwnPosition(Rectangle ownPosition){
        super.setOwnPosition(ownPosition);
        pathfinder.setEntity(this.ownPosition);
    }

    @Override
    public void setAttributes(Rectangle ownPosition, Rectangle player, IMap map) {
        super.setAttributes(ownPosition, player, map);
        pathfinder.setEntity(this.ownPosition);
        currentPlayerPosition = new Coordinates(player.getBeginX(), player.getBeginY());
        precPlayerPosition = new Coordinates(player.getBeginX(), player.getBeginY());
        precStep = new Coordinates(0, 0);
    }


    /**
     * <p>Prends comme direction les coordonnées du joueur en utilisant l'algorithme de IPathfinding</p>
     */
    @Override
    protected void manHuntPlayer(){

        //Fixe la dernière position du joueur connu (évite la concurrence de Thread)
        currentPlayerPosition.setCoordinates(player1.getBeginX(), player1.getBeginY());

        precStep.setCoordinates(ownPosition.getBeginX(), ownPosition.getBeginY());

        //Si le Joueur s'est déplacé, on recalcule un nouvel itinéraire via la stratégie implémenté par IPahtfinding
        if (!precPlayerPosition.equals(currentPlayerPosition)){

            precPlayerPosition.setCoordinates(player1.getBeginX(), player1.getBeginY());

            pathfinder.init(new Coordinates(player1.getBeginX(), player1.getBeginY()), map);

            currentGoal = pathfinder.getNextStep();
        }

        //L'entité à atteint l'étape, on renvoi une nouvelle étape vers le joueur
        if (Rectangle.isInBox(ownPosition, currentGoal)) {
            precStep = currentGoal;
            currentGoal = pathfinder.getNextStep();
        }

        //Si le joueur est plus près de l'étape, on le chasse directement
        if (isPlayerNear() || currentGoal == null) {
            goToPlayer();
        }else {
            go();
        }


    }

    /**
     * <p>Calcule si le joueur est plus près que la prochaine étape à atteindre.</p>
     * @return true si coordonnées du joueur plus près, false sinon
     */
    private boolean isPlayerNear() {
        int distToGoal = Pythagore.calculDistance(
                Rectangle.findMiddleCoor(ownPosition),
                       currentGoal
        );
        int distToPlayer = Pythagore.calculDistance(
                Rectangle.findMiddleCoor(ownPosition),
                currentPlayerPosition
        );

        return distToPlayer <= distToGoal;
    }

    /**
     * <p>Se passe du calcule de l'algorithme pour chasser le joueur.</p>
     */
    private void goToPlayer() {
        super.manHuntPlayer();
    }

    /**
     * <p>Calcul du vecteur en fonction de la prochaine position à atteindre.</p>
     */
    private void go() {


        int deltaX, deltaY;

        //Si le précédent déplacement n'est pas bloqué
        if (!directionIsNull()) {

            //Si trop haut
            if (ownPosition.getBeginY() < currentGoal.getY()) {
                deltaY = currentGoal.getY() - ownPosition.getBeginY();

                //Ajuste le vecteur pour atteindre précisément le point
                if (deltaY < vector.getY()) {
                    yDirection = deltaY;

                    //Bouge normalement
                } else
                    yDirection = vector.getY();

                xDirection = 0;

                //Si trop bas
            } else if (ownPosition.getBeginY() > currentGoal.getY()) {
                deltaY = ownPosition.getBeginY() - currentGoal.getY();

                if (deltaY < vector.getY()) {
                    yDirection = -deltaY;

                } else
                    yDirection = -vector.getY();

                xDirection = 0;

                //Si trop à gauche
            } else if (ownPosition.getBeginX() < currentGoal.getX()) {

                deltaX = currentGoal.getX() - ownPosition.getBeginX();

                if (deltaX < vector.getX()) {
                    xDirection = deltaX;

                } else
                    xDirection = vector.getX();

                yDirection = 0;

                //Trop à droite
            } else {
                deltaX = ownPosition.getBeginX() - currentGoal.getX();

                if (deltaX < vector.getX()) {
                    xDirection = deltaX;
                } else
                    xDirection = -vector.getX();

                yDirection = 0;
            }

            //Si Précédent mouvement bloqué, on agit en fonction de la dernière étape pour recalculer un vecteur
        }else {

            System.out.println("bloqué!!!!!!!!!!!!!!!!");

        }
    }

}
