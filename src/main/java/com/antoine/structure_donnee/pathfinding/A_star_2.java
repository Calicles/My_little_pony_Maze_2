package com.antoine.structure_donnee.pathfinding;

import com.antoine.contracts.IHeuristic;
import com.antoine.contracts.IMap;
import com.antoine.contracts.IPathfinding;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;
import com.antoine.structure_donnee.Node;
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
        super.createRectangle(mover, goal, map);


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


        return path;
    }

    /**
     * <p>Réaligne les points pour empêcher une collision entre l'entité qui se déplace et une tuile interdite.</p>
     * @param mover l'entité se déplacant sous forme de rectangle.
     * @param map la carte.
     */
    private void adapt_path_forMover(Rectangle mover, IMap map)
    {
        LinkedList<Node_heuristic<Tile, T>> buffer = finalizePath();

        LinkedList<Coordinates> adjustedList = new LinkedList<>();

        Node_heuristic<Tile, T> currentNode;

        Coordinates prec, next, current;

        while (!buffer.isEmpty())
        {
            currentNode = buffer.pop();

            current = Rectangle.findMiddleCoor(currentNode.getItem().toRectangle());

            if(currentNode.getParent() != null)
                prec = Rectangle.findMiddleCoor(currentNode.getParent().getItem().toRectangle());
            else
                prec = null;

            if (!buffer.isEmpty())
                next = Rectangle.findMiddleCoor(buffer.getFirst().getItem().toRectangle());
            else
                next = null;

            if (next != null)
                adjustCoordinates(prec, current, next, mover, map);

            adjustedList.push(current);
        }

        while (!adjustedList.isEmpty())
        {
            path.push(adjustedList.pop());
        }
    }

    /**
     * <p>Ajuste les coordonnées des points à la surface du rectangle mover</p>
     * Etudie les alignements des points et la présence de tuile solide dans les cases diagonales.
     * @param prec noeud précédent le noeud courant.
     * @param current noeud entrain d'être étudié.
     * @param next noeud suivant le noeud courant.
     * @param mover l'entité qui se déplace sous forme de rectangle.
     * @param map la carte.
     */
    private void adjustCoordinates(Coordinates prec, Coordinates current, Coordinates next,
                                   Rectangle mover, IMap map)
    {
        Coordinates coorInTile = map.getCoorinatesInTile(current);

        if (isInSameYline(current, next))
        {
            if (current.getY() > next.getY())
            {
                //Tile solide diagonale haut gauche
                if (map.isSolideTile(coorInTile.getX() - 1, coorInTile.getY() - 1))
                {
                    if ((!map.isSolideTile(coorInTile.getX() + 1, coorInTile.getY()) && ((!map.isSolideTile(coorInTile.getX() - 1, coorInTile.getY())))))
                    {
                        if (prec.getX() < current.getX())
                            current.setCoordinates(current.getX() + mover.getWidth(), current.getY());
                    }

                } //Tile solide diagonale haut droite
                else if (map.isSolideTile(coorInTile.getX() + 1, coorInTile.getY() - 1))
                {
                    if ((!map.isSolideTile(coorInTile.getX() + 1, coorInTile.getY()) && ((!map.isSolideTile(coorInTile.getX() - 1, coorInTile.getY())))))
                    {
                        if (prec.getX() > current.getX())
                            current.setCoordinates(current.getX() - mover.getWidth(), current.getY());
                    }
                }
            }
            else
            {
                if (map.isSolideTile(coorInTile.getX() - 1, coorInTile.getY() + 1))
                {
                    if ((!map.isSolideTile(coorInTile.getX() + 1, coorInTile.getY()) && ((!map.isSolideTile(coorInTile.getX() - 1, coorInTile.getY())))))
                    {
                        if (prec.getX() < current.getX())
                            current.setCoordinates(current.getX() + mover.getWidth(), current.getY());
                    }
                }
                else if (map.isSolideTile(coorInTile.getX() + 1, coorInTile.getY() + 1))
                {
                    if ((!map.isSolideTile(coorInTile.getX() + 1, coorInTile.getY()) && ((!map.isSolideTile(coorInTile.getX() - 1, coorInTile.getY())))))
                    {
                        if ((prec.getX() > current.getX()))
                            current.setCoordinates(current.getX() - mover.getWidth(), current.getY());
                    }
                }
            }
        }
        else if (isInSameX_line(current, next))
        {
            if (current.getX() < next.getX())
            {
                //TODO check case diagonales à droite haut et bas
                if (map.isSolideTile(coorInTile.getX() + 1, coorInTile.getY() + 1))
                {
                    if ((!map.isSolideTile(coorInTile.getX(), coorInTile.getY() + 1) && (!map.isSolideTile(coorInTile.getX(), coorInTile.getY() - 1)))) {
                        if (prec.getY() < current.getY())
                            current.setCoordinates(current.getX(), current.getY() - mover.getHeight());
                    }
                }
                else if (map.isSolideTile(coorInTile.getX() + 1, coorInTile.getY() - 1))
                {
                    if ((!map.isSolideTile(coorInTile.getX(), coorInTile.getY() + 1) && (!map.isSolideTile(coorInTile.getX(), coorInTile.getY() - 1)))) {
                        if (prec.getY() > current.getY())
                            current.setCoordinates(current.getX(), current.getY() + mover.getHeight());
                    }
                }
            }
            else
            {
                //TODO check case diago gauche haut et bas
                if (map.isSolideTile(coorInTile.getX() - 1, coorInTile.getY() - 1))
                {
                    if ((!map.isSolideTile(coorInTile.getX(), coorInTile.getY() + 1) && (!map.isSolideTile(coorInTile.getX(), coorInTile.getY() - 1)))) {
                        if (prec.getY() > prec.getY())
                           current.setCoordinates(current.getX(), current.getY() + mover.getHeight());
                    }
                }
                else if (map.isSolideTile(coorInTile.getX() - 1, coorInTile.getY() + 1))
                {
                    if ((!map.isSolideTile(coorInTile.getX(), coorInTile.getY() + 1) && (!map.isSolideTile(coorInTile.getX(), coorInTile.getY() - 1)))) {
                        if (prec.getY() < current.getY())
                            current.setCoordinates(current.getX(), current.getY() - mover.getHeight());
                    }
                }
            }
        }

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
    private LinkedList<Node_heuristic<Tile, T>> finalizePath()
    {
        LinkedList<Node_heuristic<Tile, T>> list = new LinkedList<>();

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
     * <p>initialise les données avant de démarrer l'algorithme.</p>
     * utilise le champ surface pour le réajuster aux dimensions de l'array des tuiles
     * pour récupérer la partie de la map.
     * wrap l'ensemble des tuiles dans des noeuds pour former la matrice.
     * @param mover représente l'entité qui se déplace sous forme de rectangle
     * @param map la carte
     */
    private void setData(Rectangle mover, Coordinates goal, IMap map)
    {
        int x, y, endx, endy;

        //=================   Ajust the rectangle to the map   ============

        x = surface.getBeginX() / Tile.getWidth();
        y = surface.getBeginY() / Tile.getHeight();
        endx = surface.getEndX() / Tile.getWidth();
        endy = surface.getEndY() / Tile.getHeight();

        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (endx > map.getWidth()) endx = map.getWidthInTile();
        if (endy > map.getHeight()) endy = map.getHeightInTile();

        surface = new Rectangle(x, endx, y, endy);
        //===============================================================


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
