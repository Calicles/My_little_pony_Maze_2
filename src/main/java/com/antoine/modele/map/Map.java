package com.antoine.modele.map;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import com.antoine.contracts.IMap;

public class Map extends AbstractTileMap implements IMap {
	
	public Map(HashMap<Integer, BufferedImage> tileSet, int[][] map) throws IOException {
		super(tileSet, map);
	}

	public Map(){
		super();
	}


	@Override
	public int[][] getTiles() {
		return new int[0][];
	}

	@Override
	public int getWidth() {
		return 0;
	}

	@Override
	public int getHeight() {
		return 0;
	}
}
