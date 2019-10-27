package com.example.dungeonescape.Maze;

/**
 * Managers all (visible) Maze GameObjects
 */
public class MazeManager {

    public MazeManager() {

    }

    /** Calculates the cellSize based on the screen's dimensions.
     *
     * @param screenWidth the width of the phone screen in pixels.
     * @param screenHeight the height of the phone screen in pixels.
     * @param numMazeCols the number of columns this Maze has.
     * @param numMazeRows the number of rows this Maze has.
     * @return the calculated cellSize.
     */
    public float calculateCellSize(int screenWidth, int screenHeight, int numMazeCols,
                                   int numMazeRows) {
        float newCellSize;
        float screenWidthDivHeight = screenWidth / screenHeight;
        float mazeColsDivRows = numMazeCols / numMazeRows;

        if (screenWidthDivHeight < mazeColsDivRows) {
            newCellSize = screenWidth / (numMazeCols + 1);
        } else {
            newCellSize = screenHeight / (numMazeRows + 1);
        }

        return newCellSize;
    }

    /**
     * Calculates the cell's horizontal padding based on the screen's width and calculated cell size.
     * @param screenWidth the width of the phone screen in pixels.
     * @param numMazeCols the number of columns in this Maze.
     * @param cellSize the calculated size of the MazeCell.
     * @return the calculated horizontalPadding.
     */
    public float calculateCellHorizontalPadding(int screenWidth, int numMazeCols, int cellSize) {
        return (float) ((screenWidth - (numMazeCols * cellSize)) / 2);
    }
}
