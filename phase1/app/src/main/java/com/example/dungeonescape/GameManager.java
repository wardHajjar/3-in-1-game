package com.example.dungeonescape;

/**
 * Represents the Game Manager.
 *
 * It controls the statistics that determines pass/fail conditions.
 *
 * TODO: Edit this javadoc as this class is updated.
 */
public class GameManager {
    private int totalTimePlayed;
    private Player player = new Player(0, 0, 0, "player 1");

    public GameManager() {
        /* Sets the initial total time elapsed in the Game to 0. */
        totalTimePlayed = 0;
    }
    public void addCoin() {
        int newCoinAmount = player.getNumCoins();
        player.setNumCoins(newCoinAmount + 1);
    }
    public void loseLife() {
        int currLives = player.getNumLives();
        player.setNumLives(currLives - 1);
    }

    public int getTotalTime() {
        return totalTimePlayed;
    }

    /** As each second passes, increment the totalTime elapsed.
     *
     * TODO: Include this in whichever main update() method we use.
     */
    public void updateTotalTime() {
        /* TODO: implement time function. */
    }
}
