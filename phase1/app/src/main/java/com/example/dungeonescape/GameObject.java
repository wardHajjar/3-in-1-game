package com.example.dungeonescape;

/** Represents any Object in the Game that contains an (x, y, z) coordinate. */
public class GameObject {
    private int x;
    private int y;
    private int z;

    public GameObject(int x, int y, int z) {
        setX(x);
        setY(y);
        setDepth(z);
    }
    GameObject(){
        setX(0);
        setY(0);
        setDepth(0);
    }

    /** Returns the x position of the game object. */
    public int getX() {
        return x;
    }

    /** Returns the y position of the game object.*/
    public int getY() {
        return y;
    }

    /**
     * Sets the Game Object's horizontal position to the given value.
     *
     * @param x the x-coordinate of the game object's location.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the Game Object's vertical position to the given value.
     *
     * @param y the y-coordinate of the game object's location.
     */
    public void setY(int y) {
        this.y = y;
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
