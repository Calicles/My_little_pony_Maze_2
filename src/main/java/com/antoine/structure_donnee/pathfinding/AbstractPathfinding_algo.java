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

public abstract class AbstractPathfinding_algo implements IPathfinding {

    protected ArrayList<Node<Tile>> S;
    protected ArrayList<Node<Tile>> adjNodes;
    protected Stack<Node<Tile>> path;
    protected Node<Tile> start, goal, currentNode;

    public AbstractPathfinding_algo(){
        S = new ArrayList<>();
        adjNodes = new ArrayList<>();
        path = new Stack<>();

    }

    @Override
    public void init(Coordinates start, Coordinates goal, IMap map) {
        clear();

        int x, y, width, height;
        Node<Tile> node;

        x= Math.min(start.getX(), goal.getX());
        y= Math.min(start.getY(), goal.getY());

        width = Math.max(start.getX(), goal.getX()) - x;
        height = Math.max(start.getY(), goal.getY()) - y;

        List<Tile> subList = map.getSubMap(x, width, y, height);

        for (Tile t:subList){
            node = new Node<>(t);
            S.add( node );
            if (t.contains(start)) {
                node.setWeight(0);
                adjNodes.add(node);
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
    public abstract Coordinates getVector(Rectangle position, Rectangle goal);

    protected int getDistFromCurrentNode(Node<Tile> node){
        Tile t =  node.getItem();
        Tile current = currentNode.getItem();

        return Pythagore.calculDistance(t.toCoordinates(), current.toCoordinates());
    }

    protected void clear(){
        if(!S.isEmpty()){
            S.clear();
            path.clear();
        }
    }

}
