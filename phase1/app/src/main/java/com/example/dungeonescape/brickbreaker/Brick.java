package com.example.dungeonescape.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Creates a brick obstacle in the brick breaker game.
 */
class Brick extends BBObject {
    /** width and height of each brick, respectively. */
    private int w, h;
    /** whether the brick has been hit or not. */
    private boolean hit;
    /** The coin that the brick contains, null if it contains no coin. */
    BBCoin coin;

    /**
     * Initializes a brick object.
     * @param x the x coordinate of the top left corner of the brick.
     * @param y the y coordinate of the top left corner of the brick.
     * @param w the width of the brick.
     * @param h the height of the brick.
     */
    Brick(int x, int y, int w, int h) {
        super(x, y);
        this.w = w;
        this.h = h;
        hit = false;
    }

    /**
     * Returns the width of the brick.
     * @return integer value of width.
     */
    int getWidth() {
        return this.w;
    }

    /**
     * Returns the height of the brick.
     * @return integer value of height.
     */
    int getHeight() {
        return this.h;
    }

    @Override
    void draw(Canvas canvas) {
        paintStyle.setStyle(Paint.Style.FILL);
        paintStyle.setColor(Color.LTGRAY);
        paintStyle.setStrokeWidth(3);
        canvas.drawRect(x, y, x + w, y + h, paintStyle);
    }

    /**
     * Gives the brick a coin.
     * @param coin the coin the brick contains.
     */
    void setCoin(BBCoin coin) {
        this.coin = coin;
    }

    /**
     * Returns whether the brick has a coin or not.
     * @return true if it contains a coin.
     */
    boolean hasCoin() {
        return this.coin != null;
    }

    /**
     * Method constructs a rectangle.
     * @return a rectangle representation of the brick.
     */
    Rect getRect(){
        return new Rect(x, y, x + w,  y + h);
    }

    /**
     * Method changes hit status to true to indicate that the brick has been hit by the ball.
     */
    void changeHitStatus(){
        hit = true;
    }

    /**
     * Methods returns the brick's state, whether it has been hit or not.
     * @return boolean value.
     */
    boolean getHitStatus(){
        return this.hit;
    }
}