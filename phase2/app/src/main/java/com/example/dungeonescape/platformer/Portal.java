package com.example.dungeonescape.platformer;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

class Portal extends PlatformerObject {

    private final Drawable portal;

    Portal(int x, int y, PlatformerManager manager, Drawable drawable) {
        super(x,y,200, manager);
        portal = drawable;
        setShape();
    }
    void draw(Canvas canvas) {
        portal.setBounds(getX() - 100, getY() - 100, getX() + 100, getY() + 100);
        portal.draw(canvas);
    }
    void moveDown(int down) {
        incY(down);
        this.setShape();
    }

}
