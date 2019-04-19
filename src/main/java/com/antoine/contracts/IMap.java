package com.antoine.contracts;

import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

public interface IMap {


    int getTile_width();

    int getTile_height();

    Tile findExit();

    int[] getDimension();

    int getWidth();

    int getHeight();

    Tile[][] getMap();

    HashMap<Integer, BufferedImage> getTileSet();

    Tile isSolidTileOnRoad(Rectangle rectangle);

    List<Tile> getSubMap(int x, int width, int y, int height);
}
