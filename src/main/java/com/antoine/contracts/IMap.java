package com.antoine.contracts;

import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public interface IMap {


    int[][] getTiles();

    int getTile_width();

    int getTile_height();

    Tile findExit();

    int[] getDimension();

    void drawMap(Graphics g);

    int getWidth();

    int getHeight();

    Tile[][] getMap();

    HashMap<Integer, BufferedImage> getTileSet();
}
