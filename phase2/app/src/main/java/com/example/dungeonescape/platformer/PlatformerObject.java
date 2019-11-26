package com.example.dungeonescape.platformer;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.dungeonescape.game.GameObject;

import java.util.Random;

class PlatformerObject extends GameObject {
    private int size;
    private Rect shape;
    private PlatformerManager manager;

    PlatformerObject(int x, int y, int size, PlatformerManager manager) {
        super(x,y);
        this.size = size;
        this.manager = manager;
    }


    int getSize() {
        return this.size;
    }

    void setShape() {
        this.shape = new Rect(getX() - size / 2, getY() - size / 2,
                getX() + size / 2, getY() + size / 2);
    }
    void setShape(int length, int width) {
        this.shape = new Rect(getX(), getY(), length + getX(),
                getY()+ width);
    }
    Rect getShape() {
        return this.shape;
    }

    void draw(Canvas canvas) {
        if (this instanceof Platforms) {
            canvas.drawRect(getShape(), getPaint());
        }
        else {
            canvas.drawOval(getX() - size / 2, getY() - size / 2, getX() + size / 2,
                    getY() + size / 2, getPaint());
        }

    }
    PlatformerManager getManager() {
        return this.manager;
    }



}
