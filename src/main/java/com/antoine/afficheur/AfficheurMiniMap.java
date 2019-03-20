package com.antoine.afficheur;

import com.antoine.contracts.IEntity;
import com.antoine.contracts.IMap;
import com.antoine.contracts.IStructure;
import com.antoine.contracts.IAfficheur;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;
import com.antoine.vue.panel.JMiniMap;

import java.awt.*;

public class AfficheurMiniMap extends AbstractAfficheur implements IAfficheur {

	public static final int SCALE= 10;




    @Override
    public void visit(IStructure structure) {
        drawMiniMap(structure);
    }

    public void drawMiniMap(IStructure structure) {
        this.drawBackground(structure);

        //on dessine la map
        IMap mapStruct= structure.getMap();
        Color old= g.getColor();
        Tile[][] map= mapStruct.getMap();
        int x, y;
        int width= mapStruct.getTile_width() / SCALE;
        int height= mapStruct.getTile_height() / SCALE;
        g.setColor(Color.ORANGE);
        for(int i=0; i<map.length; i++) {
            for(int j=0;j<map[0].length;j++) {
                x= map[i][j].getX() / SCALE;
                y= map[i][j].getY() / SCALE;

                if(map[i][j].getTile_num() > Tile.getSolidNum()) {
                    g.fillRect(x, y, width, height);
                }
            }
        }
        g.setColor(old);

        this.drawScreen(structure);
        this.drawPlayer(structure);
    }


    private void drawScreen(IStructure structure) {
        Rectangle screen= structure.getScreen();
        Color old= g.getColor();
        int x, y, width, height;
        g.setColor(Color.YELLOW);
        x= screen.getBeginX() / SCALE;
        y= screen.getBeginY() / SCALE;
        width= screen.getWidth() / SCALE;
        height= screen.getHeight() / SCALE;

        g.drawRect(x, y, width, height);
        g.setColor(old);
    }

    private void drawPlayer(IStructure structure) {
        IEntity player= structure.getPlayer();
        Color old= g.getColor();
        int x, y;
        x= player.getX() / SCALE;
        y= player.getY() / SCALE;
        g.setColor(Color.RED);
        g.fillRect(x, y, 5, 5);
        g.setColor(old);
    }

    private void drawBackground(IStructure structure) {
        Color old= g.getColor();
        int mapWidth, mapHeight;
        mapWidth= structure.getMapWidth() / SCALE;
        mapHeight= structure.getMapHeight() / SCALE;
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, mapWidth, mapHeight);
        g.setColor(old);
    }

}
