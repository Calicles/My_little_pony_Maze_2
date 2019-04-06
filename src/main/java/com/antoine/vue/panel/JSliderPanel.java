package com.antoine.vue.panel;

import com.antoine.manager.musique.Jukebox;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public  class JSliderPanel extends JSlider {

    Icon knobImage;

    public JSliderPanel(Jukebox jukebox, String iconPath, int minValue, int maxValue, boolean tickPaintable){
        super(SwingConstants.VERTICAL, minValue, maxValue, (maxValue / 2));
        setBackground(Color.PINK);
        super.setMajorTickSpacing(10);
        super.setMinorTickSpacing(1);

        knobImage = new ImageIcon(getClass().getResource(iconPath));
        setUI(new ThumbIconSliderUI(this));

        super.setPaintTicks(tickPaintable);
        super.setPaintLabels(tickPaintable);
        super.setPaintTrack(true);
        super.setFocusable(false);
    }

    @Override
    public Dimension getPreferredSize(){

        return super.getPreferredSize();
    }



    private class ThumbIconSliderUI extends BasicSliderUI {


        public ThumbIconSliderUI( JSlider aSlider ) {

            super( aSlider );

        }

        @Override
        public void paintThumb(Graphics g)  {
            Rectangle knobRect = thumbRect;
            g.translate(knobRect.x, knobRect.y);

            knobImage.paintIcon(slider, g, 0, 0);

            g.translate(-knobRect.x, -knobRect.y);
        }

        @Override
        protected Dimension getThumbSize(){

            return new Dimension(knobImage.getIconWidth(), knobImage.getIconHeight());
        }

    }
}
