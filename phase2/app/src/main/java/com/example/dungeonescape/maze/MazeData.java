package com.example.dungeonescape.maze;

/** Contains all data for the Maze Game. */
class MazeData {
    /** The number of columns and rows in this Maze. */
    private int numMazeCols;
    private int numMazeRows;

    /** The size of each MazeCell in pixels. */
    private float cellSize;

    MazeData(){}

    void setNumMazeCols(int numMazeCols) {
        this.numMazeCols = numMazeCols;
    }

    int getNumMazeCols() {
        return this.numMazeCols;
    }

    void setNumMazeRows(int numMazeRows) {
        this.numMazeRows = numMazeRows;
    }

    int getNumMazeRows() {
        return this.numMazeRows;
    }

    void setCellSize(float cellSize) {
        this.cellSize = cellSize;
    }

    float getCellSize() {
        return this.cellSize;
    }
}
