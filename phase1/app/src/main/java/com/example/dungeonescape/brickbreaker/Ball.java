package com.example.dungeonescape.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Rect;
/**
 * Class that creates the ball used in the brick breaker game.
 * Ball has the functionality of bouncing from walls and bricks and breaking breaks.
 */
public class Ball extends BBObject {
    /**
     * The ball's speed in the x and y directions, respectively.
     */
    private int x_speed, y_speed;

    /**
     * Initializes the ball at a given centre with a specified speed in the x and y directions.
     *
     * @param x_loc   the x coordinate of the ball's centre.
     * @param y_loc   the y coordinate of the ball's centre.
     * @param x_speed the speed of the ball in the x direction.
     * @param y_speed the speed of the ball in the y direction.
     */
    public Ball(int x_loc, int y_loc, int x_speed, int y_speed) {
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
    public void setY_speed(int y_speed) {

        this.y_speed = y_speed;
    }

    /**
     * Getter method that returns the ball's speed in the x direction.
     * @return x_speed attrivute.
     */
    public int getX_speed(){
        return x_speed;
    }

    /**
     * Getter method that return the ball's speed in the y direction.
     * @return y_speed attribute.
     */
    public int getY_speed(){
        return y_speed;
    }


    /**
     * Moves the ball in the x and y directions with their corresponding speeds.
     */
    public void move(long fps) {
        System.out.println("HERETSVSJFHASF");
//        this.x = this.x + (int)(this.x_speed/fps);
//        this.y = this.y + (int)(this.y_speed/fps);
        this.x = this.x + this.x_speed;
        this.y = this.y + this.y_speed;
    }

    /**
     * Draws the ball object as a black circle.
     *
     * @param canvas the graphic context on which the object is drawn
     */
    @Override
    public void draw(Canvas canvas) {
        this.paintStyle.setStyle(Paint.Style.FILL);
        this.paintStyle.setColor(Color.WHITE);
        int radius = 25;
        canvas.drawCircle(this.x, this.y, radius, paintStyle);
    }

    /**
     * Determines if the ball made a collision with a wall.
     *
     * @param width  the screen width
     * @param height the screen height
     * @return character which represents which movement direction to reverse.
     */
    public char madeWallCollision(int width, int height) {
        int newXPos = x + x_speed;
        int newYPos = y + y_speed;
        if (newXPos <= 0 || newXPos >= width) {
            return 'x';
        } else if (newYPos <= 0 || newYPos >= height) {
            return 'y';
        }
        return ' ';
    }

    /** Determines if the ball made a collision with a rectangular obstacle.
     * @param obstacle the Rect that represents the obstacle.
     * @return character which represents which movement direction to reverse.
     */
    public char madeRectCollision(Rect obstacle) {
        int topLeftX = obstacle.left;
        int topLeftY = obstacle.top;
        int width = obstacle.width();
        int height = obstacle.height();
        int newXPos = x + x_speed;
        int newYPos = y + y_speed;
        if (newXPos >= topLeftX && newXPos <= topLeftX + width) {
            if (newYPos <= topLeftY || newYPos >= topLeftY + height) {
                return 'y';
            }
        } else if (newYPos >= topLeftY && newYPos <= topLeftY + height) {
            if (newXPos <= topLeftX || newXPos >= topLeftX + width) {
                return 'x';
            }
        }
        return ' ';
    }
}
