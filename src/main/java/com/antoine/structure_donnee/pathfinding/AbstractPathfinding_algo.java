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


    //==================  Constructeurs  =============

    public AbstractPathfinding_algo() {
        createDataStruct();
    }

    //================================================


    protected abstract void createDataStruct();

    protected abstract void selectNextNode();

    protected abstract void fillAdjGraph();

    protected abstract Stack<Coordinates> createPath(Coordinates start, Coordinates goal, IMap map);

    /**
     * <p>Calcule de distance par rapport à la tuile du noeud courant.</p>
     * @param node le noeud dont la distance de la tuile doit être calculé
     * @return la distance euclidienne
     */
    protected int getDist(Node<Tile, Object> node, Node<Tile, Object> node2){
        Tile t =  node.getItem();
        Tile t2 = node2.getItem();

        return Pythagore.calculDistanceInSquarre(t.toCoordinates(), t2.toCoordinates());
    }

    /**
     * <p>Reset les données pour nouveaux calculs.</p>
     */
    protected abstract void clear();

}
