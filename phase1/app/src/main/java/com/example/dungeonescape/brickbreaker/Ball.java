package com.example.dungeonescape.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
/**
 * Class that creates the ball used in the brick breaker game.
 * Ball has the functionality of bouncing from walls and bricks and breaking breaks.
 */
public class Ball extends BBObject{
    /**
     * The ball's speed in the x and y directions, respectively.
     */
    private int x_speed, y_speed;
    /**
     * Initializes the ball at a given centre with a specified speed in the x and y directions.
     *
     * @param x_loc the x coordinate of the ball's centre.
     * @param y_loc the y coordinate of the ball's centre.
     * @param x_speed the speed of the ball in the x direction.
     * @param y_speed the speed of the ball in the y direction.
     */
    public Ball(int x_loc, int y_loc, int x_speed, int y_speed){
        super(x_loc, y_loc);
        this.x_speed = x_speed;
        this.y_speed = y_speed;
    }

    /**
     * Setter method that changes the ball's speed in the x direction.
     *
     * @param x_speed the new speed in the x direction.
     */
    public void setX_speed(int x_speed) {
        this.x_speed = x_speed;
    }

    /**
     * Setter method that changes the ball's speed in the y direction.
     *
     * @param y_speed the new speed in the y direction.
     */
    public void setY_speed(int y_speed){
        this.y_speed = y_speed;
    }

    /**
     * Moves the ball in the x and y directions with their corresponding speeds.
     */
    public void move(){
        this.x += this.x_speed;
        this.y += this.y_speed;
    }

    /**
     * Draws the ball object as a black circle.
     *
     * @param canvas the graphic context on which the object is drawn
     */
    @Override
    public void draw(Canvas canvas){
        this.paintStyle.setStyle(Paint.Style.FILL);
        this.paintStyle.setColor(Color.WHITE);
        int radius = 10;
        canvas.drawCircle(this.x, this.y, radius, paintStyle);
    }

}
