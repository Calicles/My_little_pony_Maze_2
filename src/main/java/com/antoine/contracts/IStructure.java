package com.antoine.contracts;

import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;

import java.util.Stack;

public interface IStructure {

    void accept(IAfficheur visiteur);

    IMap getMap();

    IEntity getPlayer();

    boolean isRunning();

    String getEndImageUrl();

    Rectangle getScreen();

    IEntity getBoss();

    int getMapHeight();

    int getMapWidth();

    Stack<Coordinates> getPath();
}
