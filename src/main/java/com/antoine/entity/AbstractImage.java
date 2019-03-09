package com.antoine.entity;

import com.antoine.services.ImageReader;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Classe gérant une image et ses dimensions.
 * @author antoine
 */
public abstract class AbstractImage {

	/**
	 * L'image pour affichage
	 */
	protected BufferedImage image;

	public void setImage(String imageUrl) throws IOException {
		image= ImageReader.lireImage(imageUrl);
	}

	public int getWidth() {return image.getWidth();}
	public int getHeight() {return image.getHeight();}
	public BufferedImage getImage() {return image;}

}
