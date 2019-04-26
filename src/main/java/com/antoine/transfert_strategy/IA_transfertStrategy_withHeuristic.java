package com.antoine.transfert_strategy;

import com.antoine.contracts.IMap;
import com.antoine.contracts.IPathfinding;
import com.antoine.contracts.ITransfert_strategy;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;
import com.antoine.structure_donnee.HeuristicBestDistance;
import com.antoine.structure_donnee.pathfinding.A_star_2;

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
    private Coordinates currentPlayerPosition, oldPlayerPos;

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
        pathfinder = new A_star_2<Integer>();
        pathfinder.setHeuristic(new HeuristicBestDistance());
    }


    @Override
    public void setOwnPosition(Rectangle ownPosition){
        super.setOwnPosition(ownPosition);
    }

    @Override
    public void setAttributes(Rectangle ownPosition, Rectangle player, IMap map) {
        super.setAttributes(ownPosition, player, map);
        currentPlayerPosition = new Coordinates(player.getBeginX(), player.getBeginY());
        oldPlayerPos = new Coordinates(player.getBeginX(), player.getBeginY());
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

        Coordinates middle = Rectangle.findMiddleCoor(ownPosition);
        Rectangle nexus = new Rectangle(middle.getX() - 3, middle.getX() + 3, middle.getY() - 3, middle.getY() + 3);



        currentPlayerPosition.setCoordinates(Rectangle.findMiddleCoor(player1));

        if (!oldPlayerPos.equals(currentPlayerPosition) || currentStep == null) {
            oldPlayerPos.setCoordinates(currentPlayerPosition.getX(), currentPlayerPosition.getY());

            path = pathfinder.createPath(
                    ownPosition, currentPlayerPosition,
                    map
            );
            if (path != null && !path.isEmpty()) {
                currentStep = path.pop();
            }
        }

        if (currentStep != null) {
            if (Rectangle.isInBox(nexus, currentStep)) {

                if (path != null && !path.isEmpty())
                    currentStep = path.pop();
                else
                    currentStep = null;
            }
        }
        go(middle, nexus);
    }

    /**
     * <p>Se passe du calcule de l'algorithme pour chasser le joueur.</p>
     */
    private void goToPlayer() {
        super.manHuntPlayer();
    }

    /**
     * <p>Calcul du vecteur en fonction de la prochaine position à atteindre.</p>
     * @param middle
     * @param nexus
     */
    private void go(Coordinates middle, Rectangle nexus)
    {

        if(currentStep != null) {

            if (currentStep.getX() < middle.getX() && inHeight(nexus))
                movesLeft();
            else if (currentStep.getY() < middle.getY())
                movesUp();
            else if (currentStep.getX() > middle.getX() && inHeight(nexus))
                movesRight();
            else if (currentStep.getY() > middle.getY())
                movesDown();
        }else
            released();
    }

    private boolean inHeight(Rectangle nexus) {
        return (currentStep.getY() >= nexus.getBeginY() && currentStep.getY() <= nexus.getEndY());
    }

    private boolean inWidth(Rectangle nexus) {
        return (currentStep.getX() >= nexus.getBeginX() && currentStep.getX() <= nexus.getEndX());
    }
}
