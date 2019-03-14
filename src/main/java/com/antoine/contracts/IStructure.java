package com.antoine.contracts;

import com.antoine.geometry.Rectangle;

public interface IStructure {

    void accept(IAfficheur visiteur);

    IMap getMap();

    IEntity getPlayer();

    boolean isRunning();

    String getEndImageUrl();

    Rectangle getScreen();
}
