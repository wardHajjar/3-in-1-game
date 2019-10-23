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

    public GameManager() {
        /* Sets the initial total time elapsed in the Game to 0. */
        setTotalTime(0);
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    /** As each second passes, increment the totalTime elapsed.
     *
     * TODO: Include this in whichever main update() method we use.
     */
    public void updateTotalTime() {
        /* TODO: implement time function. */
    }
}
