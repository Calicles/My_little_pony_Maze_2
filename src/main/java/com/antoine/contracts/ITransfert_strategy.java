package com.antoine.contracts;

import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;

import java.util.Stack;

public interface ITransfert_strategy {

    void released();

    void movesLeft();

    void movesRight();

    void movesUp();

    void movesDown();

    Coordinates memorizeMoves(Rectangle position, IMap map);

    Coordinates memorizeMoves();

    void think();

    void setAttributes(Rectangle ownPosition, Rectangle palyer1, IMap map);

    void startThinking();

    //TODO Remove after Test
    Stack<Coordinates> getPath();
}
