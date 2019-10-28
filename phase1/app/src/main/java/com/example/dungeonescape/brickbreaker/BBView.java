package com.example.dungeonescape.brickbreaker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Display;
import android.graphics.Point;
import android.app.Activity;
/*
BBMainActivity and BBView were structured like the following game:
http://gamecodeschool.com/android/building-a-simple-game-engine/
 */

/**
 * Class that controls the logic of the game by handling collisions, updating the objects' stats and
 * drawing the new states onto the canvas.
 */
public class BBView extends SurfaceView implements Runnable {
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
     * screenX - the width of the screen
     * screenY - the height of the screen
     */
    Thread gameThread = null;
    SurfaceHolder holder;
    boolean playing;
    boolean paused = true;
    Canvas canvas;
    long fps;
    private long timeThisFrame;
    Paint paint;
    int screenX;
    int screenY;
    /**
     * Initializes the surface in the context environment.
     * @param context the environment
     */
    public BBView(Context context){
        super(context);
        this.holder = getHolder();
        this.paint = new Paint();

        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.screenX = size.x;
        this.screenY = size.y;
    }

    /**
     * The method that runs the program's while loop.
     */
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

    /**
     *
     */
    // TODO: Detecting collisions and updating objects' positions.
    public void update() {

    }

    /**
     *
     */
    public void draw() {

        // Make sure the drawing surface is valid or program crashes
        if (holder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = holder.lockCanvas();

            // Draw the background color - black
            canvas.drawColor(Color.argb(255,  0, 0, 0));

            // Choose the brush color for drawing - white
            paint.setColor(Color.argb(255,  255, 255, 255));
            paint.setTextSize(100);
            // TODO: Draw the balls, bricks and paddle


            // The size of the screen in pixels

            // Draws everything to the screen
            holder.unlockCanvasAndPost(canvas);
        }

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
