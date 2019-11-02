package com.example.dungeonescape.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

class Brick extends BBObject {
    /* Width and height of each brick. */
    private int w, h;
    /* Whether the brick has been hit or not. */
    private boolean hit;
    /* Whether a brick has a coin or not. */
    BBCoin coin;

    /**
     * Construct a Paddle at location x, y, with width w and height h.
     * @param x the x location of the paddle.
     * @param y the y location of the paddle.
     * @param w width of the brick.
     * @param h height of the brick.
     */
    Brick(int x, int y, int w, int h) {
        super(x, y);
        this.w = w;
        this.h = h;
        hit = false;
    }

    /** Return the width of the brick. */
    int getWidth() {
        return this.w;
    }

    /** Return the height of the brick. */
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

    /** Set a coin to the brick. */
    void setCoin(BBCoin coin) {
        this.coin = coin;
    }

    /** Returns whether the brick contains a coin. */
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
     * Method changes this.hit to true to indicate that the brick has been hit by the ball.
     */
    void changeHitStatus(){
        hit = true;
    }

    /**
     * Methods returns the brick's state, whether it has been hit or not
     * @return this.hit
     */
    boolean getHitStatus(){
        return this.hit;
    }
}