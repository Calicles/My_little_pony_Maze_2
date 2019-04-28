package com.antoine.contracts;

import com.antoine.events.LevelChangeEvent;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;

import java.awt.*;
import java.util.List;
import java.util.Stack;

public interface ILevel {
    
    void selected();

    int getMapWidth();

    int getMapHeight();

    void deselected();

    boolean isSelected();

    boolean isRunning();

    void playerMovesLeft();

    void playerMovesRight();

    void playerMovesUp();

    void playerMovesDown();

    void playerMovesReleased();

    Dimension getDimension();

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

    void setListeners(List<LevelListener> listeners);

    void start();

    IEntity getBoss();

    void setEvent(LevelChangeEvent event);

    int getId();

    //TODO Remove after Test
    Stack<Coordinates> getPath();
}
