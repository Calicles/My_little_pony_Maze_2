package com.antoine.modele.map;

import com.antoine.contracts.IMap;
import com.antoine.geometry.Tile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Map extends AbstractTileMap implements IMap {
	

	public Map(){
		super();
	}

	@Override
	public List<Tile> getSubMap(int x, int width, int y, int height) {

		List<Tile> tilesList= null;

		for (Tile[] tab:map){

			List<Tile> buffer = null;

			Stream<Tile> stream = (Stream<Tile>) Arrays.stream(tab);
			buffer = stream.filter(t->{
				boolean inXBounds = t.getX() >= x && t.getX() <= (x + width);
				boolean inYBounds = t.getY() >= y && t.getY() <= (y + height);

				return inXBounds && inYBounds;
			}).collect(Collectors.toList());

			tilesList.addAll(buffer);
		}

		return tilesList;
	}
}
