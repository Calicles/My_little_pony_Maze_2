package com.antoine.structure_donnee.pathfinding;

import com.antoine.contracts.IHeuristic;
import com.antoine.contracts.IMap;
import com.antoine.contracts.IPathfinding;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;
import com.antoine.structure_donnee.Node_heuristic;

import java.util.*;

/**
 * <b>Implémente l'algorithme A*,
 * avec en complément placement du path en fonction de la surface de l'entité qui se déplace</b>
 *
 * @param <T> Le type utilisé pour le calcule de l'heuristique
 *
 * @author Antoine
 */
public class A_star_2<T> extends AbstractPathfinding_algo implements IPathfinding {

    /**Sous matrice découpée dans la map*/
    private Node_heuristic<Tile, T>[][] Matrix;

    /**Liste des tuiles selectionnées pour examen*/
    private TreeSet<Node_heuristic<Tile, T>> openList;

    /**listes des tuiles examinées*/
    private ArrayList<Node_heuristic<Tile, T>> closedList;

    /**heuristique utilisé dans l'algorithme*/
    private IHeuristic<T> heuristic;

    private Node_heuristic<Tile, T> currentNode, adjacentNode, goal;

    /**
     * <p>Constructeur plein</p>
     * @param heuristic utilisé pour l'algorithme
     */
    public A_star_2(IHeuristic heuristic) {

        this.heuristic = heuristic;

    }

    public A_star_2() {
    }

    public void setHeuristic(IHeuristic heuristic) {
        this.heuristic = heuristic;

            Comparator<Node_heuristic<Tile, T>> comp = heuristic.getComparator();
        openList = new TreeSet<>(heuristic.getComparator());
    }

    /**
     * @see IPathfinding#getPath()
     * @return le path construit
     */
    @Override
    public Stack<Coordinates> getPath() {
        return (Stack<Coordinates>) path.clone();
    }


    @Override
    public Node_heuristic getAdjacentNode() {
        return this.adjacentNode;
    }

    @Override
    public Node_heuristic getGoalNode() {
        return this.goal;
    }

    /**
     * @see AbstractPathfinding_algo#createDataStruct()
     */
    @Override
    protected void createDataStruct()
    {
        closedList = new ArrayList<>();
    }

    /**
     * @see IPathfinding#createPath(Rectangle, Coordinates, IMap)
     * @param mover l'entité qui se déplace
     * @param goal le but à atteindre
     * @param map la carte sur laquelle se deplace le mover
     * @return une pile contenant les coordonnées à atteindre
     */
    @Override
    public Stack<Coordinates> createPath(Rectangle mover, Coordinates goal, IMap map)
    {
        this.createRectangle(mover, goal, map);

        setData(mover, goal, map);

        Coordinates currentNodeCoor;
        int row, col;

        //+++++++++++++++++++++++++++++++++++    start's algorithm   +++++++++++++++++++++++++++++++++++++++++

        while (!openList.isEmpty()) {

            currentNode = openList.first();

            if (currentNode == this.goal) {

                adapt_path_forMover(mover, map);

                return path;
            }

            //==========   Calcul index of currentNode in Matrix  ===========

            currentNodeCoor = currentNode.getItem().toCoordinates();

            //=================   get adjacents Tiles   ==================

            for (int i = -1 ; i < 2; i++) {

                for (int j = -1; j < 2; j++) {

                    //the current tile
                    if (i == 0 && j == 0)
                        continue;

                    //diagonal tile
                    if ((i != 0) && (j != 0))
                        continue;

                    int weight = currentNode.getWeight() + 1;

                    col = j + (currentNodeCoor.getX() / Tile.getWidth() - surface.getBeginX());
                    row = i + (currentNodeCoor.getY() / Tile.getHeight() - surface.getBeginY());

                    if (col >= Matrix[0].length || row >= Matrix.length || col < 0 || row < 0)
                        continue;

                    adjacentNode = Matrix[row][col];

                    if (adjacentNode.getItem().isSolid())
                        continue;

                    if (!closedList.contains(adjacentNode)) {

                        if (adjacentNode.getWeight() > weight || adjacentNode.getWeight() == -1) {

                            adjacentNode.setWeight(weight);

                            adjacentNode.setHeuristic(this.heuristic.getHeuristicCost(this));

                            adjacentNode.setParentNode(currentNode);
                        }
                        openList.add(adjacentNode);
                    }
                }
            }
            openList.remove(currentNode);
            closedList.add(currentNode);
        }
        //++++++++++++++++++++++++++++++++++++++++  END   +++++++++++++++++++++++++++++++++++

        //If there, no result
        return null;
    }

    /**
     * <p>Réaligne les points pour empêcher une collision entre l'entité qui se déplace et une tuile interdite.</p>
     * @param mover l'entité se déplacant sous forme de rectangle.
     * @param map la carte.
     */
    private void adapt_path_forMover(Rectangle mover, IMap map)
    {
        Stack<Node_heuristic<Tile, T>> buffer = finalizePath();

        Stack<Coordinates> adjustedList = new Stack<>();

        Node_heuristic<Tile, T> currentNode;

        Coordinates next, current;

        int count = 0;

        while (!buffer.isEmpty() && count <= 3)
        {
            currentNode = buffer.pop();

            current = Rectangle.findMiddleCoor(currentNode.getItem().toRectangle());

            if (!buffer.isEmpty())
                next = Rectangle.findMiddleCoor(buffer.peek().getItem().toRectangle());
            else
                next = null;

            if (next != null)
                adjustCoordinates(current, next, mover, map);

            adjustedList.push(current);

            count++;
        }

        while (!adjustedList.isEmpty())
        {
            path.push(adjustedList.pop());
        }
    }

    /**
     * <p>Ajuste les coordonnées des points à la surface du rectangle mover</p>
     * Etudie les alignements des points et la présence de tuile solide dans les cases diagonales.
     * @param current noeud entrain d'être étudié.
     * @param next noeud suivant le noeud courant.
     * @param mover l'entité qui se déplace sous forme de rectangle.
     * @param map la carte.
     */
    private void adjustCoordinates(Coordinates current, Coordinates next,
                                   Rectangle mover, IMap map)
    {

    }

    /**
     * <p>Calcule si deux points sont alignés dans l'axe des Y</p>
     * @param coor1 premier point.
     * @param coor2 deuxième point.
     * @return true si les X sont égaux, false sinon.
     */
    private boolean isInSameYline(Coordinates coor1, Coordinates coor2)
    {
        return coor1.getX() == coor2.getX();
    }

    /**
     * <p>Calcule si deux points sont alignés dans l'axe des X</p>
     * @param coor1 premier point.
     * @param coor2 deuxième point.
     * @return true si les Y sont égaux, false sinon.
     */
    private boolean isInSameX_line(Coordinates coor1, Coordinates coor2)
    {
        return coor1.getY() == coor2.getY();
    }

    private boolean isInSameLine(Coordinates prec, Coordinates current, Coordinates next)
    {
        return (isInSameYline(prec, current) && isInSameYline(current, next) ||
                (isInSameX_line(prec, current) && isInSameX_line(current, next)));
    }

    /**
     * <p>Déroule la liste chaînée du but jusqu'au départ.</p>
     * Enregistre le tout dans une liste
     * @return La liste
     */
    private Stack<Node_heuristic<Tile, T>> finalizePath()
    {
        Stack<Node_heuristic<Tile, T>> list = new Stack<>();

        Node_heuristic<Tile, T> node = goal;

        while (node != null)
        {
            list.push(node);

            node = node.getParent();
        }

        return list;
    }

    /**
     * @see AbstractPathfinding_algo#clear()
     */
    @Override
    protected void clear()
    {
        path.clear();
        openList.clear();
        closedList.clear();
    }

    /**
     * <p>Créé un rectangle représenter la partie de la carte à copier (position et bornes).</p>
     * @see AbstractPathfinding_algo#createRectangle(Rectangle, Coordinates, IMap)
     * @param mover le rectangle qui représente l'entité se déplaçant.
     * @param goal le but.
     * @param map la carte à copier.
     */
    @Override
    protected void createRectangle(Rectangle mover, Coordinates goal, IMap map)
    {

        clear();

        start = getMoverStartCorner(mover, goal);

        //========Création du rectangle pour découper la carte=======
        int boundX, boundY;

        int x = Math.min(start.getX(), goal.getX()) / Tile.getWidth();
        int y = Math.min(start.getY(), goal.getY()) / Tile.getHeight();

        int width = Math.max(start.getX(), goal.getX()) / Tile.getWidth();
        int height = Math.max(start.getY(), goal.getY()) / Tile.getHeight();


        //Si rectangle trop petit, on l'agrandit pour trouver des chemins possibles
        if (width < 15) {
            width += 5;
            x -= 5;
        }
        if (height < 15) {
            height += 5;
            y -= 5;
        }

        boundX = x + width;
        boundY = y + height;

        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (boundX > map.getWidthInTile() - 1) boundX = map.getWidthInTile();
        if (boundY > map.getHeightInTile() - 1) boundY = map.getHeightInTile();

        surface = new Rectangle(x, boundX, y, boundY);
        //===============================================================

    }

    /**
     * <p>initialise les données avant de démarrer l'algorithme.</p>
     * utilise le champ surface pour le réajuster aux dimensions de l'array des tuiles
     * pour récupérer la partie de la map.
     * wrap l'ensemble des tuiles dans des noeuds pour former la matrice.
     * @param mover représente l'entité qui se déplace sous forme de rectangle
     * @param map la carte
     */
    private void setData(Rectangle mover, Coordinates goal, IMap map)
    {

        Tile[][] subMap = map.getsubMapInArray(surface);

        Matrix = new Node_heuristic[subMap.length][subMap[0].length];

        Node_heuristic<Tile, T> node = null;

        for (int i = 0; i < subMap.length; i++) {

            for (int j = 0; j < subMap[0].length; j++) {

                node = new Node_heuristic<>(subMap[i][j]);

                Matrix[i][j] = node;

                //Si la tuile contient les coordonnées de l'entité, elle est placé en départ
                if (subMap[i][j].contains(start)) {
                    node.setWeight(0);
                    openList.add(node);

                    //Si contient les coordonées du but, la tuile est l'arrivée
                } else if (subMap[i][j].contains(goal)) {
                    this.goal = node;
                }
            }
        }
    }
}
