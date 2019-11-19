package com.example.dungeonescape.platformer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;

import androidx.constraintlayout.solver.widgets.Rectangle;


class Platforms extends Rectangle {
    private float x, y;
    private float length;
    private float width;
    private int height;
    private Paint paint;
    private PlatformerManager manager;
    Rect rectangle;

    void setx(float x) {
        this.x = x;
    }

    float getx() {
        return this.x;
    }

    void sety(float y) {
        this.y = y;
    }

    float gety() {
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

    void update(int down) {
        // Moves platforms down
        platformDown(down);
        this.rectangle = new Rect((int) this.x, (int) this.y, (int) (this.length + x),
                (int) (y+this.width));
    }

    private void platformDown(int down) {
        if (this.y + down > manager.getGridHeight()) {
            // Move it to the top
            int diff = Math.abs((int)this.y + down - manager.getGridHeight());
            if (diff > 400) {
                this.sety(0);
            }
            else if (diff > 200) {
                this.sety(-200);
            }
            else {
                this.sety(-diff);
            }
            Random r = new Random();
            int a = r.nextInt(1080- 150);
            this.setx(a);
        }
        else {
            this.sety(this.y + down);

        }
    }

    void draw(Canvas canvas) {
        canvas.drawRect(this.rectangle, this.paint);
    }
}



