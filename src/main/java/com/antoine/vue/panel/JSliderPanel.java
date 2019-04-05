package com.antoine.vue.panel;

import com.antoine.contracts.Presentateur;
import com.antoine.manager.musique.Jukebox;
import com.antoine.services.ImageReader;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public  abstract class JSliderPanel extends JSlider {

    public JSliderPanel(Presentateur presentateur, int minValue, int maxValue, boolean tickPaintable){
        super(minValue, maxValue);
        Jukebox jukebox = presentateur.getJukebox();
        setListener(jukebox);
        setBackground(Color.PINK);
        super.setMajorTickSpacing(10);
        super.setMinorTickSpacing(1);

        setUI(new mySliderUI(this));

        super.setPaintTicks(tickPaintable);
        super.setPaintLabels(tickPaintable);
        super.setFocusable(false);
    }

    protected abstract void setListener(Jukebox jukebox);

    @Override
    public Dimension getPreferredSize(){
        return super.getPreferredSize();
    }

    private class mySliderUI extends BasicSliderUI {

        Image knobImage;

        public mySliderUI( JSlider aSlider ) {

            super( aSlider );

                this.knobImage = ImageReader.lireImage("/ressources/images/slide/lunaSlide.png");


        }
        public void paintThumb(Graphics g)  {

            g.drawImage( this.knobImage, thumbRect.x, thumbRect.y, 8, 8, null );

        }

    }
}
