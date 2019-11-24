package com.example.dungeonescape.platformer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import java.lang.Math;
import java.util.Random;


class Platforms extends PlatformerObject {

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


    Platforms(int x, int y, int length, int width, PlatformerManager manager) {
        super(x,y);
        this.length = length;
        this.width = width;
        paint = new Paint();
        paint.setColor(Color.GREEN);
        this.manager = manager;
        setShape();
    }
    /** Updates the platforms, moving the platforms down when the ball surpasses them. */
    void update(int down) {
        // Moves platforms down
        platformDown(down);
        setShape();
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
            int a = r.nextInt(manager.getGridWidth() - 150);
            this.setX(a);
        }
        else {
            this.setY(this.y + down);
        }
    }
    /** Draws the rectangle*/
    void draw(Canvas canvas) {
        canvas.drawRect(this.shape, this.paint);
    }

    void setShape() {
        this.shape = new RectF( this.x, this.y, (int) (this.length + x),
                (int) (y+this.width));
    }
}


