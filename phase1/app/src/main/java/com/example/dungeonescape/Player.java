package com.example.dungeonescape;

import java.io.Serializable;
import android.graphics.Color;

/** Represents a Player in the Game. */
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

    /** The colour of the user's character. */
    private int colour;

    /**
     * The difficulty level that the user has chosen.
     * Possible inputs: "Easy" or "Hard".
     */
    private String difficulty;

    /** The total time that the character has been playing the game for. */
    private long totalTimePlayed;

    /** Constructs a Player. */
    public Player() {
        setScore(0);
        setNumLives(5);
        setNumCoins(0);
        setColour(Color.WHITE);
        setCurrentLevel(1);
        totalTimePlayed = 0;
        setDifficulty("Easy");
    }

    public Player(String name) {
        setName(name);
        setScore(0);
        setNumLives(5);
        setNumCoins(0);
        setColour(Color.WHITE);
        setCurrentLevel(1);
        totalTimePlayed = 0;
        setDifficulty("Easy");
    }

    public Player(int x, int y, int z, String name) {
        super(x, y, z);
        setName(name);
        setScore(0);
        setNumLives(5);
        setNumCoins(0);
        setColour(Color.WHITE);
        setCurrentLevel(1);
        totalTimePlayed = 0;
        setDifficulty("Easy");
    }

    /**
     * Sets this Player's Current Level.
     *
     * @param level the new level.
     */
    public void setCurrentLevel (int level) {
        this.currentLevel = level;
    }

    /**
     * Returns this Player's Current level.
     *
     * @return the integer value of the Player's current level.
     */
    int getCurrentLevel() {
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

    /** Returns the Player's name */
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

    /** Returns the difficulty level that the Player has chosen. */
    private String getDifficulty() {
        return this.difficulty;
    }

    /**
     * Sets the Game difficulty.
     *
     * @param difficulty the String difficulty.
     */
    void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        if (difficulty.equals("Easy")) {
            setNumLives(5);
        } else {
            setNumLives(3);
        }
    }

    /**
     * Sets the Player's score.
     *
     * @param score the Player's updated score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /** Returns the number of lives the Player has left. */
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

    /** Returns the number of coins the Player has. */
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
     *
     * @param colour the new color to set the character to.
     */
    void setColour(int colour) {
        this.colour = colour;
    }

    /**
     * Returns the player's character colour.
     *
     * @return integer value of the player's color.
     */
    public int getColour() {
        return this.colour;
    }

    /**
     * Returns the total time that the player has been playing for.
     *
     * @return long value of totalTimePlayed
     */
    long getTotalTime() {
        return totalTimePlayed;
    }

    /**
     * Increments the total time that the player has been playing for.
     *
     * @param timeElapsed increment to increase time by.
     */
    public void updateTotalTime(long timeElapsed) {
        totalTimePlayed = totalTimePlayed + timeElapsed;
    }

    /** Reset the total time played by Player. */
    private void resetTime() {
        totalTimePlayed = 0;
    }

    /** Reset the player's coins and lives to default values based on the player's initial selection
     * of difficulty level.
     * */
    void resetStats() {
        setScore(0);
        setNumCoins(0);
        setCurrentLevel(1);
        resetTime();
        if (getDifficulty().equals("Easy")) {
            setNumLives(5);
        } else if (getDifficulty().equals("Hard")) {
            setNumLives(3);
        }
    }
}
