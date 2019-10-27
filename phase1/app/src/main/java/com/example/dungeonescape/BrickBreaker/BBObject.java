package com.example.dungeonescape.BrickBreaker;

/*
Class that creates a brick breaker object.
 */
public class BBObject {
    /*
    the x and y coordinates of the object's location
     */
    private int x, y;

    /*
    Method initializes a brick breaker object with a location at x, y.
     */
    protected BBObject(int x, int y){
        this.x = x;
        this.y = y;
    }

    /*
    Setter method that changes the x coordinate of the object's location.
     */
    protected void setX(int x){
        this.x = x;
    }

    /*
    Getter method that returns the x coordinate of the object's location.
     */
    protected int getX(){
        return this.x;
    }

    /*
    Setter method that changes the y coordinate of the object's location.
     */
    protected void setY(int y){
        this.y = y;
    }

    /*
    Getter method that returns the y coordinate of the object's location.
     */
    protected int getY(){
        return this.y;
    }
}
