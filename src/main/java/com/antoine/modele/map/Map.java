package com.antoine.modele.map;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import com.antoine.contracts.IMap;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;

public class Map extends AbstractTileMap implements IMap {
	
	public Map(HashMap<Integer, BufferedImage> tileSet, int[][] map) throws IOException {
		super(tileSet, map);
	}

	public Map(){
		super();
	}
}
