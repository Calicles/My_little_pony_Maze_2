package com.antoine.structure_donnee.pathfinding;

import com.antoine.contracts.IMap;
import com.antoine.contracts.IPathfinding;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;

import java.util.Stack;

public class A_impl extends AbstractPathfinding_algo implements IPathfinding {


    @Override
    protected void selectNextNode() {

    }

    @Override
    protected void fillAdjGraph() {

    }

    @Override
    public Stack<Coordinates> createPath(Coordinates start, Coordinates goal, IMap map) {
        return null;
    }

    @Override
    public Stack<Coordinates> getPath() {
        return null;
    }
}
