package com.example.dungeonescape.platformer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;

import com.example.dungeonescape.game.GameView;
import com.example.dungeonescape.player.Player;

/**
 * This class is responsible for drawing out the game objects of the level, as well as
 * as well as updating player state.
 */
public class LevelView extends GameView implements Runnable {

    private PlatformerManager manager;
    private Point size;
    protected OnCustomEventListener endGameListener;



    public LevelView(Context context, AttributeSet attrs, String mode) {

        super(context, attrs);
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        if (mode.equals("Regular")) {
            manager = new PlatformerManager(size.y, size.x);
        }
        else {
            manager = new PlatformerManager(size.y, size.x, 20);
        }
        setFocusable(true);
        setZOrderOnTop(true);
    }

    public void draw() {
        if (holder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            manager.draw(canvas);
            holder.unlockCanvasAndPost(canvas);

        }
    }

    PlatformerManager getManager() {
        return manager;
    }

    Point getSize() {
        return size;
    }

    public void update() {

        boolean finishedLevel = manager.finishedLevel();
        if (finishedLevel) {
//            nextLevel();
        }
        boolean dead = manager.isDead();
        if (dead) {
            dead();
        }

        boolean alive = manager.update();
        if (!alive) {
            gameOver(manager.getPlayer());
        }

    }

    public void gameOver(Player player) {
        manager = new PlatformerManager(size.y, size.x);
        manager.setPlayer(player);
        holder = getHolder();
        setFocusable(true);
        setZOrderOnTop(true);

    }

//    public void nextLevel() {
//        finishLevelListener.onEvent();
//    }


    public void setEndGameListener(OnCustomEventListener eventListener) {
        this.endGameListener = eventListener;
    }

    public void dead() {
        endGameListener.onEvent();
    }

}
