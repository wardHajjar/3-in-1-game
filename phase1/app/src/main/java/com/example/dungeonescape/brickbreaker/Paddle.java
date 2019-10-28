package com.example.dungeonescape.brickbreaker;

import android.graphics.Rect;


class Paddle {
    // width and height of paddle
    private int w, h;

    // position of paddle, in x & y
    private int x, y;

    // whether paddle if moving left or right
    private boolean movingLeft;
    private boolean movingRight;


    // construct a Paddle at location x, y
    // width and height of the canvas are passed into the constructor

    Paddle(int width, int height) {
        w = 150;
        h = 20;
        x = width/2 - w/2;
        y = height - 40;
        movingLeft = false;
        movingRight = false;
    }

    void draw() {
        Rect rect = new Rect(x, y - h, x + w, y);
    }

    void updateLocation() {
        if (movingLeft) {
            this.move(-20);
        } else if (movingRight) {
            this.move(20);
        }
    }

    private void move(int step) {
        x += step;
    }

    // checks the x-axis edges of the canvas; width of canvas ia passed into it
    void checkBounds(int width) {
        if (x <= 0) x = 0;
        else if (x + w >= width) x = width - this.w;
    }
}
