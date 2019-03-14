package com.antoine.contracts;

import com.antoine.geometry.Rectangle;

public interface IEnnemi extends IEntity {

    void think(Rectangle playerPosition);

    void memorizeMozes();
}
