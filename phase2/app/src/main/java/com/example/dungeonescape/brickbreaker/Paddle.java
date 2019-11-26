package com.example.dungeonescape.brickbreaker;

import com.example.dungeonescape.game.GameObject;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

/** Creates a paddle object that "catches" the ball within the Brick Breaker game. */
class Paddle extends GameObject {
    /** Width and height of the paddle, respectively. */
    private int w, h;

    /** Whether the paddle is moving left or right. */
    private boolean movingLeft;
    private boolean movingRight;

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
    void draw(Canvas canvas) {
        this.getPaint().setColor(Color.WHITE);
        this.getPaint().setStrokeWidth(3);
        int x = this.getX();
        int y = this.getY();
        canvas.drawRect(x, y, x + w, y + h, this.getPaint());
        // x is left, y is top
    }

    /**
     * Moves the paddle in the x direction by a specified increment.
     * @param step the number of pixels to move the paddle by.
     */
    void move(int step) {
        int x = this.getX();
        this.setX(x += step);
    }

    /**
     * Changes the direction of movement of the paddle.
     * @param dir the direction to change.
     * @param val the boolean value to change the direction to.
     */
    void setMovementDir(String dir, boolean val){
        if (dir.equals("left")){
            movingLeft = val;
        } else if (dir.equals("right")){
            movingRight = val;
        }
    }

    Boolean getMovingLeft() {
        return movingLeft;
    }

    Boolean getMovingRight() {
        return movingRight;
    }

    /**
     * Returns a rectangular representation of the object.
     * @return Rect object.
     */
    Rect getRect() {
        int x = this.getX();
        int y = this.getY();
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
