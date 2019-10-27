package com.example.dungeonescape.BrickBreaker;


import android.graphics.Canvas;
import android.graphics.Paint;
/**
 * Abstract Class that creates a brick breaker object.
*/
abstract class BBObject {
    /**
     * the x and y coordinates of the object's location
     */
    int x, y;
    /**
     * Paint object that describes the colors and styles for drawing.
     */
    Paint paintStyle;
    /**
     * Method initializes a brick breaker object.
     *
     * @param x the x coordinate of the new object's location.
     * @param y the y coordinate of the new object's location.
     */
    BBObject(int x, int y){
        this.x = x;
        this.y = y;
        this.paintStyle = new Paint();
    }

    /**
     * Setter method that changes the x coordinate of the object's location.
     *
     * @param x the new x coordinate of the object's location.
     */
    void setX(int x){
        this.x = x;
    }

    /**
     * Getter method that returns the x coordinate of the object's location.
     */
    int getX(){
        return this.x;
    }

    /**
     * Setter method that changes the y coordinate of the object's location.
     *
     * @param y the new y coordinate of the object's location.
     */
    void setY(int y){
        this.y = y;
    }

    /**
     * Getter method that returns the y coordinate of the object's location.
     */
    int getY(){
        return this.y;
    }

    /**
     * Draws the brick breaker object on screen.
     *
     * @param canvas the graphic context on which the object is drawn
     */
    abstract void draw(Canvas canvas);
}
