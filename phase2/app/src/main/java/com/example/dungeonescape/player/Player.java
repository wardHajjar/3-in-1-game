package com.example.dungeonescape.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Color;

import com.example.dungeonescape.game.GameObject;
import com.example.dungeonescape.game.collectable.Coin;
import com.example.dungeonescape.game.collectable.Collectable;
import com.example.dungeonescape.game.collectable.Gem;

import androidx.annotation.NonNull;

/**
 * Represents a Player in the Game.
 */
public class Player implements Serializable {

    /** The Player's name. */
    private String name;

    /** The Player's score. */
    private int score;
    /** The Player's current level. */
    private int currentLevel;

    /** The number of lives this Player has. */
    private int numLives;

    /** The number of coins this Player has. */
    private int numCoins;

    /** The colour of the user's character. */
    private int colour;

    /** An Arraylist of the Collectable GameObjects this Player has. */
    private Map<String, Integer> satchel = new HashMap<>();;

    /** The Game Difficulty. Acts as a modifier. */
    private int gameDifficulty = 1;

    /** The total time that the character has been playing the game for. */
    private long totalTimePlayed;

    private Map<String, Integer> highScore = new HashMap<>();

    public Player(String name) {
        setName(name);
        setScore(0);
        setNumCoins(0);
        setHighScore();
        setColour(Color.WHITE);
        setCurrentLevel(1);
        totalTimePlayed = 0;
        initSatchel();

    }
    /** Initializes the Satchel. */
    private void initSatchel(){
        satchel.put("Coins", 0);
        satchel.put("Gems", 0);
    }

    /** Adds a Collectable GameObject to the Player's satchel. */
    public void addToSatchel(Collectable collectable) {
        if (collectable instanceof Coin) {
            satchel.put("Coins", satchel.get("Coins") + 1);
            addCoin();
        }
        else if (collectable instanceof Gem) {
            satchel.put("Gems", satchel.get("Coins") + 1);
        }
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
    public int getCurrentLevel() {
        return this.currentLevel;
    }

    /** Adds 1 coin to this Player. */
    public void addCoin(){
        setNumCoins(getNumCoins() + 1);
    }

    /** Causes this Player to lose one life. */
    public void loseLife() {
        numLives -= 1;
    }

    /** Causes this Player to gain one life. */
    public void addLife() {numLives += 1;}

    /** Returns player name */
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

    //return the score of the player.
    public int getScore() {
        return score;
    }

    public int getGameDifficulty() {
        return gameDifficulty;
    }

    /** Sets the gameDifficulty value to an integer.
     *
     * @param difficulty the String difficulty.
     */
    public void setGameDifficulty(String difficulty) {
        if (difficulty.equals("Easy")) {
            this.gameDifficulty = 1;
        } else if (difficulty.equals("Hard")) {
            this.gameDifficulty = 2;
        }
        updatePlayerData();
    }

    private void updatePlayerData() {
        setNumLives((int) Math.ceil(5.0 / gameDifficulty));
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
     *
     * @param colour the new color to set the character to.
     */
    public void setColour(int colour) {
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
     * @return totalTimePlayed
     */
    public long getTotalTime() {
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

    public long totalMinutes(){
        return (totalTimePlayed / 1000) / 60;
    }

    public long totalSeconds(){
        return (totalTimePlayed / 1000) % 60;
    }

    private void resetTime() {
        totalTimePlayed = 0;
    }

    /** Reset the player's coins and lives to default values. */
    public void resetStats() {
        setScore(0);
        setNumCoins(0);
        setCurrentLevel(1);
        resetTime();
        updatePlayerData();
        initSatchel();

    }

    public Map<String, Integer> getSatchel() {
        return satchel;
    }

    private void setHighScore() {
        highScore.put("Lives", 0);
        highScore.put("Coins", 0);
        highScore.put("Time", 99999999);
    }

    public void setHighScore(List<Integer> score) {

        if (highScore.get("Lives") != null && highScore.get("Coins") != null && highScore.get("Time") != null) {
            if (score.get(0) > highScore.get("Lives")) {
                highScore.put("Time", score.get(2));
                highScore.put("Coins", score.get(1));
                highScore.put("Lives", score.get(0));
            }
            else if (score.get(0).equals(highScore.get("Lives"))) {
                if (score.get(1) > highScore.get("Coins")) {
                    highScore.put("Time", score.get(2));
                    highScore.put("Coins", score.get(1));
                    highScore.put("Lives", score.get(0));
                }
            }
            else if (score.get(0).equals(highScore.get("Lives")) && score.get(1) > highScore.get("Coins")) {
                if (score.get(2) < highScore.get("Time")) {
                    if (score.get(2) > 0) {
                        highScore.put("Time", score.get(2));
                    }
                    highScore.put("Coins", score.get(1));
                    highScore.put("Lives", score.get(0));
                }
            }
        }

    }
    public Map<String, Integer> getHighScore() {
        return highScore;
    }
}
