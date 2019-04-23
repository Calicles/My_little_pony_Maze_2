package com.antoine.structure_donnee.pathfinding;

import com.antoine.contracts.IMap;
import com.antoine.geometry.Coordinates;
import com.antoine.structure_donnee.Node;

import java.util.Comparator;
import java.util.Stack;
import java.util.TreeSet;

public class A_star_impl extends AbstractPathfinding_algo {

    @Override
    protected void createOpenList() {

        openList = new TreeSet<>(Comparator.comparingInt(Node::getHeuristic));
    }

    @Override
    protected void selectNextNode() {

    }

    @Override
    protected void fillAdjGraph() {

    }

    @Override
    protected Stack<Coordinates> createPath(Coordinates start, Coordinates goal, IMap map) {
        return null;
    }
}
