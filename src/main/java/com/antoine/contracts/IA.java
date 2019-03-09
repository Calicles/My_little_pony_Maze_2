package com.antoine.contracts;

import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;

public interface IA {
    void setAttributes(Rectangle position, Rectangle player1, IMap map);

    Coordinates memorizMoves();

    void think(Rectangle position, Coordinates player);
}
