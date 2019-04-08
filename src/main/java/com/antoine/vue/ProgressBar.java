package com.antoine.vue;

import com.antoine.contracts.LevelListener;
import com.antoine.events.LevelChangeEvent;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

public class ProgressBar extends JProgressBar implements LevelListener {


    public ProgressBar(int minimum, int maximum){
        this.setBorderPainted(true);
        this.setStringPainted(true);
        this.setMinimum(minimum);
        this.setMaximum(maximum);
        this.setBackground(Color.PINK);
        this.setForeground(Color.RED);
    }

    @Override
    public void update(LevelChangeEvent lve) {
        setValue(lve.getNumber_level_over());
    }

    private class InnerBarUI extends BasicProgressBarUI {

        private InnerBarUI (JProgressBar aBar){
            progressBar = aBar;
        }
    }
}
