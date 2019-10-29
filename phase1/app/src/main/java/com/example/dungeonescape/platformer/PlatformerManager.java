package com.example.dungeonescape.platformer;

import android.graphics.Canvas;

import java.util.*;


class PlatformerManager {

    /**
     * The width of myLittleFishes.
     */
    private int gridWidth;
    /**
     * The height of myLittleFishes.
     */
    private int gridHeight;
    private ArrayList<Platforms> platforms;
    private Character character;
    private List<Integer> yPositions = new ArrayList<>();
    private List<Double> xPositions = new ArrayList<>();

    int getGridWidth() {
        return gridWidth;
    }

    int getGridHeight() {
        return gridHeight;
    }

    /**
     * The fish tank manager on a screen with height rows and width columns.
     */
    PlatformerManager() {
        character = new Character(50,50,100, this);
        for (int i=0; i<18; i++){
            yPositions.add(i*10);
            xPositions.add(Math.random()*500);
        }
        platforms = new ArrayList<>();
//        for (int i=0; i<18; i++){
//            platforms.add(new Platforms(xPositions.get(i), yPositions.get(i), 200, 50,
//                    this));

//        }


    }

    void draw(Canvas canvas) {
        for (int i = 0; i < platforms.size(); i++) {
            platforms.get(i).draw(canvas);
        }
        character.draw(canvas);
        gridHeight = canvas.getHeight();
        gridWidth = canvas.getWidth();
    }
    void left_button() {
        character.move_left();
    }
    void right_button() {
        character.move_right();
    }

    void move_down() {
        for (int i = 0; i < platforms.size(); i++) {
            platforms.get(i).platformDown();
        }
    }

    void update() {

        for (int i = 0; i < platforms.size(); i++) {
            platforms.get(i).update();
        }
        character.move();
    }



}
