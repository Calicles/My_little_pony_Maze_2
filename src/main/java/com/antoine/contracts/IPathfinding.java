package com.antoine.contracts;

import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;

public interface IPathfinding {

    void init(Coordinates goal, IMap map);

    Coordinates getNextStep();

    void setEntity(Rectangle ownPosition);
}
