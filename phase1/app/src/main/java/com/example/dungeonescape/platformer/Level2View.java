package com.example.dungeonescape.platformer;
import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.dungeonescape.Player;

public class Level2View extends SurfaceView implements Runnable{
    private PlatformerManager manager;
    SurfaceHolder holder;
    boolean playing;
    boolean paused;
    long fps;
    private Thread thread;
    Canvas canvas;
    private boolean nextLevel;
    private boolean noLives;
    private long timeThisFrame;

    //    private Player user; // referencing from player class for lives

    public Level2View(Context context, AttributeSet attrs) {
        super(context, attrs);
        manager = new PlatformerManager();
        holder = getHolder();
        setFocusable(true);
        setZOrderOnTop(true);

    }
    public Level2View(Context context) {
        super(context);
        manager = new PlatformerManager();
        holder = getHolder();
        setFocusable(true);
        setZOrderOnTop(true);
    }
    /**
     * The method that runs the program's while loop.
     */
    public void setData(Player data)
    {
        manager.setPlayer(data);
    }

    @Override
    public void run(){
        while (playing) {

            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // Updating the frame
            if(!paused){
                update();
            }
            // Draw the frame
            draw();
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {   // Calculating the fps
                fps = 1000 / timeThisFrame;
            }
        }
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

    public void pause() {
        playing = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    /**
     * If Activity is started then thread must start as well.
     */
    public void resume() {
        playing = true;
        thread = new Thread(this);
        thread.start();
    }

}