package com.antoine.events;


import java.util.Arrays;

public class LevelChangeEvent {

    private int booleanTable = 0;

    private int numberOfLevelFinshed;

    public static final int LEVEL1_RUNNING = 0;
    public static final int LEVEL2_RUNNING = 1;
    public static final int LEVEL3_RUNNING = 2;
    public static final int LEVEL4_RUNNING = 3;
    public static final int LEVEL5_RUNNING = 4;
    public static final int LEVEL6_RUNNING = 5;
    public static final int LEVEL1_SELECTED = 6;
    public static final int LEVEL2_SELECTED = 7;
    public static final int LEVEL3_SELECTED = 8;

    public LevelChangeEvent(){
        numberOfLevelFinshed = 0;
    }

    public void setBooleanTable(int b, boolean value){
        if (value)
            booleanTable |= (1<<b);
        else {
            if (b < 6 && valueOf(b))
              numberOfLevelFinshed++;
            booleanTable &= ~(1 << b);
        }
    }

    public boolean valueOf(int b){
        return (0x1 & (booleanTable >> b)) == 1 ? true : false;
    }

    public int getNumber_level_over(){
        return numberOfLevelFinshed;
    }

    @Override
    public String toString(){
        byte[] bytes = new byte[4];


        for (int i= 0; i < bytes.length; i++){
            bytes[i] = (byte) (0xFF & (booleanTable >> 8 * i));
        }

        return Arrays.toString(bytes);
    }


}
