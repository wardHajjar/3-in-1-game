package com.example.dungeonescape.platformer;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.example.dungeonescape.game.GameObject;


class PlatformerObject extends GameObject {
    /**
     * The size of the object.
     */
    private int size;
    /**
     * The rect shape of the object.
     */
    private Rect shape;
    /**
     * The manager for this object.
     */
    private PlatformerManager manager;

    PlatformerObject(int x, int y, int size, PlatformerManager manager) {
        super(x, y);
        this.size = size;
        this.manager = manager;
    }

    /**
     * @return size of the object
     */
    int getSize() {
        return this.size;
    }

    /**
     * Sets the rectangle for this object
     */
    void setShape() {
        this.shape = new Rect(getX() - size / 2, getY() - size / 2,
                getX() + size / 2, getY() + size / 2);
    }
    /**
     * @param length the length of the shape.
     * @param width the length of the shape.
     * Sets the rectangle for this object with defined length and width.
     */
    void setShape(int length, int width) {
        this.shape = new Rect(getX(), getY(), length + getX(),
                getY()+ width);
    }
    /**
     * @return shape of object.
     */
    Rect getShape() {
        return this.shape;
    }

    /**
     * Draws this item to canvas.
     */

    public void draw(Canvas canvas) {
        if (this instanceof Platforms) {
            canvas.drawRect(getShape(), getPaint());
        }
        else {
            canvas.drawOval(getX() - size / 2, getY() - size / 2, getX() + size / 2,
                    getY() + size / 2, getPaint());
        }
    }
    /**
     * @return the manager.
     */
    PlatformerManager getManager() {
        return this.manager;
    }

}
