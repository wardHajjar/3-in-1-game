package com.example.dungeonescape.platformer;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;

import java.util.*;


class PlatformerManager {

    /**
     * The width of canvas.
     */
    private int lives;
    private int gridWidth;
    /**
     * The height of canvas.
     */
    private int gridHeight;
    private ArrayList<Platforms> platforms;
    private Character character;

    int getGridWidth() {
        return gridWidth;
    }
    ArrayList<Platforms> getPlatforms() {
        return platforms;
    }
    int getGridHeight() {
        return gridHeight;
    }
    Character getCharacter() {
        return character;
    }
    int getCharacterScore(){
        return character.getGamescore();
    }

    /**
     * The fish tank manager on a screen with height rows and width columns.
     */
    PlatformerManager() {
        character = new Character(50,1000,100, this);
        platforms = createPlatforms();
        gridHeight = 1684;

    }

    private ArrayList<Platforms> createPlatforms() {
        int h = 1684;
        int w = 1080;

        ArrayList<Platforms> arr = new ArrayList<>(15);
        for (int i = 1; i <= 8; i++) {
            Random random = new Random();
            int a = random.nextInt(1080- 150);
            arr.add(new Platforms(a, h*i/10, 150, 30, this));
        }

        return arr;

    }

    void collision_detection() {
        character.collision_detection();
        if(character.collision_detection()){
            character.setGamescore(character.getGamescore()+1);
        }
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


    boolean update() {

        character.move();
        boolean alive = character.isAlive();
        if (!alive) {
            return false;
        }
        collision_detection();
        if (character.getY() < 550) {
            int diff = Math.abs(550 - (int) character.getY());
            character.setY(549);


            for (int i = 0; i < platforms.size(); i++) {
                platforms.get(i).update(diff);
            }

        }
        return true;
    }

}
