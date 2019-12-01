package com.example.dungeonescape.maze;

/** An Interface for any GameObjects that need to read from the MazeData class. */
interface RetrieveData {

    /** Sets the mazeData variable equal to the passed in MazeData instance.
     *
     * @param mazeData the instance of MazeData that this GameObject will read from.
     */
    void setMazeData(MazeData mazeData);
}
