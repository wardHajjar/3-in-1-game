package com.example.dungeonescape.platformer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RectShape;

class Level3Object extends RectShape {
    int x,y,size;
    Paint paint;
    RectF oval;
    PlatformerManager manager;
    Rect rect;

    Level3Object(int x, int y, int size, PlatformerManager manager) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.manager = manager;
        this.oval = new RectF(x - size / 2, y - size / 2, x + size / 2, y + size / 2);
        this.paint = new Paint();
    }
    Level3Object(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        paint = new Paint();
        this.oval = new RectF(x - size / 2, y - size / 2, x + size / 2, y + size / 2);
    }

    void setX(int x) {
        this.x = x;
    }
    void setY(int y) {
        this.y = y;
    }
    int getX() {
        return x;
    }
    int getY() {
        return y;
    }

    void draw(Canvas canvas) {
        canvas.drawOval(this.oval,this.paint);
    }


}
