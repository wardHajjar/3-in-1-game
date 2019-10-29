package com.example.dungeonescape.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Brick extends BBObject {
    // width and height of each brick
    private int w, h;
    // whether the brick has been hit or not
    boolean hit;


    Brick(int x, int y) {
    // construct a Paddle at location x, y
    // width and height of the canvas are passed into the constructor
        super(x, y);
        w = 24;
        h = 18;
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
        return new Rect(x, y, w, h);
    }


}

/** in BBView
 * - initialize bricks as an array
 * - then them up using a for-loop
 *      - decide how many bricks and how they will be positioned
 * - in BBView draw(): loop through brick array --> call brick.draw()
 *      - nested for-loop:
 *
 *
 *
 */
