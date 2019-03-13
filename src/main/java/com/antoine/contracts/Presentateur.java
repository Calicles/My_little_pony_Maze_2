package com.antoine.contracts;

import java.awt.*;

public interface Presentateur {

    void playerMovesLeft();

    void playerMovesRight();

    void playerMovesUp();

    void playerMovesDown();

    Dimension getDimension();

    void AddListener(LevelListener listener);

    boolean isLevelsNull();

    boolean isAppleSelectedAndRunning();

    boolean isRainbowSelectedAndRunning();

    boolean isRaritySelectedAndRunning();

    void switchLeveApple();

    void switchLevelRarity();

    void switchLevelRainbow();

    boolean isLevelFlutterNull();

    boolean isLevelPinkyNull();

    void playerMovesReleased();

    int getMapHeight();

    int getScreenX();

    int getScreenY();

    int getScreenWidth();

    int getScreenHeight();

    int getPlayerX();

    int getPlayerY();

    int getMapWidth();

    void accept(IVisiteur visiteur);
}
