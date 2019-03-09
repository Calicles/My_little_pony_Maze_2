package com.antoine.services;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class Character_reader {
	
	public static HashMap<Integer, BufferedImage[]> readCharactereAnimation(String url){
		HashMap<Integer, BufferedImage[]> map= new HashMap<>();
		
		try(BufferedReader reader= new BufferedReader(
				new FileReader(new File(url)))) {
					
			String[] bounds= reader.readLine().split(" ");
			String line;
			int directionNumber= Integer.parseInt(bounds[0]);
			int imagePerDirection= Integer.parseInt(bounds[1]);
			BufferedImage[] tab= null;
			
			for(int i= 0; i < directionNumber; i++) {
				
				tab= new BufferedImage[imagePerDirection];
				
				for(int j= 0; j < imagePerDirection; j++) {
					line= reader.readLine();
					tab[j]= ImageReader.lireImage(line);
				}
				
				map.put(i, tab);
			}

			
		}catch(Throwable t) {
			t.printStackTrace();
			throw new RuntimeException("Erreur de Lecture d'image animation");
		}

		return map;
	}

}
