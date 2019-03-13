package com.antoine.services;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * <p>Classe de service pour créer des BufferedImage.</p>
 * @author antoine
 */
public class ImageReader {

    /**
     * Importe le fichier sous forme de BufferedImage.
     * @param imageUrl url du fichier
     * @return une BufferedImage
     * @throws RuntimeException
     */
    public static BufferedImage lireImage(String imageUrl) {
        try {
            return ImageIO.read(new File(imageUrl));
        }catch (IOException ioe){
            throw new RuntimeException("Erreur de lecture de l'image");
        }
    }
}
