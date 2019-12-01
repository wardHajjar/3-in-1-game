package com.example.dungeonescape.maze;

/** Contains all data for the Maze Game. */
class MazeData {
    /** The number of columns and rows in this Maze. */
    private int numMazeCols;
    private int numMazeRows;

    /** The size of each MazeCell in pixels. */
    private float cellSize;

    /** The horizontal and vertical margin from the edge of the screen to the walls of the maze */
    private float horizontalPadding;
    private float verticalPadding;

    MazeData(){}

    /** Calculates the cellSize based on the screen's dimensions.
     *
     * @param screenWidth the width of the phone screen in pixels.
     * @param screenHeight the height of the phone screen in pixels.
     */
    void calculateCellSize(int screenWidth, int screenHeight) {
        float newCellSize;
        float screenWidthDivHeight = screenWidth / screenHeight;
        float mazeColsDivRows = numMazeCols / numMazeRows;

        if (screenWidthDivHeight < mazeColsDivRows) {
            newCellSize = screenWidth / (numMazeCols + 1);
        } else {
            newCellSize = screenHeight / (numMazeRows + 1);
        }

        setCellSize(newCellSize);
    }

    /**
     * Calculates the cell's horizontal padding based on the screen's width and calculated cell size.
     *
     * @param screenWidth the width of the phone screen in pixels.
     */
    void calculateCellHorizontalPadding(float screenWidth) {
         setHorizontalPadding((screenWidth - (numMazeCols * cellSize)) / 2);
    }

    /**
     * Calculates the cell's vertical padding based on the screen's height and calculated cell size.
     *
     * @param screenHeight the height of the phone screen in pixels.
     */
    void calculateCellVerticalPadding(float screenHeight) {
        setVerticalPadding((screenHeight - (numMazeRows * cellSize)) / 2);
    }

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

    private void setCellSize(float cellSize) {
        this.cellSize = cellSize;
    }

    float getCellSize() {
        return this.cellSize;
    }

    float getHorizontalPadding() {
        return this.horizontalPadding;
    }

    private void setHorizontalPadding(float horizontalPadding) {
        this.horizontalPadding = horizontalPadding;
    }

    float getVerticalPadding() {
        return this.verticalPadding;
    }

    private void setVerticalPadding(float verticalPadding) {
        this.verticalPadding = verticalPadding;
    }
}
