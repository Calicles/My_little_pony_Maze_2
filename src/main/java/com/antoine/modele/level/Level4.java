package com.antoine.modele.level;

import com.antoine.contracts.IEnnemi;
import com.antoine.contracts.ILevel;
import com.antoine.contracts.LevelListener;
import com.antoine.entity.Boss;
import com.antoine.events.LevelChangeEvent;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;
import com.antoine.services.ImageReader;
import com.antoine.structure_donnee.LevelState;

import java.util.List;


/**
 * Classe gérant un Thread qui cadence l'évolution du jeu
 *
 * @author antoine
 */
public class Level4 extends Level3 implements ILevel {

    private IEnnemi boss;
    private Thread gameLoop;
    private List<LevelListener> listeners;
    private LevelChangeEvent event;
    private Coordinates startPlayerPosition, startBossPosition, startScreenPoisiton, startScrollPosition;

    private boolean over;
    private long before, after;
    private final long SLEEP= 1000 / 24;
    private String loseImagePath;

    public Level4(){
        super();
        init();
        over= false;
    }

    /**
     * <p>Doit être appelée après avoir injecté player et map</p>
     * @param boss ennemi du niveau
     */
    public void setBoss(IEnnemi boss){
        this.boss= boss;
        this.boss.setAttributes(player.getPosition(), map);
        startBossPosition= new Coordinates(boss.getX(), boss.getY());
        startPlayerPosition= new Coordinates(player.getX(), player.getY());
        startScreenPoisiton= new Coordinates(boxes.getScreenBeginX(), boxes.getScreenBeginY());
        startScrollPosition= new Coordinates(boxes.getScrollBeginX(), boxes.getScrollBeginY());
        boss.startThinking();
    }

    /*
    for test
     */
    public Boss getBoss(){return (Boss) boss;}

    @Override
    public void setListeners(List<LevelListener> listeners){
        this.listeners= listeners;
    }

    @Override
    public void setEvent(LevelChangeEvent lve){
        event = lve;
    }

    public void setLoseImagePath(String loseImagePath){
        this.loseImagePath= loseImagePath;
    }

    private void init() {
        gameLoop = new Thread(() -> {

            int count= 0;

            sleep(2000);

            while (running) {

                if(count != 0){
                    gameLose();
                }

                setAll();
                startAnimation();

                before = System.currentTimeMillis();
                while (!over && running) {
                    loop();
                    after = System.currentTimeMillis();
                    sleep();
                    before = System.currentTimeMillis();
                }

                if(count == 0){
                    count++;
                }
            }
            event.setBooleanTable(LevelState.get(id), false);
        });
    }

    private void setAll() {
        boss.translateTo(startBossPosition);
        player.translateTo(startPlayerPosition);
        boxes.getScreen().setCoordinates(startScreenPoisiton);
        boxes.getScroll().setCoordinates(startScrollPosition);
        over= false;
    }

    private void startAnimation(){
        Coordinates oldScreenPosition= new Coordinates(boxes.getScreenBeginX(), boxes.getScreenBeginY());
        animescreen(0,0, 8);
        animeBoss();
        animescreen(oldScreenPosition.getX(), oldScreenPosition.getY(), 3);

    }

    private void animeBoss() {
        while (boss.getY() < 12){
            boss.movesDown();
            fireUpdate();
            sleep(50);
        }
    }

    private void animescreen(int x, int y, long speed) {
        Coordinates vector;
        int xDirection= 0, yDirection= 0;

        if (x != boxes.getScreenBeginX()) {
            xDirection = ((x < boxes.getScreenBeginX()) ? -1 : 1);
        } else if (y != boxes.getScreenBeginY()) {
            yDirection = ((y < boxes.getScreenBeginY()) ? -1 : 1);
        }

        vector = new Coordinates(xDirection, yDirection);
        boxes.getScreen().translate(vector);
        fireUpdate();
        sleep(speed);
        if(!boxes.getScreen().equalsCoordinates(new Coordinates(x, y))) {
            animescreen(x, y, speed);
        }

    }

    private void gameLose(){
        String tampon= endImageUrl;

        endImageUrl= loseImagePath;
        fireUpdate();
        sleep(2500);

        endImageUrl= tampon;
    }

    public void start(){
        gameLoop.start();
    }

    private void sleep(long sleep){
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException ignored) {}
    }

    private void sleep() {
        try {
            Thread.sleep(fixSleep());
        }catch(InterruptedException ignored) {}
    }

    private long fixSleep() {
        long delta= after - before;

        if(delta < SLEEP)
            return SLEEP - delta;
        else
            return 0;
    }

    private synchronized void loop() {

        if(!checkCollision()) {

            Coordinates vector = player.memorizeMoves(map);

            scroll(vector);

            boss.memorizeMoves();

            boss.think();

            fireUpdate();

            checkRunning();
        }
    }

    private void scroll(Coordinates vector) {
        if(!vector.isZero()){
            if (vector.getX() < 0){
                scrollLeft(vector);
            }else if(vector.getX() > 0){
                scrollRight(vector);
            }else if (vector.getY() < 0){
                scrollUp(vector);
            }else
                scrollDown(vector);
        }
    }

    private boolean checkCollision() {
        if(Rectangle.isTouching2(boss.getPosition(), player.getPosition())){
            over= true;
            sleep(50);
            return true;
        }
        return false;
    }

    @Override
    public void playerMovesLeft() {
        player.movesLeft();
    }

    @Override
    public void playerMovesRight() {
        player.movesRight();
    }

    @Override
    public void playerMovesUp() {
        player.movesUp();
    }

    @Override
    public void playerMovesDown() {
        player.movesDown();
    }

    private void fireUpdate(){
        for (LevelListener l:listeners){
            l.update(event);
        }
    }

    @Override
    public boolean isRunning(){
        if(running && over){
            return false;
        }
        return running;
    }

}
