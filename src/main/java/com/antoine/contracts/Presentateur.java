package com.antoine.contracts;

import java.awt.*;
import java.io.IOException;

public interface Presentateur {


    void playerMoves(int xVector, int yVector) throws IOException;

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
}
