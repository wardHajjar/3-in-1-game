package com.example.dungeonescape.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Brick extends BBObject {
    // width and height of each brick
    private int w, h;
    // whether the brick has been hit or not
    private boolean hit;


    Brick(int x, int y, int w, int h) {
    // construct a Paddle at location x, y
    // width and height of the canvas are passed into the constructor
        super(x, y);
        this.w = w;
        this.h = h;
        hit = false;
    }

    @Override
    void draw(Canvas canvas) {
        paintStyle.setStyle(Paint.Style.FILL);
        paintStyle.setColor(Color.LTGRAY);
        paintStyle.setStrokeWidth(3);
        canvas.drawRect(x, y, x + w, y + h, paintStyle);
        // x is left, y is top
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
