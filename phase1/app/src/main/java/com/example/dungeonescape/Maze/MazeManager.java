package com.example.dungeonescape.Maze;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Managers all (visible) Maze GameObjects
 */
class MazeManager {

    private float cellSize;

    private Paint wallPaint;
    private Paint playerPaint;
    private Paint exitPaint;
    private Paint coinPaint;

    /** The horizontal and vertical margin from the edge of the screen to the walls of the maze */
    private float horizontalPadding;
    private float verticalPadding;

    MazeManager() {
        initializePaint();
    }

    private void initializePaint() {
        wallPaint = new Paint();
        wallPaint.setColor(Color.WHITE);
        wallPaint.setStrokeWidth(4);

        playerPaint = new Paint();
        playerPaint.setColor(Color.RED);

        exitPaint = new Paint();
        exitPaint.setColor(Color.BLUE);

        coinPaint = new Paint();
        coinPaint.setColor(Color.YELLOW);
    }

    Paint getWallPaint() {
        return wallPaint;
    }

    Paint getPlayerPaint() {
        return playerPaint;
    }

    Paint getExitPaint() {
        return exitPaint;
    }

    Paint getCoinPaint() {
        return coinPaint;
    }

    /** Calculates the cellSize based on the screen's dimensions.
     *
     * @param screenWidth the width of the phone screen in pixels.
     * @param screenHeight the height of the phone screen in pixels.
     * @param numMazeCols the number of columns this Maze has.
     * @param numMazeRows the number of rows this Maze has.
     */
    void calculateCellSize(int screenWidth, int screenHeight,
                           int numMazeCols, int numMazeRows) {
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
     * @param numMazeCols the number of columns in this Maze.
     * @param cellSize the calculated size of the MazeCell.
     */
    void calculateCellHorizontalPadding(int screenWidth, int numMazeCols, float cellSize) {
        float newHorizontalPadding = (screenWidth - (numMazeCols * cellSize)) / 2;
        setHorizontalPadding(newHorizontalPadding);
    }

    /**
     * Calculates the cell's vertical padding based on the screen's height and calculated cell size.
     *
     * @param screenHeight the height of the phone screen in pixels.
     * @param numMazeRows the number of rows in this Maze.
     * @param cellSize the calculated size of the MazeCell.
     */
    void calculateCellVerticalPadding(int screenHeight, int numMazeRows, float cellSize) {
        float newVerticalPadding = (screenHeight - (numMazeRows * cellSize)) / 2;
        setVerticalPadding(newVerticalPadding);
    }

    float getCellSize() {
        return cellSize;
    }

    /** Sets the cellSize in pixel for all MazeCells.
     *
     * @param cellSize the new cellSize.
     */
    private void setCellSize(float cellSize) {
        this.cellSize = cellSize;
    }

    float getHorizontalPadding() {
        return horizontalPadding;
    }

    /** Sets the horizontalPadding for this Maze.
     *
     * @param horizontalPadding the new horizontalPadding of the Maze.
     */
    private void setHorizontalPadding(float horizontalPadding) {
        this.horizontalPadding = horizontalPadding;
    }

    float getVerticalPadding() {
        return verticalPadding;
    }

    /** Sets the verticalPadding for this Maze.
     *
     * @param verticalPadding the new verticalPadding of the Maze.
     */
    private void setVerticalPadding(float verticalPadding) {
        this.verticalPadding = verticalPadding;
    }
}
