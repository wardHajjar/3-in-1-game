package com.example.dungeonescape.Maze;

import com.example.dungeonescape.GameManager;

/**
 * Represents the Maze Game Manager.
 *
 * It controls the statistics that determines pass/fail conditions.
 *
 * TODO: Edit this javadoc as this class is updated.
 */
public class MazeGameManager extends GameManager {

    /** The maximum time the Player has to complete this Maze.
     * If the player takes longer than the maxTime to complete the Maze, they automatically lose.
     * */
    private int maxTime;

    public MazeGameManager(int maxTime) {
        super();
        setMaxTime(maxTime);
    }

    public int getMaxTime() {
        return maxTime;
    }

    /**
     * Sets the Maze Game's maximum time allowed.
     *
     * @param maxTime the Maze Game's new maximum time.
     */
    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }
}
