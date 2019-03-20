package com.antoine.geometry;

public class Pythagore {

    public static int calculDistance(Rectangle position1, Rectangle position2){
        return (int) Math.sqrt(add(position1, position2));
    }

    public static int calculDistance(Coordinates position1, Coordinates position2){
        return (int) Math.sqrt(square(position1.getX() - position2.getX()) + square(position1.getY() - position2.getY()));
    }

    private static int square(int nbr){
        return (int) Math.pow(nbr, 2);
    }

    private static int add(Rectangle position1, Rectangle position2){
        return (squareX(position1, position2) + squareY(position1, position2));
    }

    public static int squareX(Rectangle position1, Rectangle position2){
        return (square(Rectangle.findMiddleX(position1) - Rectangle.findMiddleX(position2)));
    }

    public static int squareY(Rectangle position1, Rectangle position2){
        return (square(Rectangle.findMiddleY(position1) - Rectangle.findMiddleY(position2)));
    }

}
