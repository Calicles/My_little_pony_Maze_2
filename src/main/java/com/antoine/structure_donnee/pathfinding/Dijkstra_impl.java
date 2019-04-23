package com.antoine.structure_donnee.pathfinding;

import com.antoine.contracts.IMap;
import com.antoine.contracts.IPathfinding;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Pythagore;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;
import com.antoine.structure_donnee.Node;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * <b>Classe qui implémente l'algorithme de Dijkstra</b>
 * <p>Trouve le plus court chemin pour se rendre sur une Tile.</p>
 * fonctionne en deux vagues:
 * 1 enregistre le chemin de tuile à tuile.
 * 2 affine le path de point en point.
 *
 * @author Antoine
 */
public class Dijkstra_impl extends AbstractPathfinding_algo implements IPathfinding {

    /**
     * <p>Liste contenant l'ensemble de recherche</p>
     */
    protected ArrayList<Node<Tile>> S;

    /**
     * <p>List for checking</p>
     */
    protected TreeSet<Node<Tile>> openList;

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

    /**
     * <p>Le path en coordonnée</p>
     */
    private Stack<Coordinates> path;

    /**
     * <p>Fonction utilisé pour trouver les Tiles adjacentes.</p>
     */
    private Predicate<Node<Tile>> isAdjacent;

    private int distofAdjecent;


    //=======   Constructeurs ==========

    public Dijkstra_impl() {
        super();

        initMethods();

        distofAdjecent = Pythagore.square(Tile.getWidth());

        path = new Stack<>();
    }

    //=================================

    @Override
    protected void createDataStruct() {

        S = new ArrayList<>();

        adjNodes = new ArrayList<>();

        closedList = new ArrayList<>();

        openList = new TreeSet<>(Comparator.comparingInt(Node::getWeight));
    }

    /**
     * <p>Initialise les Méthodes des attributs : byWeight et getMin.</p>
     */
    private void initMethods() {

        isAdjacent = t-> getDist(t, currentNode) <= distofAdjecent;
    }

    /**
     * <p>Initialise les données pour débuter l'algorithme</p>
     * @param start les coordonnées de départ
     * @param goal les coordonnées du but à atteindre
     * @param map la carte
     * @return une pile contenant le path, null si pas de chemin
     */
    @Override
    public Stack<Coordinates> createPath(Coordinates start, Coordinates goal, IMap map) {
        //Reset les données, si précédent appel
        clear();
        path.clear();

        //Pout découper la partie de la carte intéressante
        int x, y, width, height;

        Node<Tile> node;

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


        //Récupère le morceau de la carte selon le rectangle
        List<Tile> subList = map.getSubMap(new Rectangle(x, width, y, height));

        for (Tile t : subList) {
            node = new Node<>(t);

            //N'ajoute pas la tuile si elle est solide
            if (!node.getItem().isSolid())
                S.add(node);

            //Si la tuile contient les coordonnées de l'entité, elle est placé en départ
            if (t.contains(start)) {
                node.setWeight(0);
                adjNodes.add(node);
                currentNode = node;

                //Si contient les coordonées du but, la tuile est en arrivée
            } else if (t.contains(goal)) {
                this.goal = node;
            }

        }
        openList.addAll(S);

        openList.remove(currentNode);

        closedList.add(currentNode);

        return startSearch();
    }

    @Override
    protected void clear() {
        S.clear();
        adjNodes.clear();
        path.clear();
        openList.clear();
        closedList.clear();
    }

    /**
     * <p>Boucle l'algorithme pour passer en revu tous les chemins.</p>
     * Rempli le graphe des tuiles adjacentes à la tuile courante.
     * Selectionne la meilleur comme tuile courante.
     *
     * Crée une liste chaînée en reseignant le parent de chaque noeud
     */
    protected Stack<Coordinates> startSearch() {

        while (!openList.isEmpty()) {

            fillAdjGraph();

            selectNextNode();

        }
        return createFinalPath();
    }

    /**
     * <p>Affine le path pour produire un chemin de point en point.</p>
     * place les coordonnées du milieu de la prochaine tuile
     */
    private Stack<Coordinates> createFinalPath() {

        Node<Tile> node = goal;

        while (node != null) {

            path.push(Rectangle.findMiddleCoor(node.getItem().toRectangle()));

            node = node.getParent();
        }

        return path;
    }

    /**
     * <p>Selectionne le prochain noeud de plus petit poids dans le graphe des cases adjacentes
     * et le place en tant que noeud courant.</p>
     */
    @Override
    protected void selectNextNode() {


        Optional<Node<Tile>> res = adjNodes.stream()
                .min(Comparator.comparingInt(Node::getWeight));

        if (res.isPresent()) {
            currentNode = res.get();
            openList.remove(currentNode);
            closedList.add(currentNode);
        }else
            currentNode = null;
    }


    /**
     * <p>Rempli le graphe des cases adjacentes à la case courante</p>
     * Construit la liste chaînée qui sera remontée pour construire le path final
     */
    @Override
    protected void fillAdjGraph() {

        List<Node<Tile>>  buffer = openList.stream()

                .filter(t-> isAdjacent.test(t))

                .collect(Collectors.toList());

        adjNodes.clear();

        adjNodes.addAll(buffer);

        //Parcours du graphe des tuile adjacentes
        for (Node<Tile> n : adjNodes) {

            int distance = getDist(n, currentNode) + currentNode.getWeight();

            //Si la distance est plus avantageuse que la précédente on change le poids (en distance) du noeud
            if (n.getWeight() > distance || n.getWeight() == -1) {

                n.setWeight(distance);

                //On évite placé la première case dans la liste chaîné pour que la prochaine étape du path soit toujours
                //entre le départ et l'arrivée
                if (currentNode.getWeight() != 0)
                    n.setParentNode(currentNode);
                }
        }
    }

    /**
     * <p>Retire de la pile la coordonées suivante.</p>
     *
     * @return la coordonnée retirée
     */
    public Coordinates getNextStep() {
        return null;
    }


    //TODO Remove after test
    public Stack<Coordinates> getPath() {
        return (Stack<Coordinates>) path.clone();
    }

}
