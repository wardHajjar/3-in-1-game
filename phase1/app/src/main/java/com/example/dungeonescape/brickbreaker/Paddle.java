package com.example.dungeonescape.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

/**
 * Creates a paddle object that "catches" the ball within the Brick Breaker game.
 */
class Paddle extends BBObject {
    /**
     * Width and height of the paddle, respectively.
     */
    private int w, h;

    /**
     * Whether the paddle is moving left or right.
     */
    boolean movingLeft;
    boolean movingRight;


    /**
     * Construct a new Paddle at location (x, y).
     * @param x coordinate of the top left corner of the paddle location.
     * @param y coordinate of the top left cornery of the paddle location.
     */
    Paddle(int x, int y) {
        super(x, y);
        w = 150;
        h = 20;
        movingLeft = false;
        movingRight = false;
    }

    /**
     * Draws the paddle object on screen.
     * @param canvas the graphic context on which the object is drawn
     */
    @Override
    void draw(Canvas canvas) {
        paintStyle.setColor(Color.WHITE);
        paintStyle.setStrokeWidth(3);
        canvas.drawRect(x, y, x + w, y + h, paintStyle);
        // x is left, y is top
    }

    /**
     * Updates the paddle location based on the direction of movement.
     */
    void updateLocation() {
        if (movingLeft) {
            move(-20);
        } else if (movingRight) {
            move(20);
        }
    }

    /**
     * Moves the paddle in the x direction by a specified increment.
     * @param step the number of pixels to move the paddle by.
     */
    private void move(int step) {
        x += step;
    }

    //

    /**
     * Checks the x-axis edges of the canvas.
     * @param width of canvas.
     */
    void checkBounds(int width) {
        if (x <= 0) x = 0;
        else if (x + w >= width) x = width - w;
    }

    /**
     * Returns a rectangular representation of the object.
     * @return Rect object.
     */
    Rect getRect(){
        return new Rect(x, y, x + w, y + h);

    }
}
