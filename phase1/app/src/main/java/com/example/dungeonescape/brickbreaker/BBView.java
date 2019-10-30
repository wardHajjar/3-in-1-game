package com.example.dungeonescape.brickbreaker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import android.view.Display;
import android.graphics.Point;
import android.app.Activity;
import com.example.dungeonescape.GameView;
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
    Ball ball;
    Paddle paddle;
    ArrayList<Brick> bricks;
    boolean startGame = false;

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

        // construct the ball
        this.ball = new Ball(screenX/2 - 75 + (screenX/2 - 75)/2 - 25,
                screenY - 100 - 26, 25, -26);

        // construct paddle and bricks
        paddle = new Paddle(screenX/2 - 75, screenY - 100, screenX/3, 40);

        bricks = new ArrayList<>();
        int brickWidth = screenX / 6;
        int brickHeight = screenY / 20;
        for (int x = 0; x < screenX; x += brickWidth + 5) {
            for (int y = 10; y < 6 * brickHeight; y += brickHeight + 5) {
                bricks.add(new Brick(x, y, brickWidth, brickHeight));
            }
        }
    }

    /**
     *
     */
    public void update() {
        if (startGame){
            ball.move();
        }

        // Wall Collision Detection
        char wall_collision = ball.madeWallCollision(screenX, screenY);
        if ( wall_collision == 'x'){
            ball.setXSpeed(ball.getXSpeed() * -1);
        }
        else if(wall_collision == 'y'){
            ball.setYSpeed(ball.getYSpeed() * -1);
        }

        // Brick Collision Detection
        for (Brick brick: bricks){
            if (!(brick.getHitStatus())) {
                String brick_collision = ball.madeRectCollision(brick.getRect());
                if (brick_collision.equals("x")) {
                    ball.setXSpeed(ball.getXSpeed() * -1);
                    brick.changeHitStatus();
                    break;
                } else if (brick_collision.equals("y")) {
                    ball.setYSpeed(ball.getYSpeed() * -1);
                    brick.changeHitStatus();
                    break;
                }
            }
        }

        // Paddle Collision Detection
        String paddle_collision = ball.madeRectCollision(paddle.getRect());
        if (!(paddle_collision.equals(" ")) && startGame) {
            ball.setYSpeed(ball.getYSpeed() * -1);
            ball.setRandomXSpeed();

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

            // Ball
            ball.draw(canvas);

            // Paddle
            paddle.draw(canvas);
            // while playing/not dead
            if (playing) paddle.checkBounds(screenX);
            if (playing) paddle.updateLocation();

            // Bricks
            for (int i = 0; i < bricks.size(); i++) {
                if (!bricks.get(i).getHitStatus()) {
                    bricks.get(i).draw(canvas);
                }
            }

            // Draws everything to the screen
            holder.unlockCanvasAndPost(canvas);
        }

    }
    // Dictates the movement of the paddle
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!startGame){
                ball.move();
                startGame = true;
            }
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            if (x < paddle.x) {
                paddle.movingLeft = true;
            } else if (x > paddle.x) {
                paddle.movingRight = true;
            }
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            paddle.movingLeft = false;
            paddle.movingRight = false;
        }
        return super.onTouchEvent(event);
    }
}
