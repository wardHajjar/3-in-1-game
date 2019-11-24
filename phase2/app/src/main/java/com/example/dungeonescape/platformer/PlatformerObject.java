package com.example.dungeonescape.platformer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RectShape;

import com.example.dungeonescape.game.GameObject;

class PlatformerObject extends RectShape {
    int x,y,size;
    Paint paint;
    RectF oval;
    PlatformerManager manager;
    Rect rect;

    PlatformerObject(int x, int y, int size, PlatformerManager manager) {
//        super(x,y);
        this.size = size;
        this.x = x;
        this.y = y;
        this.manager = manager;
        this.oval = new RectF(x - size / 2, y - size / 2, x + size / 2, y + size / 2);
        this.paint = new Paint();
    }
    PlatformerObject(int x, int y) {
//        super(x,y);
        this.x = x;
        this.y = y;
        this.oval = new RectF(x * size,
                y * size,
                (x + 1) * size,
                (y + 1) * size
                );
    }

    void setX(int x) {
        this.x = x;
    }
    void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    void draw(Canvas canvas) {
        canvas.drawOval(this.oval,this.paint);
    }


}
