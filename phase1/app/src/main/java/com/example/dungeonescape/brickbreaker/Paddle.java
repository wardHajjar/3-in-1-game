package com.example.dungeonescape.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Color;

class Paddle extends BBObject {
    // width and height of paddle
    private int w, h;

    // whether paddle if moving left or right
    private boolean movingLeft;
    private boolean movingRight;

    // construct a Paddle at location x, y
    // width and height of the canvas are passed into the constructor
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
            this.move(-20);
        } else if (movingRight) {
            this.move(20);
        }
    }

    private void move(int step) {
        x += step;
    }

    // checks the x-axis edges of the canvas; width of canvas is passed into it
    void checkBounds(int width) {
        if (x <= 0) x = 0;
        else if (x + w >= width) x = width - w;
    }
}

    /**  Move the key events to BBView when ready

    import android.view.KeyEvent;

    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        if (KeyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            paddle.movingLeft = true;
        } else if (KeyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            paddle.movingRight = true;
        }
        return super.onKeyDown(keyCode, event);
    }

    */
