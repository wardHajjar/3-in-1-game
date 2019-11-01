package com.example.dungeonescape;

import java.io.Serializable;
import java.util.ArrayList;

import android.graphics.Color;

/**
 * Represents a Player in the Game.
 */
public class Player extends GameObject implements Serializable {

    /** The Player's name. */
    private String name;

    /** The Player's score. */
    private int score;
    private int currentLevel;
    /** The number of lives this Player has. */
    private int numLives;

    /** The number of coins this Player has. */
    private int numCoins;

    /**
     * The colour of the user's character
     */
    private int colour;

    /**
     * The difficulty level that the user has chosen.
     * Possible inputs: "Easy" or "Hard"
     */
    private String difficulty;

    /**
     * The total time that the character has been playing the game for.
     */
    private long totalTimePlayed;

    public Player(int x,int y,int z, String name) {
        super(0,0,0);
        setName(name);
        setScore(0);
        setNumLives(5);
        setNumCoins(0);
        setColour(Color.WHITE);
        setCurrentLevel(1);
        totalTimePlayed = 0;
    }
    public Player(String name){
        setName(name);
        setScore(0);
        setNumLives(5);
        setNumCoins(0);
        setColour(Color.WHITE);
        setCurrentLevel(1);
    }

    /** Sets this Player's Current Level. */
    public void setCurrentLevel (int level) {
        this.currentLevel = level;
    }

    /** Gets this Player's Current level. */
    public int getCurrentLevel () {
        return this.currentLevel;
    }

    /** Adds 1 coin to this Player. */
    public void addCoin(){
        setNumCoins(getNumCoins() + 1);
    }

    /** Causes this Player to lose one life. */
    public void loseLife() {
        setNumLives(getNumLives() - 1);
    }

    public String getName() {
        return name;
    }

    /**
     * Sets the Player's name.
     *
     * @param name the Player's updated name.
     */
    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(String diff) {
        this.difficulty = diff;
    }

    /**
     * Sets the Player's score.
     *
     * @param score the Player's updated score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    public int getNumLives() {
        return numLives;
    }

    /**
     * Sets the Player's number of lives.
     *
     * @param numLives the new number of lives.
     */
    public void setNumLives(int numLives) {
        this.numLives = numLives;
    }

    public int getNumCoins() {
        return numCoins;
    }

    /**
     * Sets the Player's number of coins.
     *
     * @param numCoins the new number of coins.
     */
    public void setNumCoins(int numCoins) {
        this.numCoins = numCoins;
    }

    /**
     * Sets the color of the player's character.
     * @param colour the new color to set the character to.
     */
    public void setColour(int colour) {
        this.colour = colour;
    }

    /**
     * Returns the player's character colour.
     * @return integer value of the player's color.
     */
    public int getColour() {
        return this.colour;
    }

    /**
     * Returns the total time that the player has been playing for.
     * @return totalTimePlayed
     */
    public long getTotalTime() {
        return totalTimePlayed;
    }

    /**
     * Increments the total time that the player has been playing for.
     * @param timeElapsed increment to increase time by.
     */
    public void updateTotalTime(long timeElapsed) {
        totalTimePlayed = totalTimePlayed + timeElapsed;
    }

    /**
     * Reset the player's coins and lives to default values.
     */
    public void resetStats() {
        setScore(0);
        setNumCoins(0);
        setCurrentLevel(1);
        if (this.getDifficulty().equals("Easy")) {
            setNumLives(5);
        } else if (this.getDifficulty().equals("Hard")) {
            setNumLives(3);
        }
    }
}
