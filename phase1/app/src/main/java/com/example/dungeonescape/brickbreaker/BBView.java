package com.example.dungeonescape.brickbreaker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

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
     */
    Thread gameThread = null;
    SurfaceHolder holder;
    boolean playing;
    boolean paused = true;
    Canvas canvas;
    long fps;
    private long timeThisFrame;
    Paint paint;

    // Canvas width and height
    int width; int height;

    //
    Paddle paddle;
    ArrayList<Brick> bricks;

    /**
     * Initializes the surface in the context environment.
     * @param context the environment
     */
    public BBView(Context context){
        super(context);
        holder = getHolder();
        paint = new Paint();

        // construct paddle and bricks
        paddle = new Paddle(width/2 - 75, height - 30);
        bricks = new ArrayList<>();
        for (int x = 0; x < width; x += 24) {
            for (int y = 0; y < height; y += 18) {
                bricks.add(new Brick(x, y));
            }
        }

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

            // TODO: Draw the balls, bricks and paddle.

            // Paddle
            paddle.draw(canvas);
            // while playing/not dead
            paddle.checkBounds(width);
            paddle.updateLocation();

            // bricks
//            for (int i = 0; i < bricks.size(); i++) {
//                if (bricks.get(i).hit == false) {
//
//                }
//
//            }

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
