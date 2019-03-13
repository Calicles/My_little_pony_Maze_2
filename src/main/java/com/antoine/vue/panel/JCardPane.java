package com.antoine.vue.panel;

import javax.swing.*;
import java.awt.*;

public class JCardPane extends JPanel {

    private JPanel pane1, pane2;
    private String labelPane1, labelPane2;

    public JCardPane() {
        super(new CardLayout());

    }

    public void setLabelPane1(String labelPane1){
        this.labelPane1= labelPane1;
    }

    public void setLabelPane2(String labelPane2){
        this.labelPane2= labelPane2;
    }

    public void setPane1(JPanel pane1){
        this.pane1= pane1;
        CardLayout card= (CardLayout) this.getLayout();
        card.show(this, labelPane1);
    }

    public void setPane2(JPanel pane2){
        this.pane2= pane2;
    }

    public void switchPane2(){
        CardLayout card= (CardLayout) this.getLayout();
        card.show(this, labelPane1);
    }


}
