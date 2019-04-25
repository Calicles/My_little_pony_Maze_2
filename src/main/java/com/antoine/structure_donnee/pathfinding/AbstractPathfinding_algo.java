package com.antoine.structure_donnee.pathfinding;

import com.antoine.contracts.IMap;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Pythagore;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;
import com.antoine.structure_donnee.Node;

import java.util.Stack;

/**
 * <b>Cadre de l'algorithme de recherche du meilleur chemin</b>
 *
 * @author Antoine
 */
public abstract class AbstractPathfinding_algo {


    /**
     * <p>Le path en coordonnée</p>
     */
    protected Stack<Coordinates> path;

    protected Coordinates start;

    protected Rectangle surface;


    //==================  Constructeurs  =============

    public AbstractPathfinding_algo() {
        path = new Stack<>();

        createDataStruct();
    }

    //================================================


    protected abstract void createDataStruct();

    public abstract Stack<Coordinates> createPath(Rectangle mover, Coordinates goal, IMap map);

    protected void createRectangle(Rectangle mover, Coordinates goal, IMap map) {

        //Reset les données, si précédent appel
        clear();


        start = getMoverStartCorner(mover, goal);


        //========Création du rectangle pour découper la carte=======
        int x = Math.min(start.getX(), goal.getX());
        int y = Math.min(start.getY(), goal.getY());

        int width = Math.max(start.getX(), goal.getX());
         int height = Math.max(start.getY(), goal.getY());

        //Si rectangle trop petit, on l'agrandit pour trouver des chemins possibles
        if (width < (Tile.getWidth() * 10)) {
            width += (Tile.getWidth() * 10);
        }
        if (height < (Tile.getHeight() * 10)) {
            height += (Tile.getHeight() * 10);
        }


        surface = new Rectangle(x, x + width, y, y + height);
        //===========================================================
    }

    /**
     * <p>Calcule de distance par rapport à la tuile du noeud courant.</p>
     * @param node le noeud dont la distance de la tuile doit être calculé
     * @return la distance euclidienne
     */
    protected int getDist(Node<Tile> node, Node<Tile> node2){
        Tile t =  node.getItem();
        Tile t2 = node2.getItem();

        return Pythagore.calculDistanceInSquarre(t.toCoordinates(), t2.toCoordinates());
    }

    /**
     * <p>Reset les données pour nouveaux calculs.</p>
     */
    protected abstract void clear();

    /**
     * <p>Calcul la meilleure coordonnée de départ.</p>
     * @param mover l'entité qui se déplace
     * @param goal les coordonnées du but à atteindre
     * @return la meilleur coordonnée pour prendre la direction la plus rapide pour un personnage de grande surface
     */
    protected Coordinates getMoverStartCorner(Rectangle mover, Coordinates goal) {

        Coordinates middleMover = Rectangle.findMiddleCoor(mover);

        if (goal.getY() > mover.getEndY()) {

            if (goal.getX() > middleMover.getX()) {

                return new Coordinates(mover.getEndX(), mover.getEndY());

            } else if (goal.getX() < middleMover.getX()) {

                return new Coordinates(mover.getBeginX(), mover.getEndY());

            } else {

                return new Coordinates(middleMover.getX(), mover.getEndY());
            }
        } else if (goal.getY() < mover.getBeginY()) {

            if (goal.getX() > middleMover.getX()) {

                return new Coordinates(mover.getEndX(), mover.getBeginY());

            } else if (goal.getX() < middleMover.getX()) {

                return new Coordinates(mover.getBeginX(), mover.getBeginY());

            } else {

                return new Coordinates(middleMover.getX(), mover.getBeginY());
            }
        } else {

            if (goal.getX() < mover.getBeginX()) {

                return new Coordinates(mover.getBeginX(), middleMover.getY());

            }else

                return new Coordinates(mover.getEndX(), middleMover.getY());
        }
    }

}
