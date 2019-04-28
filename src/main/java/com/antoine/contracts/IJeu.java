package com.antoine.contracts;

import com.antoine.geometry.Rectangle;
import com.antoine.manager.musique.Jukebox;

import java.awt.*;

public interface IJeu {

    int getMapWidth();

    int getMapHeight();

    void switchLeveApple();

    void switchLevelRarity();

    void switchLevelRainbow();

    void playerMovesLeft();

    void playerMovesRight();

    void playerMovesUp();

    void playerMovesDown();

    void playerMovesReleased();

    Dimension getDimension();

    void addListener(LevelListener listener);

    void removeListener(LevelListener listener);

    int getScreenX();

    int getScreenY();

    int getScreenWidth();

    int getScreenHeight();

    int getPlayerX();

    int getPlayerY();

    void accept(IAfficheur visiteur);

    IMap getMap();

    IEntity getPlayer();

    boolean isRunning();

    String getEndImageUrl();

    Rectangle getScreen();

    IEntity getBoss();

    Jukebox getJukebox();
}
