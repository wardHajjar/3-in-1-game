package com.example.dungeonescape.Maze;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Managers all (visible) Maze GameObjects
 */
public class MazeManager {

    private float cellSize;

    private Paint wallPaint;
    private Paint playerPaint;
    private Paint exitPaint;
    private Paint coinPaint;

    public MazeManager() {
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

    public Paint getWallPaint() {
        return wallPaint;
    }

    public Paint getPlayerPaint() {
        return playerPaint;
    }

    public Paint getExitPaint() {
        return exitPaint;
    }

    public Paint getCoinPaint() {
        return coinPaint;
    }

    /** Calculates the cellSize based on the screen's dimensions.
     *
     * @param screenWidth the width of the phone screen in pixels.
     * @param screenHeight the height of the phone screen in pixels.
     * @param numMazeCols the number of columns this Maze has.
     * @param numMazeRows the number of rows this Maze has.
     * @return the calculated cellSize.
     */
    public void calculateCellSize(int screenWidth, int screenHeight, int numMazeCols,
                                   int numMazeRows) {
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
     * @return the calculated horizontalPadding.
     */
    public float calculateCellHorizontalPadding(int screenWidth, int numMazeCols, float cellSize) {
        return (float) ((screenWidth - (numMazeCols * cellSize)) / 2);
    }

    /**
     * Calculates the cell's vertical padding based on the screen's height and calculated cell size.
     *
     * @param screenHeight the height of the phone screen in pixels.
     * @param numMazeRows the number of rows in this Maze.
     * @param cellSize the calculated size of the MazeCell.
     * @return the calculated verticalPadding.
     */
    public float calculateCellVerticalPadding(int screenHeight, int numMazeRows, float cellSize) {
        return (float) ((screenHeight - (numMazeRows * cellSize)) / 2);
    }

    public float getCellSize() {
        return cellSize;
    }

    private void setCellSize(float cellSize) {
        this.cellSize = cellSize;
    }

}
