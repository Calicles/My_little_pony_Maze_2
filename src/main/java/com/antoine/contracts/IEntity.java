package com.antoine.contracts;

import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;

import java.awt.image.BufferedImage;

public interface IEntity {
    void movesLeft();

    void movesRight();

    void movesUp();

    void movesDown();

    Coordinates memorizeMoves(IMap map);

    BufferedImage getImage();

    int getX();

    int getY();

    void movesReleased();

    int getWidth();

    int getHeight();

    Rectangle toRectangle();

    void translate(Coordinates verector);

    void setPosition(Coordinates newPosition);

    Rectangle getPosition();

    void translateTo(Coordinates startBossPosition);
}
