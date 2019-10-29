package com.example.dungeonescape.platformer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;

import androidx.constraintlayout.solver.widgets.Rectangle;


public class Platforms extends Rectangle {
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
    }

    private void platformDown(int down) {
        if (this.y + down > manager.getGridHeight()) {
            // Move it to the top
            this.sety(0);
            Random r = new Random();
            int a = r.nextInt(1080- 150);
            this.setx(a);
            this.rectangle = new Rect((int) this.x, (int) this.y, (int) (this.length + x),
                    (int) (y+this.width));
        }
        else {
            this.sety(this.y + down);
            this.rectangle = new Rect((int) this.x, (int) this.y, (int) (this.length + x),
                    (int) (y+this.width));
        }

    }

    void draw(Canvas canvas) {
        canvas.drawRect(this.rectangle, this.paint);
    }

}



