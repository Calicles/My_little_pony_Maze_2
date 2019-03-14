package com.antoine.contracts;

import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;

public interface ITransfert_strategy {

    void released();

    void movesLeft();

    void movesRight();

    void movesUp();

    void movesDown();

    Coordinates memorizeMoves(Rectangle position, IMap map);

    Coordinates memorizMoves();

    void think(Rectangle position, Rectangle player);
}
