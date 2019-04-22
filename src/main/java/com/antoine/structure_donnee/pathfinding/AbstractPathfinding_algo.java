package com.antoine.structure_donnee.pathfinding;

import com.antoine.contracts.IMap;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Pythagore;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;
import com.antoine.structure_donnee.Node;

import java.util.ArrayList;
import java.util.Stack;

/**
 * <b>Cadre de l'algorithme de recherche du meilleur chemin</b>
 *
 * @author Antoine
 */
public abstract class AbstractPathfinding_algo {

    /**
     * <p>les positions de l'entité qui recherche le meilleur chemin</p>
     */
    protected Rectangle entity;

    /**
     * <p>Liste contenant l'ensemble de recherche</p>
     */
    protected ArrayList<Node<Tile>> S;

    /**
     * <p>List for checking</p>
     */
    protected ArrayList<Node<Tile>> openList;

    /**
     * <p>Closed list, checked</p>
     */

    protected ArrayList<Node<Tile>> closedList;

    /**
     * <p>graphe des tuiles adjacentes à la tuile courante dans la recherche</p>
     */
    protected ArrayList<Node<Tile>> adjNodes;

    /**
     * <p>Les noeuds utilisés dans la recherche</p>.
     * CurrentNode : le noeud entrain d'être examiné.
     * goal : l'arrivé, pour tester la fin de l'algorithme.
     */
    protected Node<Tile> goal, currentNode;


    //==================  Constructeurs  =============

    public AbstractPathfinding_algo() {
        S = new ArrayList<>();

        adjNodes = new ArrayList<>();

        openList = new ArrayList<>();

        closedList = new ArrayList<>();

    }

    //================================================



    protected abstract void selectNextNode();

    protected abstract void fillAdjGraph();

    protected abstract Stack<Coordinates> createPath(Coordinates start, Coordinates goal, IMap map);

    /**
     * <p>Calcule de distance par rapport à la tuile du noeud courant.</p>
     * @param node le noeud dont la distance de la tuile doit être calculé
     * @return la distance euclidienne
     */
    protected int getDistFromCurrentNode(Node<Tile> node){
        Tile t =  node.getItem();
        Tile current = currentNode.getItem();

        return Pythagore.calculDistanceInSquarre(t.toCoordinates(), current.toCoordinates());
    }

    /**
     * <p>Reset les données pour nouveaux calculs.</p>
     */
    protected void clear(){
        S.clear();
        adjNodes.clear();
        openList.clear();
        closedList.clear();
    }

}
