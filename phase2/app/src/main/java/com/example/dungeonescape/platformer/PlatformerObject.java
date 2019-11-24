package com.example.dungeonescape.platformer;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.dungeonescape.game.GameObject;

class PlatformerObject extends GameObject {
    private int size;
    private RectF shape;
    PlatformerManager manager;


    PlatformerObject(int x, int y, int size, PlatformerManager manager) {
        super(x,y);
        this.size = size;
        this.manager = manager;

    }
    int getSize() {
        return this.size;
    }
    void setShape() {
        this.shape = new RectF(getX() - size / 2, getY() - size / 2,
                getX() + size / 2, getY() + size / 2);
    }
    void setShape(int length, int width) {
        this.shape = new RectF(getX(), getY(), length + getX(),
                getY()+ width);
    }
    RectF getShape() {
        return this.shape;
    }
    void draw(Canvas canvas) {
        canvas.drawOval(this.shape, getPaint());
    }


}
