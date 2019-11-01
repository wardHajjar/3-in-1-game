package com.example.dungeonescape;

import java.io.Serializable;

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

    private int colour;

    public Player(int x,int y,int z, String name) {
        super(0,0,0);
        setName(name);
        setScore(0);
        setNumLives(5);
        setNumCoins(0);
        setColour(Color.WHITE);
        setCurrentLevel(1);
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
    public void setColour(int colour){
        this.colour = colour;
    }

    /**
     * Returns the player's character colour.
     * @return integer value of the player's color.
     */
    public int getColour(){
        return this.colour;
    }

}
