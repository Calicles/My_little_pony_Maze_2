package com.antoine.contracts;

import com.antoine.geometry.Rectangle;

import java.awt.*;

public interface ILevel {
    
    void selected();

    int getMapWidth();

    int getMapHeight();

    void deselected();

    boolean isSelected();

    boolean isRunning();

    boolean playerMovesLeft();

    boolean playerMovesRight();

    boolean playerMovesUp();

    boolean playerMovesDown();

    void playerMovesReleased();

    Dimension getDimension();

    void drawMiniMap(Graphics g, int echelle);

    int getScreenX();

    int getScreenY();

    int getScreenWidth();

    int getScreenHeight();

    int getPlayerX();

    int getPlayerY();

    IMap getMap();

    IEntity getPlayer();

    String getEndImageUrl();

    Rectangle getScreen();
}
