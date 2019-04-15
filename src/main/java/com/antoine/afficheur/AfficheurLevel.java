package com.antoine.afficheur;

import com.antoine.contracts.IEntity;
import com.antoine.contracts.IMap;
import com.antoine.contracts.IStructure;
import com.antoine.contracts.IAfficheur;
import com.antoine.geometry.Rectangle;
import com.antoine.geometry.Tile;
import com.antoine.modele.level.Level;
import com.antoine.modele.level.Level2;
import com.antoine.modele.level.Level3;
import com.antoine.modele.level.Level4;
import com.antoine.services.ImageReader;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class AfficheurLevel extends AbstractAfficheur implements IAfficheur {



    @Override
    public void visit(IStructure structure) {

        if(structure instanceof Level){
            drawLevel1(structure);
        }else if(structure instanceof Level2) {
            drawLevel2(structure);
        }else if(structure instanceof Level4){
            drawLevel4(structure);
        }else if(structure instanceof Level3){
            drawLevel3(structure);
        }

    }

    private void drawLevel4(IStructure structure) {
        IEntity boss= structure.getBoss();
        Rectangle screen= structure.getScreen();
        drawLevel3(structure);
        g.drawImage(boss.getImage(), playerScreenPositionX(boss, screen), playerScreenPositionY(boss, screen), null);
    }


    private void drawLevel1(IStructure structure) {

        if(!structure.isRunning()) {
            String endImageUrl= structure.getEndImageUrl();
            BufferedImage endImage= ImageReader.lireImage(endImageUrl);
            g.drawImage(endImage, 0, 0, null);
        }else
            drawLevel(structure);

    }
    private void drawLevel(IStructure structure) {
        IEntity player= structure.getPlayer();
        IMap map= structure.getMap();
        drawMap(map);
        g.drawImage(player.getImage(), player.getX(), player.getY(), null);
    }
    private void drawMap(IMap mapStruct) {
        HashMap<Integer, BufferedImage> tileSet= mapStruct.getTileSet();
        Tile[][] map= mapStruct.getMap();
        Tile tile;
        for(int i=0;i<map.length;i++)
        {
            for(int j=0;j<map[0].length;j++)
            {
                tile= map[i][j];
                g.drawImage(tileSet.get(tile.getTile_num()),
                        tile.getX(), tile.getY(), null);
            }
        }
    }
    private void drawLevel2(IStructure structure) {
        if(structure.isRunning()) {
            drawScreen(structure);
            drawPlayer(structure);
        } else {
            BufferedImage image = ImageReader.lireImage(structure.getEndImageUrl());
            g.drawImage(image, 0, 0, null);
        }
    }

    private void drawPlayer(IStructure structure) {
        IMap map = structure.getMap();
        IEntity player= structure.getPlayer();
        int screenPosY= playerScreenPositionY(player, map);
        g.drawImage(player.getImage(), player.getX(), screenPosY, null);
    }

    private void drawScreen(IStructure structure) {
        IMap mapStruct= structure.getMap();
        Rectangle screen= structure.getScreen();
        Tile tile;
        Tile[][] map= mapStruct.getMap();
        HashMap<Integer, BufferedImage> set= mapStruct.getTileSet();
        int row= screen.getBeginY();
        int col= screen.getBeginX();
        int rowMax= screen.getEndY();
        int colMax= screen.getEndX();
        int x= 0, y= 0;

        for(int i= row; i<rowMax; i++) {
            for(int j= col; j<colMax; j++) {
                tile= map[i][j];
                g.drawImage(set.get(tile.getTile_num()), x * mapStruct.getTile_width(),
                        y * mapStruct.getTile_height(), null);
                x++;
            }
            x= 0; y++;
        }
    }
    private int playerScreenPositionY(IEntity player, IMap map) {
        int coef;
        int tile_height= map.getTile_height();
        coef= player.getY() / (tile_height * 20);
        return player.getY() - (coef * (tile_height * 20));
    }

    private void drawLevel3(IStructure structure) {
        if(structure.isRunning()) {
            drawScreenLevel3(structure);
            drawPlayerLevel3(structure);
        } else{
              BufferedImage image= ImageReader.lireImage(structure.getEndImageUrl());
              g.drawImage(image, 0, 0, null);
            }
    }

    private void drawScreenLevel3(IStructure structure) {
        IMap mapStruct= structure.getMap();
        Rectangle screen= structure.getScreen();
        Tile tile;
        Tile[][] map= mapStruct.getMap();
        HashMap<Integer, BufferedImage> set= mapStruct.getTileSet();
        int tile_width= mapStruct.getTile_width();
        int tile_height= mapStruct.getTile_height();

        int row= screen.getBeginY() / tile_height;
        int col= screen.getBeginX() / tile_width;
        int rowMax= screen.getEndY() / tile_height;
        int colMax= screen.getEndX() / tile_width;
        int x, y;

        if(screen.getEndY() % mapStruct.getTile_height() != 0) rowMax ++;
        if(screen.getEndX() % mapStruct.getTile_width() != 0) colMax ++;
        for(int i= row; i<rowMax; i++) {
            for(int j= col; j<colMax; j++) {
                tile= map[i][j];
                y= tile.getY() - screen.getBeginY();
                x= tile.getX() - screen.getBeginX();
                g.drawImage(set.get(tile.getTile_num()), x,
                        y, null);
            }
        }
    }

    private void drawPlayerLevel3(IStructure structure) {
        IEntity player= structure.getPlayer();
        Rectangle screen= structure.getScreen();
        int screenPosY= playerScreenPositionY(player, screen);
        int screenPosX= playerScreenPositionX(player, screen);
        g.drawImage(player.getImage(), screenPosX, screenPosY, null);
    }

    private int playerScreenPositionY(IEntity player, Rectangle screen) {
        return player.getY() - screen.getBeginY();
    }

    private int playerScreenPositionX(IEntity player, Rectangle screen) {
        return player.getX() - screen.getBeginX();
    }

    private void sleep(long sleep){
        try{
            Thread.sleep(sleep);
        } catch (InterruptedException ignored) {}
    }
}
