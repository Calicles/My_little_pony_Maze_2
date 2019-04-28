package com.antoine.contracts;

import com.antoine.manager.musique.Jukebox;

import java.awt.*;

public interface Presentateur {

    void playerMovesLeft();

    void playerMovesRight();

    void playerMovesUp();

    void playerMovesDown();

    Dimension getDimension();

    void AddListener(LevelListener listener);

    void switchLeveApple();

    void switchLevelRarity();

    void switchLevelRainbow();

    void playerMovesReleased();

    int getMapHeight();

    void accept(IAfficheur afficheur);

    Jukebox getJukebox();
}
