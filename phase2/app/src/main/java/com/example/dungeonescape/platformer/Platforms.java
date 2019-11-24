package com.example.dungeonescape.platformer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import java.lang.Math;
import java.util.Random;

import androidx.constraintlayout.solver.widgets.Rectangle;


class Platforms extends Rectangle {
    /** x,y coordinate of the platforms. */
    private float x, y;

    /** length of the platforms. */
    private float length;

    /** width of the platforms. */
    private float width;

    /** height of the platforms. */
    private int height;

    /** colour of the platforms. */
    private Paint paint;

    /** manger for the platforms. */
    private PlatformerManager manager;

    /** shape of the platforms. */
    Rect rectangle;

    /** sets x coordinate. */
    void setX(float x) {
        this.x = x;
    }

    /** gets x coordinate.
     *
     * @return a float x coordinate
     *
     * */
    float getX() {
        return this.x;
    }

    /** sets x coordinate. */
    void setY(float y) {
        this.y = y;
    }

    /** gets y coordinate.
     *
     * @return a float y coordinate
     *
     * */
    float getY() {
        return this.y;
    }


    Platforms(float x, float y, float length, float width, PlatformerManager manager) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
        paint = new Paint();
        paint.setColor(Color.GREEN);
        this.manager = manager;
        this.rectangle = new Rect((int) this.x, (int) this.y, (int) (this.length + x),
                (int) (y+this.width));
    }
    /** Updates the platforms, moving the platforms down when the ball surpasses them. */
    void update(int down) {
        // Moves platforms down
        platformDown(down);
        this.rectangle = new Rect((int) this.x, (int) this.y, (int) (this.length + x),
                (int) (y+this.width));
    }

    /** Moves the platforms down, i.e when the ball surpasses the platforms they get moved to their
     * new location above the character. */
    private void platformDown(int down) {
        if (this.y + down > manager.getGridHeight()) {
            // Move it to the top
            int diff = Math.abs((int)this.y + down - manager.getGridHeight());
            if (diff > 400) {
                this.setY(0);
            }
            else if (diff > 200) {
                this.setY(-200);
            }
            else {
                this.setY(-diff);
            }
            Random r = new Random();
            int a = r.nextInt(1080- 150);
            this.setX(a);
        }
        else {
            this.setY(this.y + down);

        }
    }
    /** Draws the rectangle*/
    void draw(Canvas canvas) {
        canvas.drawRect(this.rectangle, this.paint);
    }
}


