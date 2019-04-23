package com.antoine.structure_donnee.pathfinding;

import com.antoine.contracts.IMap;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Pythagore;
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

    protected int x, y, width, height;


    //==================  Constructeurs  =============

    public AbstractPathfinding_algo() {
        path = new Stack<>();

        createDataStruct();
    }

    //================================================


    protected abstract void createDataStruct();

    protected abstract Stack<Coordinates> createPath(Coordinates start, Coordinates goal, IMap map);

    protected void createRectangle(Coordinates start, Coordinates goal, IMap map) {

        //Reset les données, si précédent appel
        clear();


        //========Création du rectangle pour découper la carte=======
        x = Math.min(start.getX(), goal.getX());
        y = Math.min(start.getY(), goal.getY());

        width = Math.max(start.getX(), goal.getX());
        height = Math.max(start.getY(), goal.getY());

        //Si rectangle trop petit, on l'agrandit pour trouver des chemins possibles
        if (width < (Tile.getWidth() * 10)) {
            width += (Tile.getWidth() * 10);
        }
        if (height < (Tile.getHeight() * 10)) {
            height += (Tile.getHeight() * 10);
        }
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

}
