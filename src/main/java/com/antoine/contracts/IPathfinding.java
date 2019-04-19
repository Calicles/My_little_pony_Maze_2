package com.antoine.contracts;

import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;

public interface IPathfinding {

    void init(Coordinates start, Coordinates goal, IMap map);

    Coordinates getVector(Rectangle position, Rectangle goal);
}
