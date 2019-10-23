package com.example.dungeonescape;

/**
 * Represents a Player in the Game.
 */
public class Player extends GameObject {
    /** The Player's name. */
    private String name;

    /** The Player's score. */
    private int score;

    /** The number of lives this Player has. */
    private int numLives;

    public Player(int x, int y, int z, String name) {
        super(x, y, z);
        this.name = name;
    }

    public String getName() {
        return name;
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
}
