package com.antoine.modele.level;

import com.antoine.contracts.IEnnemi;
import com.antoine.contracts.ILevel;
import com.antoine.contracts.LevelListener;
import com.antoine.entity.Boss;
import com.antoine.geometry.Coordinates;
import com.antoine.geometry.Rectangle;

import java.util.List;


/**
 * Classe gérant un Thread qui cadence l'évolution du jeu
 *
 * @author antoine
 */
public class Level4 extends Level3 implements ILevel {

    private IEnnemi boss;
    private  Thread gameLoop;
    private List<LevelListener> listeners;

    private long before, after;
    private final long SLEEP= 1000 / 24;

    public Level4(){
        super();
        init();
    }

    /**
     * <p>Doit être appelée après avoir injecté player et map</p>
     * @param boss ennemi du niveau
     */
    public void setBoss(IEnnemi boss){
        this.boss= boss;
        boss.setAttributes(boss.toRectangle(), player.toRectangle(), map);
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

    private void init() {
        gameLoop = new Thread(() -> {

            startAnimation();
            before = System.currentTimeMillis();
            while (running) {
                loop();
                after = System.currentTimeMillis();
                sleep();
                before = System.currentTimeMillis();
            }
        });
    }

    private void startAnimation(){
        Coordinates oldScreenPosition= new Coordinates(boxes.getScreenBeginX(), boxes.getScreenBeginY());
        animescreen(0,0);
        animeBoss();
        animescreen(oldScreenPosition.getX(), oldScreenPosition.getY());

    }

    private void animeBoss() {
        while (boss.getY() != 0){
            boss.movesDown();
            fireUpdate();
            sleep(50);
        }
    }

    private void animescreen(int x, int y) {
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
        sleep(10);
        if(!boxes.getScreen().equalsCoordinates(new Coordinates(x, y))) {
            animescreen(x, y);
        }

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
        Coordinates vector= player.memorizeMoves(map);

        scroll(vector);

        boss.memorizeMoves();

        checkCollision();

        fireUpdate();

        checkRunning();

        boss.think();

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

    private void checkCollision() {
        if(Rectangle.isTouching(boss.toRectangle(), player.toRectangle())){
            throw new RuntimeException("perdu");
        }
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
            l.update();
        }
    }

}
