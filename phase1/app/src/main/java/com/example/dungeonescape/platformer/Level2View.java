package com.example.dungeonescape.platformer;
import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class Level2View extends SurfaceView implements Runnable{
    PlatformerManager manager;
    SurfaceHolder holder;
    boolean playing;
    boolean paused;
    long fps;
    private Thread thread;
    Canvas canvas;
    private long timeThisFrame;

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

    @Override
    public void run(){
        while (playing) {

            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // Updating the frame
//            if(!paused){
//                update();
//            }
            // Draw the frame

            update();
            draw();
//            collision_detection();

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

    public void update() {
        manager.update();
    }
    public void collision_detection() {
        manager.collision_detection();
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