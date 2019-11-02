package com.example.dungeonescape.platformer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import com.example.dungeonescape.GameView;
import com.example.dungeonescape.Player;

/**
 * This class is responsible for drawing out the game objects of the level, as well as
 * as well as updating player state.
 */
public class Level3View extends GameView implements Runnable{


    private PlatformerManager manager;
    private boolean nextLevel;
    private boolean noLives;

    /**
     * Initializes the surface in the context environment.
     * @param context the environment
     * @param attrs attributes
     */
    public Level3View(Context context, AttributeSet attrs) {
        super(context, attrs);
        manager = new PlatformerManager();
        setFocusable(true);
        setZOrderOnTop(true);
    }

    public Level3View(Context context) {
        super(context);
        manager = new PlatformerManager();
        setFocusable(true);
        setZOrderOnTop(true);
    }
    /**
     * Method draws all the objects onto the screen.
     */
    public void draw() {
        if (holder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            manager.draw(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }
    /** Returns the the Platform Manager for Level3
     *
     * @return manager
     */
    public PlatformerManager getManager() {
        return manager;
    }

    /** Updates the platform manager and if player is alive or dead
     */
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
    /** Updates the the screen when the player loses a life, creates a new platform manager.
     */
    public void gameOver(Player player) {
        manager = new PlatformerManager();
        manager.setPlayer(player);
        holder = getHolder();
        setFocusable(true);
        setZOrderOnTop(true);
    }
    /** Returns the boolean nextLevel that is true if the player moves onto the next level.
     */
    public boolean nextLevel() {
        return nextLevel;
    }

    /** Returns the boolean noLives that is true if the player has no more lives.
     */
    public boolean dead(){
        return noLives;
    }

}