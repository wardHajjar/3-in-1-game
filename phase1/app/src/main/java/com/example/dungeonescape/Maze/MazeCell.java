package com.example.dungeonescape.Maze;

import com.example.dungeonescape.GameObject;

/**
 * Represents a MazeCell GameObject which is a square cell in the Maze.
 */
public class MazeCell extends GameObject {

    /** Assigns whether or not this cell as a wall on the specified side. */
    private boolean topWall = true;
    private boolean bottomWall = true;
    private boolean leftWall = true;
    private boolean rightWall = true;

    /** If this cell has been visited before. */
    private boolean visited = false;

    /** The size of this MazeCell in pixels. */
    private float cellSize;

    /** The inner margins of the MazeCell; prevents player from touching the walls. */
    private float horizontalPadding;
    private float verticalPadding;

    public MazeCell(int col, int row, int depth) {
        super(col, row, depth);
    }

    public boolean isTopWall() {
        return topWall;
    }

    public void setTopWall(boolean topWall) {
        this.topWall = topWall;
    }

    public boolean isBottomWall() {
        return bottomWall;
    }

    public void setBottomWall(boolean bottomWall) {
        this.bottomWall = bottomWall;
    }

    public boolean isLeftWall() {
        return leftWall;
    }

    public void setLeftWall(boolean leftWall) {
        this.leftWall = leftWall;
    }

    public boolean isRightWall() {
        return rightWall;
    }

    public void setRightWall(boolean rightWall) {
        this.rightWall = rightWall;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public float getCellSize() {
        return cellSize;
    }

    public void setCellSize(float cellSize) {
        this.cellSize = cellSize;
    }

    public float getHorizontalPadding() {
        return horizontalPadding;
    }

    public void setHorizontalPadding(float horizontalPadding) {
        this.horizontalPadding = horizontalPadding;
    }

    public float getVerticalPadding() {
        return verticalPadding;
    }

    public void setVerticalPadding(float verticalPadding) {
        this.verticalPadding = verticalPadding;
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
}
