package com.example.dungeonescape.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import com.example.dungeonescape.game.GameObject;
import com.example.dungeonescape.game.collectable.Collectable;

class Brick extends GameObject {
    // width and height of each brick
    private int w, h;
    // whether the brick has been hit or not
    private boolean hit;
    // whether a brick has a coin or not
    Collectable item;


    Brick(int x, int y, int w, int h) {
    // construct a Paddle at location x, y
    // width and height of the canvas are passed into the constructor
        super(x, y);
        this.w = w;
        this.h = h;
        hit = false;
    }

    int getWidth() {
        return this.w;
    }

    int getHeight() {
        return this.h;
    }

    void draw(Canvas canvas) {
        Paint paint = getPaint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.LTGRAY);
        paint.setStrokeWidth(3);
        canvas.drawRect(getX(), getY(), getX() + w, getY() + h, paint);
        // x is left, y is top
    }

    void setItem(Collectable item) {
        this.item = item;
    }

    boolean hasItem() {
        return this.item != null;
    }

    /**
     * Method constructs a rectangle.
     * @return a rectangle representation of the brick.
     */
    Rect getRect(){
        int x = getX();
        int y = getY();
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