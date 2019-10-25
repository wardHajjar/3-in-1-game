package com.example.dungeonescape.Platformer;

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

    int getGridWidth() {
        return gridWidth;
    }

    int getGridHeight() {
        return gridHeight;
    }

    /**
     * The fish tank manager on a screen with height rows and width columns.
     */
    PlatformerManager(int height, int width) {
        gridHeight = height;
        gridWidth = width;

    }

//    void draw(Canvas canvas) {
//        for (int i = 0; i < platforms.size(); i++) {
//            platforms.get(i).draw(canvas);
//        }
//        character.draw(canvas);
//    }
//
//    void update() {
//
//        for (int i = 0; i < platforms.size(); i++) {
//            platforms.get(i).draw(canvas);
//        }
//    }

    void createTankItems() {

    }
//}

}
