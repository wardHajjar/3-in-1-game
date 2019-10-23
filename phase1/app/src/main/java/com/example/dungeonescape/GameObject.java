package com.example.dungeonescape;

/**
 * Represents any Object in the Game that contains an (x, y, z) coordinate.
 */
public class GameObject {
    private int x;
    private int y;
    private int z;

    public GameObject(int x, int y, int z) {
        setX(x);
        setY(y);
        setDepth(z);
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

    public int getZ() {
        return z;
    }

    /**
     * Sets the Game Object's depth to the given value.
     *
     * @param z the z-coordinate of the game object's location.
     */
    public void setDepth(int z) {
        this.z = z;
    }
}
