package com.antoine.geometry;

/**
 * <b>Classe de service</b>
 * <p>utilise le théorème di pythagore</p>
 *
 * @author antoine
 */
public class Pythagore {


    /**
     * Calcule la distance entre deux points dans R2
     * @param position1 point 1
     * @param position2 point 2
     * @return la distance les séparant sous forme d'entier
     */
    public static int calculDistance(Coordinates position1, Coordinates position2){
        return (int) Math.sqrt(square(position1.getX() - position2.getX()) + square(position1.getY() - position2.getY()));
    }

    /**
     * <p>Elève au carré</p>
     * @param nbr entier
     * @return le carré de nbr sous frome d'entier
     */
    private static int square(int nbr){
        return (int) Math.pow(nbr, 2);
    }

}
