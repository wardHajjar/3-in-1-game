package com.example.dungeonescape;

/**
 * Represents the Game Manager.
 *
 * It controls the statistics that determines pass/fail conditions.
 *
 * TODO: Edit this javadoc as this class is updated.
 */
public class GameManager {
    private int totalTime;
    private int lives;
    private int coins;

    public GameManager() {
        /* Sets the initial total time elapsed in the Game to 0. */
        totalTime = 0;
        lives = 3;
        coins = 0;
    }
    public void add_coin() {
        coins += 1;
    }
    public void lost_life() {
        lives -= 1;
    }
    public int getTotalTime() {
        return totalTime;
    }

    int get_lives() {
        return lives;
    }
    int get_coins() {
        return coins;
    }

    /** As each second passes, increment the totalTime elapsed.
     *
     * TODO: Include this in whichever main update() method we use.
     */
    public void updateTotalTime() {
        /* TODO: implement time function. */
    }
}
