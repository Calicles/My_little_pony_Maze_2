package com.antoine.structure_donnee.pathfinding;

import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Pythagore;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;
import com.antoine.structure_donnee.Node;

import java.util.Comparator;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * <b>Classe qui implémente l'algorithme de Dijkstra</b>
 * <p>Trouve le plus court chemin pour se rendre sur une Tile</p>
 * @author Antoine
 */
public class Dijkstra_impl extends AbstractPathfinding_algo {


    /**
     * <p>Boucle l'algorithme pour passer en revu tous les chemins.</p>
     */
    @Override
    protected void startSearch() {

        //Tant qu'on a pas trouvé la fin ou qu'il y a une issue
        while (currentNode != goal && currentNode != null) {

            selectNextNode();

            fillAdjGraph();

        }

        createPath();
    }

    /**
     * <p>Selectionne le prochain noeud de plus petit poids dans le graphe des cases adjacentes
     * et le place en tant que noeud courant.</p>
     */
    @Override
    protected void selectNextNode() {

        currentNode = adjNodes.stream()

                //Retourne le plus petit Poids
                .min(Comparator.comparingInt(Node::getWeight))

                //Retourne le Noeud
                .get();

        //On le marque comme utilisé
        if (currentNode != null)
            currentNode.used();
    }


    /**
     * <p>Rempli le graphe des cases adjacentes</p>
     */
    @Override
    protected void fillAdjGraph() {

        //Distance pour être adjacente
        int adjacente = currentNode.getItem().getWidth();

        //Retiens les Noeuds dont le poids est intéressant
        Predicate<Node<Tile>> predicate = t->{

            //Distance de ce noeud au noeud courant avec ajout de la distance déja parcouru
            int dist = getDistFromCurrentNode(t) + t.getParent().getWeight();

            //Si la distance est inférieure au précédent calcul on change son parent pour chemin plus court
            //Et on la garde comme adjacente
            if (t.getWeight() <= dist || t.getWeight() == -1){
                t.setWeight(dist);
                t.setParentNode(currentNode);
                return  true;
            }
            return false;
        };

        //Efface le graphe de casses adjacentes au précédent Noeuds
        adjNodes.clear();

        //Rempli le graphe des cases adjacentes au noeud courant
        adjNodes.addAll(

            S.stream()

                    //Filtre si le noeud est déja utilisé et si adjacent au noeud courant
                    .filter(t-> !t.isUsed() && getDistFromCurrentNode(t) <= adjacente)

                    .filter(predicate)

                    .collect(Collectors.toList())
        );

    }

    /**
     * <p>Rempli la pile "path" en remontant le file de l'arrivée jusqu'au départ</p>
     */
    @Override
    protected void createPath() {

        path.clear();

        Node<Tile> n = goal;
        while (n.getParent() != null) {
            path.push(n);

            n= n.getParent();
        }
    }

    @Override
    public Coordinates getVector(Rectangle position, Rectangle goal) {
        Tile current = path.peek().getItem();
        Coordinates middlePosition, middleTile, middleGoal;

        middlePosition = Rectangle.findMiddleCoor(position);
        middleTile = Rectangle.findMiddleCoor(current.toRectangle());
        middleGoal = Rectangle.findMiddleCoor(goal);

        if (Pythagore.calculDistance(middlePosition, middleGoal) < Pythagore.calculDistance(middleTile, middleGoal)) {
            path.pop();
            current = path.peek().getItem();
        }

        return goTo(current, position);
    }

    private Coordinates goTo(Tile current, Rectangle position) {
        return null;
    }
}
