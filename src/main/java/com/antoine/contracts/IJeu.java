package com.antoine.contracts;

import com.antoine.geometry.Rectangle;

import java.awt.*;

public interface IJeu {

    int getMapWidth();

    int getMapHeight();

    void switchLeveApple();

    void switchLevelRarity();

    void switchLevelRainbow();

    boolean isAppleSelectedAndRunning();

    boolean isRaritySelectedAndRunning();

    boolean isRainbowSelectedAndRunning();

    void playerMovesLeft();

    void playerMovesRight();

    void playerMovesUp();

    void playerMovesDown();

    void playerMovesReleased();

    Dimension getDimension();

    void AddListener(LevelListener listener);

    void removeListener(LevelListener listener);

    boolean isLevelsNull();

    boolean isLevelFlutterNull();

    boolean isLevelPinkyNull();

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
}
