package com.example.dungeonescape;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/*
GameView was structured like the following game:
http://gamecodeschool.com/android/building-a-simple-game-engine/
 */

/**
 * Class that controls the logic of the game by handling collisions, updating the objects' stats and
 * drawing the new states onto the canvas.
 */
public class GameView extends SurfaceView implements Runnable{
    /**
     * gameThread - the main game thread that gets executed by the program.
     * holder - contains the canvas on which objects are drawn.
     * playing - indicates whether the game is running i.e. user is playing
     * paused - indicates if the game is paused; starts off as true since the user hasn't started
     *          playing yet.
     * canvas - the Canvas object onto which objects are drawn.
     * fps - frames per second.
     * timeThisFrame - time it takes to execute the draw and update methods in one frame.
     * paint - the Paint object which determines the drawing style.
     */
    Thread gameThread = null;
    SurfaceHolder holder;
    boolean playing;
    boolean paused = true;
    Canvas canvas;
    long fps;
    private long timeThisFrame;
    Paint paint;

    /**
     * Initializes the surface in the context environment.
     * @param context the environment
     */
    public GameView(Context context){
        super(context);
        holder = getHolder();
        paint = new Paint();
    }
    public GameView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        holder = getHolder();
        paint = new Paint();
    }

    /**
     * The method that runs the program's while loop.
     */
    @Override
    public void run(){
    }

    /**
     *
     */
    void update() {
    }

    /**
     *
     */
    void draw() {
    }

    /**
     * If Activity is paused/stopped, the thread must stop as well.
     */
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    /**
     * If Activity is started then thread must start as well.
     */
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }



}

