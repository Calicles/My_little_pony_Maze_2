package com.antoine.modele.level;

import com.antoine.contracts.IEnnemi;
import com.antoine.contracts.ILevel;
import com.antoine.contracts.LevelListener;
import com.antoine.geometry.Coordinates;

import java.util.List;


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

    public void setBoss(IEnnemi boss){
        this.boss= boss;
    }

    public void setListeners(List<LevelListener> listeners){
        this.listeners= listeners;
    }

    private void init() {
        gameLoop = new Thread(() -> {

            before = System.currentTimeMillis();
            while (running) {
                loop();
                after = System.currentTimeMillis();
                sleep();
                before = System.currentTimeMillis();
            }
        });
    }

    public void start(){
        gameLoop.start();
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

        boss.memorizeMoves(map);

        checkCollision();

        fireUpdate();

        checkRunning();

        boss.think(player.toRectangle());

    }

    private void scroll(Coordinates vector) {
        if(!vector.isZero()){
            if (vector.getX() < 0){
                scrollUp(vector);
            }else if(vector.getX() > 0){
                scrollRight(vector);
            }else if (vector.getY() < 0){
                scrollUp(vector);
            }else
                scrollDown(vector);
        }
    }

    private void checkCollision() {
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
