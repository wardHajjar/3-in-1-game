package com.example.dungeonescape.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Color;

class Paddle extends BBObject {
    // Width and height of paddle
    private int w, h;

    // Whether paddle if moving left or right
    boolean movingLeft;
    boolean movingRight;

    // Construct a Paddle at location x, y
    Paddle(int x, int y) {
        super(x, y);
        w = 150;
        h = 20;
        movingLeft = false;
        movingRight = false;
    }

    @Override
    void draw(Canvas canvas) {
        paintStyle.setColor(Color.WHITE);
        paintStyle.setStrokeWidth(3);
        canvas.drawRect(x, y, x + w, y + h, paintStyle);
        // x is left, y is top
    }

    void updateLocation() {
        if (movingLeft) {
            move(-20);
        } else if (movingRight) {
            move(20);
        }
    }

    private void move(int step) {
        x += step;
    }

    // Checks the x-axis edges of the canvas; width of canvas is passed into it
    void checkBounds(int width) {
        if (x <= 0) x = 0;
        else if (x + w >= width) x = width - w;
    }
}
