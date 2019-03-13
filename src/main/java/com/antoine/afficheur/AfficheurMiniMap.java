package com.antoine.afficheur;

import com.antoine.contracts.IMap;
import com.antoine.contracts.IStructure;
import com.antoine.contracts.IAfficheur;
import com.antoine.geometry.Tile;
import com.antoine.vue.panel.JMiniMap;

import java.awt.*;

public class AfficheurMiniMap extends AbstractAfficheur implements IAfficheur {


    @Override
    public void visit(IStructure structure) {
        drawMiniMap(structure);
    }

    public void drawMiniMap(IStructure structure) {
        IMap mapStruct= structure.getMap();
        int ECHELLE= JMiniMap.ECHELLE;
        Color old= g.getColor();
        Tile[][] map= mapStruct.getMap();
        int x, y;
        int width= mapStruct.getTile_width() / ECHELLE;
        int height= mapStruct.getTile_height() / ECHELLE;
        g.setColor(Color.ORANGE);
        for(int i=0; i<map.length; i++) {
            for(int j=0;j<map[0].length;j++) {
                x= map[i][j].getX() / ECHELLE;
                y= map[i][j].getY() / ECHELLE;

                if(map[i][j].getTile_num() > Tile.getSolidNum()) {
                    g.fillRect(x, y, width, height);
                }
            }
        }
        g.setColor(old);
    }
}
