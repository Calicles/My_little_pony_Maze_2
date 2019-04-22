package com.antoine.transfert_strategy;

import com.antoine.contracts.IMap;
import com.antoine.contracts.IPathfinding;
import com.antoine.contracts.ITransfert_strategy;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;
import com.antoine.structure_donnee.pathfinding.Dijkstra_impl;

import java.util.Stack;


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
    private Coordinates currentPlayerPosition;

    /**
     * <p>Pile de coordonnée représentant un pathfinding</p>
     */
    private Stack<Coordinates> path;

    /**
     * <p>La direction courante à suivre pour arriver au but</p>
     */
    private Coordinates currentStep;


    public IA_transfertStrategy_withHeuristic() {
        super();
        pathfinder = new Dijkstra_impl();
    }


    @Override
    public void setOwnPosition(Rectangle ownPosition){
        super.setOwnPosition(ownPosition);
    }

    @Override
    public void setAttributes(Rectangle ownPosition, Rectangle player, IMap map) {
        super.setAttributes(ownPosition, player, map);
        currentPlayerPosition = new Coordinates(player.getBeginX(), player.getBeginY());
    }


    //TODO Remove after Test
    @Override
    public Stack<Coordinates> getPath() {
        return pathfinder.getPath();
    }


    /**
     * <p>Prends comme direction les coordonnées du joueur en utilisant l'algorithme de IPathfinding</p>
     */
    @Override
    protected void manHuntPlayer() {


        currentPlayerPosition.setCoordinates(player1.getBeginX(), player1.getBeginY());

        path = pathfinder.createPath(
                Rectangle.findMiddleCoor(ownPosition),
                Rectangle.findMiddleCoor(player1),
                map
        );

        currentStep = path.pop();

        if (currentStep != null)
            go();

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
    private void go() { //TODO Retirer les "si null" une fois Dijkstra amélioré

        Coordinates middle = Rectangle.findMiddleCoor(ownPosition);

        ownPosition.setCoordinates(currentStep.getX() - ownPosition.getWidth() / 2, currentStep.getY() - ownPosition.getHeight() / 2);

    }

    private boolean inHeight() {
        return (currentStep.getY() >= ownPosition.getBeginY() && currentStep.getY() <= ownPosition.getEndY());
    }

    private boolean inWidth() {
        return (currentStep.getX() >= ownPosition.getBeginX() && currentStep.getX() <= ownPosition.getEndX());
    }
}
