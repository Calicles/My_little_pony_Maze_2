package com.antoine.contracts;

import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;
import com.antoine.structure_donnee.Node;

import java.util.Stack;

public interface IEnnemi extends IEntity {

    void think();

    void memorizeMoves();

    void setAttributes(Rectangle toRectangle1, IMap map);

    void startThinking();

    //TODO Remove after Test
    Stack<Coordinates> getPath();
}
