package com.example.dungeonescape.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

/** Creates a paddle object that "catches" the ball within the Brick Breaker game. */
class Paddle extends BBObject {
    /** Width and height of the paddle, respectively. */
    private int w, h;

    /** Whether the paddle is moving left or right. */
    boolean movingLeft;
    boolean movingRight;

    /**
     * Construct a new Paddle at location (x, y).
     * @param x coordinate of the top left corner of the paddle location.
     * @param y coordinate of the top left corner of the paddle location.
     */
    Paddle(int x, int y, int w, int h) {
        super(x, y);
        this.w = w;
        this.h = h;
        movingLeft = false;
        movingRight = false;
    }

    /**
     * Draws the paddle object on screen.
     * @param canvas the graphic context on which the object is drawn.
     */
    @Override
    void draw(Canvas canvas) {
        paintStyle.setColor(Color.WHITE);
        paintStyle.setStrokeWidth(3);
        canvas.drawRect(x, y, x + w, y + h, paintStyle);
        // x is left, y is top
    }

    /**
     * Moves the paddle in the x direction by a specified increment.
     * @param step the number of pixels to move the paddle by.
     */
    void move(int step) {
        x += step;
    }

    /**
     * Changes the direction of movement of the paddle.
     * @param dir the direction to change.
     * @param val the value to change the direction to.
     */
    void setMovementDir(String dir, boolean val){
        if (dir.equals("left")){
            movingLeft = val;
        }else if (dir.equals("right")){
            movingRight = val;
        }
    }

    /**
     * Returns a rectangular representation of the object.
     * @return Rect object.
     */
    Rect getRect(){
        return new Rect(x, y, x + w, y + h);

    }

    /**
     * Returns the height of the paddle.
     * @return integer value of height dimension.
     */
    int getHeight(){
        return h;
    }

    /**
     * Returns the width of the paddle
     * @return integer valye of width dimension.
     */
    int getWidth(){
        return w;
    }
}
