package com.example.dungeonescape.platformer;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;


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

    PlatformerManager() {
        character = new Character(50,50,100);
        for (int i=0; i<18; i++){
            yPositions.add(i*10);
            xPositions.add(Math.random()*500);
        }
        for (int i=0; i<18; i++){
            platforms.add(new Platforms(xPositions.get(i), yPositions.get(i), 200, 50, this))

    }

    void draw(Canvas canvas){
//        for (int i = 0; i < platforms.size(); i++) {
//            platforms.get(i).draw(canvas);
//        }
        character.move(canvas);
        character.draw(canvas);
        gridHeight = canvas.getHeight();
        gridWidth = canvas.getWidth();
    }

    void update(Canvas canvas) {

        for (int i = 0; i < platforms.size(); i++) {
            platforms.get(i).update(canvas);
        }
        character.update(canvas);
    }



}
