package com.antoine.contracts;

import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;
import com.antoine.structure_donnee.Node;

import java.util.Stack;

public interface IPathfinding {

    Stack<Coordinates> createPath(Coordinates start, Coordinates goal, IMap map);

    //TODO Remove after test
    Stack<Coordinates> getPath();
}
