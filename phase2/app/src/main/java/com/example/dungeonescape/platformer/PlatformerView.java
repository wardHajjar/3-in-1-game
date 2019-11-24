package com.example.dungeonescape.platformer;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.example.dungeonescape.game.GameView;
import com.example.dungeonescape.player.Player;

/**
 * This class is responsible for drawing out the game objects of the level, as well as
 * as well as updating player state.
 */
public class PlatformerView extends GameView implements Runnable{

    private PlatformerManager manager;
    private boolean nextLevel;
    private boolean noLives;


    public PlatformerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        manager = new PlatformerManager();
        setFocusable(true);
        setZOrderOnTop(true);

    }
    public PlatformerView(Context context) {
        super(context);
        manager = new PlatformerManager();
        setFocusable(true);
        setZOrderOnTop(true);
    }

    public void setData(Player data)
    {
        manager.setPlayer(data);
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
    public PlatformerManager getManager() {
        return manager;
    }

    public void update() {

        boolean alive = manager.update();
        if (!alive) {
            gameOver(manager.getPlayer());
        }

        boolean finishedLevel = manager.finishedLevel();
        if (finishedLevel) {
            nextLevel = true;
        }
        boolean dead = manager.death();
        if (dead){
            noLives = true;
        }
    }

    public void gameOver(Player player) {
        manager = new PlatformerManager();
        manager.setPlayer(player);
        holder = getHolder();
        setFocusable(true);
        setZOrderOnTop(true);

    }

    public boolean nextLevel() {
        return nextLevel;
    }

    public boolean dead(){
        return noLives;
    }

}