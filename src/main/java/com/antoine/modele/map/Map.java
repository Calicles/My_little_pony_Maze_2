package com.antoine.modele.map;

import com.antoine.contracts.IMap;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Map extends AbstractTileMap implements IMap {
	

	public Map(){
		super();
	}

	@Override
	public List<Tile> getSubMap(Rectangle surface) {

		Predicate<Tile> predicate = t -> {

			boolean inXBounds = ((t.getX() > surface.getBeginX()) && (t.getX() < surface.getEndX())) ||
					((t.getX() + tile_width > surface.getBeginX()) && (t.getX() + tile_width < surface.getEndX()));

			boolean inYBounds = ((t.getY() > surface.getBeginY()) && (t.getY() < surface.getEndY())) ||
					((t.getY() + tile_height > surface.getBeginY()) && (t.getY() + tile_height < surface.getEndY()));

			return inXBounds && inYBounds;
		};

		List<Tile> tilesList= new ArrayList<>();

		for (Tile[] tab: map){

			List<Tile> buffer = Arrays.stream(tab)

					.filter(t-> predicate.test(t))

					.collect(Collectors.toList());

			tilesList.addAll(buffer);
		}

		return tilesList;
	}
}
