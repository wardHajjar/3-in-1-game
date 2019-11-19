package com.example.dungeonescape.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Rect;
import java.util.Random;
/**
 * Class that creates the ball used in the brick breaker game.
 * Ball has the functionality of bouncing from walls and bricks and breaking breaks.
 */
public class Ball extends BBObject {
    /**
     * The ball's speed in the x and y directions, respectively.
     */
    private int xSpeed, ySpeed;
    private int colour;

    /**
     * Initializes the ball at a given centre with a specified speed in the x and y directions.
     *
     * @param x_loc   the x coordinate of the ball's centre.
     * @param y_loc   the y coordinate of the ball's centre.
     * @param xSpeed the speed of the ball in the x direction.
     * @param ySpeed the speed of the ball in the y direction.
     */
    public Ball(int x_loc, int y_loc, int xSpeed, int ySpeed, int colour) {
        super(x_loc, y_loc);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.colour = colour;
    }


    /**
     * Setter method that changes the ball's speed in the x direction.
     *
     * @param x_speed the new speed in the x direction.
     */
    public void setXSpeed(int x_speed) {
        this.xSpeed = x_speed;
    }

    /**
     * Setter method that changes the ball's speed in the y direction.
     *
     * @param y_speed the new speed in the y direction.
     */
    public void setYSpeed(int y_speed) {

        this.ySpeed = y_speed;
    }

    /**
     * Sets the speed of the ball in the x direction as a random speed with a max of y_speed - 1
     * and min speed of 1.
     */
    void setRandomXSpeed(){
        Random random = new Random();
        xSpeed = random.nextInt((Math.abs(ySpeed) - 15)) + 15;
        System.out.printf("%d", xSpeed);
        System.out.println(" ");
    }

    /**
     * Getter method that returns the ball's speed in the x direction.
     * @return xSpeed attrivute.
     */
    public int getXSpeed(){
        return xSpeed;
    }

    /**
     * Getter method that return the ball's speed in the y direction.
     * @return ySpeed attribute.
     */
    public int getYSpeed(){
        return ySpeed;
    }


    /**
     * Moves the ball in the x and y directions with their corresponding speeds.
     */
    public void move() {
        this.x = this.x + this.xSpeed;
        this.y = this.y + this.ySpeed;
    }

    /**
     * Draws the ball object as a black circle.
     *
     * @param canvas the graphic context on which the object is drawn
     */
    @Override
    public void draw(Canvas canvas) {
        this.paintStyle.setStyle(Paint.Style.FILL);
        this.paintStyle.setColor(colour);
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
    String madeWallCollision(int width, int height) {
        int newXPos = x + xSpeed;
        int newYPos = y + ySpeed;
        if (newXPos <= 0 || newXPos >= width) {
            return "x";
        } else if (newYPos >= height) {
            return "y";
        } else if(newYPos <= 0) {
            return "win";
        }
        return " ";

    }

    /** Determines if the ball made a collision with a rectangular obstacle.
     * @param obstacle the Rect that represents the obstacle.
     * @return String which represents which movement direction to reverse.
     */
    String madeRectCollision(Rect obstacle) {

        Rect ballRect = getRect();
        Rect intersection = null;
        // calculating the rectangle of intersection between the obstacle and the ball.
        int leftX = Math.max(ballRect.left, obstacle.left);
        int rightX = Math.min(ballRect.right, obstacle.right);
        int topY = Math.max(ballRect.top, obstacle.top);
        int bottomY = Math.min(ballRect.bottom, obstacle.bottom);
        if (leftX < rightX && topY < bottomY){
            intersection = new Rect(leftX, topY, rightX, bottomY);
        }
        if (intersection != null){
            // if width of the intersection is greater than the height then the ball must have hit
            // the top/bottom of the obstacle.
            if (intersection.width() >= intersection.height()){
                return "y";
            }
            // if height of the intersection is greater than the width then the ball must have hit
            // the sides of the obstacle.
            else if (intersection.height() >= intersection.width()){
                System.out.println('x');
                return "x";
            }
        }
        return " ";
    }

    /**
     * Returns a rectangular representation of the object.
     * @return Rect object.
     */
    Rect getRect(){
        return new Rect(x - 25, y - 25, x + 25, y + 25);
    }

    /**
     * Sets the colour of the ball.
     * @param colour new colour to draw the ball in.
     */
    void setColour(int colour){
        this.colour = colour;
    }
}
