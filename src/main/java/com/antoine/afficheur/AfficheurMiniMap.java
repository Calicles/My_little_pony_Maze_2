package com.antoine.afficheur;

import com.antoine.contracts.IMap;
import com.antoine.contracts.IStructure;
import com.antoine.contracts.IVisiteur;
import com.antoine.geometry.Tile;
import com.antoine.vue.panel.JMiniMap;

import java.awt.*;

public class AfficheurMiniMap extends AbstractAfficheur implements IVisiteur{


    @Override
    public void visit(IStructure structure) {
        drawMiniMap(structure);
    }

    public void drawMiniMap(IStructure structure) {
        IMap mapStruct= structure.getMap();
        int ECHELLE= JMiniMap.ECHELLE;
        Color old= g.getColor();
        Tile[][] map= mapStruct.getMap();
        int num= 0, x, y;
        int width= mapStruct.getTile_width() / ECHELLE;
        int height= mapStruct.getTile_height() / ECHELLE;
        g.setColor(Color.ORANGE);
        for(int i=0; i<map.length; i++) {
            for(int j=0;j<map[0].length;j++) {
                num= map[i][j].getTile_num();
                x= map[i][j].getX() / ECHELLE;
                y= map[i][j].getY() / ECHELLE;
                if(num > Tile.SOLID) {
                    g.fillRect(x, y, width, height);
                }
            }
        }
        g.setColor(old);
    }
}
