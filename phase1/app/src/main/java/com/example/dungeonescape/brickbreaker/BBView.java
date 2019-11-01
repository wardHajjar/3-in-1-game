package com.example.dungeonescape.brickbreaker;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;

import android.view.Display;
import android.graphics.Point;
import android.app.Activity;
import android.widget.TextView;

import com.example.dungeonescape.GameView;
import com.example.dungeonescape.R;
/*
BBMainActivity and BBView were structured like the following game:
http://gamecodeschool.com/android/building-a-simple-game-engine/
 */

/**
 * Class that controls the logic of the game by handling collisions, updating the objects' stats and
 * drawing the new states onto the canvas.
 */
public class BBView extends GameView {
    /**
     * screenX - the width of the screen
     * screenY - the height of the screen
     */
    int screenX;
    int screenY;

    /**
     * ball - the ball object that bounces around and hits bricks.
     * paddle - the paddle that catches the ball.
     * bricks - list of all the bricks in the game.
     */

    boolean startGame = false;
    BBGameManager manager;
    public BBView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.screenX = size.x;
        this.screenY = size.y - 500;    // 300 accounts for the buttons above the playing screen.
        manager = new BBGameManager(screenX, screenY);
    }

    /**
     * Initializes the surface in the context environment.
     * @param context the environment
     */
    public BBView(Context context){
        super(context);

        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.screenX = size.x;
        this.screenY = size.y;
        manager = new BBGameManager(screenX, screenY);
    }

    /**
     *
     */
    public void update() {
        if (startGame){
            manager.moveBall();
            manager.movePaddle();
            if (manager.checkLifeCondition()){
                startGame = false;
            }
        }
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

            manager.drawGame(canvas);
            // Draws everything to the screen
            holder.unlockCanvasAndPost(canvas);
        }
    }

    // Dictates the movement of the paddle
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!startGame){
                System.out.println("GAME STrted; ");
                startGame = true;
            }
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            manager.movePaddle(event, x);
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            manager.movePaddle(event, x);
        }
        return super.onTouchEvent(event);
    }

    /**
     * Indicates whether the user has succeeded in passing the level
     * @return true if user passed.
     */
    public boolean doneLevel(){
        return manager.hitAllBricks();
    }

}
