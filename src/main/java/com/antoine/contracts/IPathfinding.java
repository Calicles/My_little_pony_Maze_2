package com.antoine.contracts;

import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;
import com.antoine.structure_donnee.pathfinding.Node_heuristic;

import java.util.Stack;

public interface IPathfinding<T> {

    Stack<Coordinates> createPath(Rectangle mover, Coordinates goal, IMap map);

    //TODO Remove after test
    Stack<Coordinates> getPath();

    Node_heuristic<Tile, T> getAdjacentNode();

    Node_heuristic<Tile, T> getGoalNode();

    void setHeuristic(IHeuristic<T> heuristic);
}
