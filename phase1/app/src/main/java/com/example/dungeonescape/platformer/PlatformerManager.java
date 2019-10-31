package com.example.dungeonescape.platformer;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.dungeonescape.Player;

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
    private ArrayList<Coin> coins;
    Player player;

    int getCharacterLives(){
        return character.getLives();
    }
    void setCharacterLives(int lives){
        character.setLives(lives);
    }

    int getGridWidth() {
        return gridWidth;
    }
    ArrayList<Platforms> getPlatforms() {
        return platforms;
    }
    int getGridHeight() {
        return gridHeight;
    }
    ArrayList<Coin> getCoins() {
        return coins;
    }

    void setPlayer(Player player){
        player.setNumCoins(this.player.getNumCoins() + player.getNumCoins());
        if (!(this.player.getNumLives() == 5)) {
            player.setNumLives(player.getNumLives() - (5 - this.player.getNumLives()));
        }
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    int getCharacterScore(){
        return character.getGamescore();
    }

    /**
     * Platform manager on a screen with characters and platforms.
     */
    PlatformerManager() {
        character = new Character(50,1000,100, this);
        player = new Player("temp");
        gridHeight = 1684;
        gridWidth = 1080;
        platforms = createPlatforms();
        coins = new ArrayList<>(2);
        coins.add(new Coin(300,300,60, this));
        coins.add(new Coin(70,1000,60, this));

    }

    private ArrayList<Platforms> createPlatforms() {
        int h = gridHeight;

        ArrayList<Platforms> arr = new ArrayList<>(15);
        for (int i = 1; i <= 8; i++) {
            Random random = new Random();
            int a = random.nextInt(gridWidth- 150);
            arr.add(new Platforms(a, h*i/10, 150, 30, this));
        }
        return arr;
    }


    void draw(Canvas canvas) {

        for (int i = 0; i < platforms.size(); i++) {
            platforms.get(i).draw(canvas);
        }
        character.draw(canvas);
        gridHeight = canvas.getHeight();
        gridWidth = canvas.getWidth();
        for (int i = 0; i < coins.size(); i++) {
            coins.get(i).draw(canvas);
        }
    }
    void left_button() {
        character.move_left();
    }
    void right_button() {
        character.move_right();
    }
    boolean update() {

        character.move();
        character.coin_detection();
        boolean alive = character.isAlive();
        if (!alive) {
            player.loseLife();
            return false;
        }

        if (character.getY() < 550) {
            int diff = Math.abs(550 - (int) character.getY());
            character.setY(549);

            for (int i = 0; i < coins.size(); i++) {
                coins.get(i).update(diff);
            }
            for (int i = 0; i < platforms.size(); i++) {
                platforms.get(i).update(diff);
            }
        }
        return true;
    }

    boolean finishedLevel() {
        return (character.getGamescore() > 10);
    }

}
