package com.example.dungeonescape;

import java.io.Serializable;

/**
 * Represents a Player in the Game.
 */
public class Player implements Serializable {

    /** The Player's name. */
    private String name;

    /** The Player's score. */
    private int score;

    /** The number of lives this Player has. */
    private int numLives;

    /** The number of coins this Player has. */
    private int numCoins;

    public Player(String name) {
        setName(name);
        setScore(0);
        setNumLives(5);
        setNumCoins(0);
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

    public void addCoin(){
        this.numCoins += 1;
    }
}
