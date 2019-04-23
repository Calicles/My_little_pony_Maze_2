package com.antoine.structure_donnee.pathfinding;

import com.antoine.contracts.IMap;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Tile;
import com.antoine.structure_donnee.Node;
import com.antoine.structure_donnee.Node_heuristic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;
import java.util.TreeSet;

/**
 * <b>Implémentation de l'algorithme A*.</b>
 * <p>Doit rechercher le plus court chemin dans graphe en utilisant une heuristique
 * comme priorité de recherche.</p>
 *
 * @author Antoine
 */
public class A_star_impl extends AbstractPathfinding_algo {

    /**
     * <p>Graphe de recherche.</p>
     */
    private ArrayList<Node_heuristic<Tile, Integer>> graphe;

    /**
     * <p>Liste des tuiles non encore scrutées.</p>
     */
    private TreeSet<Node_heuristic<Tile, Integer>> openList;

    /**
     * <p>Liste des tuiles traitées.</p>
     */
    private ArrayList<Node_heuristic<Tile, Integer>> closedList;

    private Node_heuristic<Tile, Integer> currentNode, goal;



    public A_star_impl() {
        super();
    }


    @Override
    protected Stack<Coordinates> createPath(Coordinates start, Coordinates goal, IMap map) {
        return null;
    }

    @Override
    protected void createDataStruct() {
        openList = new TreeSet<>(Comparator.comparingInt(Node_heuristic::getHeuristic));
        closedList = new ArrayList<>();
    }

    @Override
    protected void clear() {

    }
}
