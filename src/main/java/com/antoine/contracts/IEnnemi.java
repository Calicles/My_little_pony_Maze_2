package com.antoine.contracts;

import com.antoine.geometry.Rectangle;

public interface IEnnemi extends IEntity {

    void think();

    void memorizeMoves();

    void setAttributes(Rectangle toRectangle, Rectangle toRectangle1, IMap map);

    void startThinking();
}
