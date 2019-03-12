package com.antoine.vue.panel;

import javax.swing.*;
import java.awt.*;

public class JCardPane extends JPanel {

    private JPanel pane1, pane2;
    private String labelPane1, labelPane2;

    public JCardPane(){
        super(new CardLayout());
    }

    public void setPane1(JPanel pane1){
        this.pane1= pane1;
    }


}
