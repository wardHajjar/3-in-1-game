package com.example.dungeonescape.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Brick extends BBObject {
    // width and height of each brick
    private int w, h;

    private boolean hit;

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


}


