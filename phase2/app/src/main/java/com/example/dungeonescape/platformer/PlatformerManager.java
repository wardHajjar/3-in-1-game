package com.example.dungeonescape.platformer;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.example.dungeonescape.game.collectable.Coin;
import com.example.dungeonescape.player.Player;

import java.util.Random;

import java.util.*;

/**
 * Platform manager on a screen with characters and platforms.
 */
class PlatformerManager {

    /**
     * The width of canvas.
     */
    private int gridWidth;
    /**
     * The height of canvas.
     */
    private int gridHeight;
    /**
     * The list of platforms.
     */
    private List<Platforms> platforms;
    /**
     * The character for this game.
     */
    private Character character;
    /**
     * A list of coins.
     */
    private List<Coin> coins;
    /**
     * The player user.
     */
    private Player player;

    private Portal portal;

    private Drawable portalImage;

    private boolean enterPortal;

    private String gameMode;

    /**
     * Platform manager on a screen with characters and platforms.
     * @param h height of the screen.
     * @param w the width of the screen.
     */
    PlatformerManager(int h, int w) {

        character = new Character(50,1000,100, this);
        player = null;
        gridHeight = h - 344; //1684
        gridWidth = w; //1080
        platforms = createPlatforms();
        createCoins(3);
        gameMode = "Regular";

    }
    PlatformerManager(int h, int w, int coins) {

        character = new Character(50,1000,100, this);
        player = null;
        gridHeight = h - 344; //1684
        gridWidth = w; //1080
        platforms = createPlatforms();
        createCoins(coins);
        gameMode = "Blitz";
    }

    void createCoins(int number) {
        coins = new ArrayList<>(number);
        coins.add(new Coin(300,300,30));
        coins.add(new Coin(70,1000,30));
    }
    /**
     * @return gets the grid width.
     * */
    int getGridWidth() {
        return gridWidth;
    }
    List<Platforms> getPlatforms() {
        return platforms;
    }

    /**
     * @return gets the grid height.
     * */
    int getGridHeight() {
        return gridHeight;
    }
    List<Coin> getCoins() {
        return coins;
    }

    /**
     * Sets the player and his attributes
     * */
    void setPlayer(Player player){
        this.player = player;
        character.setColour(player.getColour());
    }

    /**
     * @return the player
     * */
    public Player getPlayer() {
        return player;
    }


    /**
     * @return the character scores
     * */
    int getCharacterScore(){
        return character.getGameScore();
    }

    /**
     * Creates platforms.
     */
    private List<Platforms> createPlatforms() {
        List<Platforms> arr = new ArrayList<>(15);
        for (int i = 1; i <= 8; i++) {
            Random random = new Random();
            int a = random.nextInt(gridWidth - 150);
            arr.add(new Platforms(a, gridHeight*i/10, 150, 30, this));
        }
        return arr;
    }
    /**
     * Draws the canvas screen.
     */
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
        if (portal != null && !gameMode.equals("Blitz")) {
            portal.draw(canvas);
        }
    }
    /**
     * Left and right buttons to move character
     */
    void left_button() {
        character.moveLeft();
    }
    void right_button() {
        character.moveRight();
    }

    /**
     * Updates the characters, platforms and coins.
     */
    void setImage(Drawable drawable) {
        portalImage = drawable;
    }

    Portal getPortal() {
        return this.portal;
    }
    boolean update() {

        character.move();
        character.coinDetection(coins);
        boolean alive = character.isAlive();
        if (portal != null) {
            enterPortal = character.portalDetection();
            if (enterPortal) {
                System.out.println("PORTAL");
            }
        }
        if (!alive) {
            player.loseLife();
            return false;
        }

        if (character.getY() < 550) {
            int diff = Math.abs(550 - (int) character.getY());
            character.setY(549);

            for (int i = 0; i < coins.size(); i++) {
                coins.get(i).update(diff, gridHeight);
            }
            for (int i = 0; i < platforms.size(); i++) {
                platforms.get(i).update(diff);
            }
            if (portal != null) {
                portal.moveDown(diff);
            }
        }

        if (character.getGameScore() == 2) {
            Random random = new Random();
            int a = random.nextInt(gridWidth - 150);
            portal = new Portal(a, -100, this, portalImage);
        }
        return true;
    }
    /**
     * Returns a boolean that indicates if the player has passed this level.
     */
    boolean finishedLevel() {
        return (character.getGameScore() > 10);
    }
    boolean enterPortal() {
        return enterPortal;

    }

    /**
     *  Returns a boolean that indicates if the player has lost all their lives.
     */
    boolean isDead(){ return (player.getNumLives() <= 0);}

}
