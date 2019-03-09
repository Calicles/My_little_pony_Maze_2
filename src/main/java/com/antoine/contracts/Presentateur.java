package com.antoine.contracts;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface Presentateur {


    void playerMoves(int xVector, int yVector) throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;

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
