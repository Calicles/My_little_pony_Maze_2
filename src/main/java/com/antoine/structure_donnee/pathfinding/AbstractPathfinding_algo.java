package com.antoine.structure_donnee.pathfinding;

import com.antoine.contracts.IMap;
import com.antoine.contracts.IPathfinding;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Pythagore;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;
import com.antoine.structure_donnee.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * <b>Cadre de l'algorithme de recherche du meilleur chemin</b>
 *
 * @author Antoine
 */
public abstract class AbstractPathfinding_algo implements IPathfinding {

    /**
     * <p>Liste contenant l'ensemble de recherche</p>
     */
    protected ArrayList<Node<Tile>> S;
    /**
     * <p>graphe des tuiles adjacentes à la tuile courante dans la recherche</p>
     */
    protected ArrayList<Node<Tile>> adjNodes;
    /**
     * <p>pile construite à la fin de l'algorithme, contient le path à l'echelle des tuiles</p>
     */
    protected Stack<Node<Tile>> path;
    /**
     * <p>pile du chemin affiné, point à point</p>
     */
    protected Stack<Coordinates> finalPath;
    /**
     * <p>Les noeuds utilisés dans la recherche</p>.
     * CurrentNode : le noeud entrain d'être examiné.
     * goal : l'arrivé, pour tester la fin de l'algorithme.
     */
    protected Node<Tile> goal, currentNode;
    /**
     * <p>les données de l'entité concernée par la recherche du meilleur chemin</p>
     */
    protected Rectangle entity;


    //==================  Constructeurs  =============
    public AbstractPathfinding_algo() {
        S = new ArrayList<>();
        adjNodes = new ArrayList<>();
        path = new Stack<>();
        finalPath = new Stack<>();
    }

    public AbstractPathfinding_algo(Rectangle entity){
        S = new ArrayList<>();
        adjNodes = new ArrayList<>();
        path = new Stack<>();
        finalPath = new Stack<>();

        this.entity = entity;
    }
    //================================================

    public void setEntity(Rectangle entity) {
        this.entity = entity;
    }
    /**
     * <p>Initialise les données pour débuter l'algorithme</p>
     * @param goal les coordonnées du but à atteindre
     * @param map la carte
     */
    @Override
    public void init(Coordinates goal, IMap map) {
        //Reset les données, si précédent appel
        clear();

        //Centre la recherche sur les coordonnées de l'entité
        Coordinates start = new Coordinates(entity.getBeginX(), entity.getBeginY());

        //Pout découper la partie de la carte intéressante
        int x, y, width, height;

        Node<Tile> node;

        //========Création du rectangle pour découper la carte=======
        x= Math.min(start.getX(), goal.getX());
        y= Math.min(start.getY(), goal.getY());

        width = Math.max(start.getX(), goal.getX()) - x;
        height = Math.max(start.getY(), goal.getY()) - y;
        //===========================================================


        //Récupère le morceau de la carte qui contient le départ et l'arrivée
        //pour éviter de traiter de trop grandes informations
        List<Tile> subList = map.getSubMap(x, width, y, height);

        for (Tile t:subList){
            node = new Node<>(t);

            //N'ajoute pas la tuile si elle est solide
            if (!node.getItem().isSolid())
                S.add( node );

            //Si la tuile contient les coordonnées de l'entité, elle est placé en départ
            if (t.contains(start)) {
                node.setWeight(0);
                adjNodes.add(node);

            //Si contient les coordonées du but, la tuile est en arrivée
            }else if (t.contains(goal))
                this.goal = node;

        }

        startSearch();
        createPath();
    }

    protected abstract void selectNextNode();

    protected abstract void fillAdjGraph();

    protected abstract void startSearch();

    protected abstract void createPath();

    @Override
    public abstract Coordinates getNextStep();

    /**
     * <p>Calcule de distance par rapport à la tuile du noeud courant.</p>
     * @param node le noeud dont la distance de la tuile doit être calculé
     * @return la distance euclidienne
     */
    protected int getDistFromCurrentNode(Node<Tile> node){
        Tile t =  node.getItem();
        Tile current = currentNode.getItem();

        return Pythagore.calculDistance(t.toCoordinates(), current.toCoordinates());
    }

    /**
     * <p>Reset les données pour nouveaux calculs.</p>
     */
    protected void clear(){
        if(!S.isEmpty()){
            S.clear();
            path.clear();
        }
    }
}
