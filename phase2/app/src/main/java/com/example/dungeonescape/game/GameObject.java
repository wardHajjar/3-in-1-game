package com.example.dungeonescape.game;

import android.graphics.drawable.shapes.RectShape;

/**
 * Represents any Object in the Game that contains an (x, y) coordinate.
 */
public class GameObject extends RectShape {
    private int x;
    private int y;

    public GameObject(){}

    public GameObject(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getX() {
        return x;
    }

    /**
     * Sets the Game Object's horizontal position to the given value.
     *
     * @param x the x-coordinate of the game object's location.
     */
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    /**
     * Sets the Game Object's vertical position to the given value.
     *
     * @param y the y-coordinate of the game object's location.
     */
    public void setY(int y) {
        this.y = y;
    }
}
