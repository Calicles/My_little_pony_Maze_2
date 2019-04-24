package com.antoine.contracts;

import com.antoine.geometry.Coordinates;
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

    List<Tile> getSubMap(Rectangle surface);

    Tile[][] getsubMapInArray(Rectangle surface);

    Coordinates getCoorinatesInTile(Coordinates current);

    boolean isSolideTile(int i, int j);
}
