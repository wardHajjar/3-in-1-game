package com.example.dungeonescape.maze;

import com.example.dungeonescape.game.GameObject;

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

    MazeCell(int col, int row) {
        super(col, row);
    }

    boolean isTopWall() {
        return topWall;
    }

    /**
     * Sets if the MazeCell has a topWall.
     *
     * @param topWall MazeCell's topWall.
     */
    void setTopWall(boolean topWall) {
        this.topWall = topWall;
    }

    boolean isBottomWall() {
        return bottomWall;
    }

    /**
     * Sets if the MazeCell has a bottomWall.
     *
     * @param bottomWall MazeCell's bottomWall.
     */
    void setBottomWall(boolean bottomWall) {
        this.bottomWall = bottomWall;
    }

    boolean isLeftWall() {
        return leftWall;
    }

    /**
     * Sets if the MazeCell has a leftWall.
     *
     * @param leftWall MazeCell's leftWall.
     */
    void setLeftWall(boolean leftWall) {
        this.leftWall = leftWall;
    }

    boolean isRightWall() {
        return rightWall;
    }

    /**
     * Sets if the MazeCell has a rightWall.
     *
     * @param rightWall MazeCell's rightWall.
     */
    void setRightWall(boolean rightWall) {
        this.rightWall = rightWall;
    }

    boolean isVisited() {
        return visited;
    }

    /**
     * Sets if the MazeCell has been visited.
     *
     * @param visited MazeCell's visit case.
     */
    void setVisited(boolean visited) {
        this.visited = visited;
    }

    public float getCellSize() {
        return cellSize;
    }

    /**
     * Sets the MazeCell's size.
     *
     * @param cellSize the MazeCell's cellSize.
     */
    public void setCellSize(float cellSize) {
        this.cellSize = cellSize;
    }
}
