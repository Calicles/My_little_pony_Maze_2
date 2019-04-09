package com.antoine.events;


import com.antoine.structure_donnee.LevelState;

import java.util.Arrays;

public class LevelChangeEvent {

    private int booleanTable = 0;

    private int numberOfLevelFinshed;

    public LevelChangeEvent(){
        numberOfLevelFinshed = 0;
    }

    public void setBooleanTable(LevelState state, boolean value){
        if (value)
            booleanTable |= (1 << state.ordinal());
        else {
            if (state.ordinal() < 6 && valueOf(state))
              numberOfLevelFinshed++;
            booleanTable &= ~(1 << state.ordinal());
        }
    }

    public boolean valueOf(LevelState state){
        return (0x1 & (booleanTable >> state.ordinal())) == 1 ? true : false;
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
