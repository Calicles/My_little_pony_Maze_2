package com.antoine.contracts;

import java.awt.*;
import java.io.IOException;

public interface Presentateur {

    void playerMovesLeft();

    void playerMovesRight();

    void playerMovesUp();

    void playerMovesDown();

    Dimension getDimension();

    void AddListener(LevelListener listener);

    void draw(Graphics g) throws IOException;

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

    void drawMiniMap(Graphics g, int echelle);
}
