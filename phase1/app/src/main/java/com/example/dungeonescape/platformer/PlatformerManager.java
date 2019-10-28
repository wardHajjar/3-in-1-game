package com.example.dungeonescape.platformer;

import android.graphics.Canvas;

import java.util.ArrayList;


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
        character = new Character(50,50,100);

    }

    void draw(Canvas canvas) {
//        for (int i = 0; i < platforms.size(); i++) {
//            platforms.get(i).draw(canvas);
//        }
        character.move(canvas);
        character.draw(canvas);
        gridHeight = canvas.getHeight();
        gridWidth = canvas.getWidth();
    }

    void update(Canvas canvas) {

//        for (int i = 0; i < platforms.size(); i++) {
//            platforms.get(i).draw(canvas);
//        }
//        character.update(canvas);
    }



}
