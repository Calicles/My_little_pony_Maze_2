package com.antoine.vue.panel;

import com.antoine.contracts.LevelListener;
import com.antoine.events.LevelChangeEvent;

import javax.swing.*;
import java.awt.*;

public class JCardPane extends JPanel implements LevelListener {

    private JPanel pane1, pane2, container;
    private String label1, labelPane2;

    public JCardPane(String label1, JPanel panel1, String label2, JPanel pane2) {
        super(new BorderLayout());

        this.pane1 = panel1;
        this.pane2 = pane2;
        this.label1 = label1;
        pane2.setPreferredSize(panel1.getPreferredSize());
        this.labelPane2 = label2;
        container = new JPanel(new CardLayout());
        container.add(label1, panel1);
        container.add(label2, pane2);
        container.setBorder(BorderFactory.createRaisedBevelBorder());
        this.add(container, BorderLayout.CENTER);
    }

    @Override
    public void update(LevelChangeEvent lve){
        if (!isFirstsLevelsRunning(lve)){
            switchPane2();
        }
        repaint();
    }

    private boolean isFirstsLevelsRunning(LevelChangeEvent lve) {
        return (lve.valueOf(LevelChangeEvent.LEVEL1_RUNNING) || lve.valueOf(LevelChangeEvent.LEVEL2_RUNNING) ||
                lve.valueOf(LevelChangeEvent.LEVEL3_RUNNING));
    }

    @Override
    public Dimension getPreferredSize(){
        return pane1.getPreferredSize();
    }

    public void switchPane2(){
        CardLayout card= (CardLayout) container.getLayout();
        card.show(container, labelPane2);
    }


}
