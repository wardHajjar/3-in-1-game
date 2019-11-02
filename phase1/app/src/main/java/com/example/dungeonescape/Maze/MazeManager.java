package com.example.dungeonescape.Maze;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.SparseIntArray;

import com.example.dungeonescape.platformer.Coin;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Represents a manager class for this Maze Game.
 *
 * Calculates sizes for objects on screen. Creates MazeCells and Coins in their own arrays.
 */
class MazeManager {
    /* The size of each MazeCell in pixels. */
    private float cellSize;

    /* The number of columns and rows in this Maze. */
    private int numMazeCols;
    private int numMazeRows;

    /* All Paint objects on the screen. */
    private Paint wallPaint;
    private Paint playerPaint;
    private Paint exitPaint;
    private Paint coinPaint;

    /* Initialize a Random instance, used to randomize coin locations. */
    private Random rand = new Random();

    /** The horizontal and vertical margin from the edge of the screen to the walls of the maze */
    private float horizontalPadding;
    private float verticalPadding;

    MazeManager(int cols, int rows) {
        numMazeCols = cols;
        numMazeRows = rows;
        initializePaint();
    }

    /** Initializes the paint values for all Paint objects. */
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

    /**
     * Creates a Maze which populates a 2D Array of MazeCells.
     *
     * @return the 2D Array of MazeCells in this Maze.
     */
    MazeCell[][] createMaze(){
        Stack<MazeCell> stack = new Stack<>();
        MazeCell current, next;
        int mazeCols = getNumMazeCols();
        int mazeRows = getNumMazeRows();
        MazeCell[][] cells = new MazeCell[mazeCols][mazeRows];

        /* Creates a Maze x MazeCells wide and y MazeCells tall. Populates index of Array with
        * a new MazeCell. */
        for (int x = 0; x < mazeCols; x++) {
            for (int y = 0; y < mazeRows; y++) {
                cells[x][y] = new MazeCell(x, y, 1);
            }
        }

        /* Sets the current MazeCell to the top left hand corner of the Maze. */
        current = cells[0][0];
        current.setVisited(true);

        /* Checks for neighbours and remove walls until all cells have been traversed. */
        do {
            next = getNeighbour(current, cells);
            if (next != null) {
                removeWall(current, next);
                stack.push(current);
                current = next;
                current.setVisited(true);
            } else {
                current = stack.pop();
            }
        } while (!stack.isEmpty());

        return cells;
    }

    /**
     * Returns random MazeCell neighbour that has not been visited and proceed to that cell.
     *
     * @param currCell the current MazeCell we're at, used to determine possible neighbors.
     * @param cells the array of MazeCells in this Maze.
     * @return a MazeCell that has not been visited, and is beside the current MazeCell.
     */
    private MazeCell getNeighbour(MazeCell currCell, MazeCell[][] cells) {
        ArrayList<MazeCell> neighbours = new ArrayList<>();
        int cellX = currCell.getX();
        int cellY = currCell.getY();
        int mazeCols = getNumMazeCols();
        int mazeRows = getNumMazeRows();

        if (cellX - 1 >= 0 && !cells[cellX - 1][cellY].isVisited()) { // left
            neighbours.add(cells[cellX - 1][cellY]);
        }

        if (cellX + 1 < mazeCols && !cells[cellX + 1][cellY].isVisited()) { // right
            neighbours.add(cells[cellX + 1][cellY]);
        }

        if (cellY + 1 < mazeRows && !cells[cellX][cellY + 1].isVisited()) { // bottom
            neighbours.add(cells[cellX][cellY + 1]);
        }

        if (cellY - 1 >= 0 && !cells[cellX][cellY - 1].isVisited()) { // top
            neighbours.add(cells[cellX][cellY - 1]);
        }

        if (neighbours.isEmpty()) {
            return null;
        } else {
            return neighbours.get(rand.nextInt(neighbours.size()));
        }
    }

    /** Removes walls between this MazeCell and the next MazeCell, as long as they're not borders.
     *
     * @param current the current MazeCell.
     * @param next the next MazeCell.
     */
    private void removeWall(MazeCell current, MazeCell next){
        /* (x, y) coordinates for the current and next MazeCell. */
        int currX = current.getX();
        int currY = current.getY();
        int nextX = next.getX();
        int nextY = next.getY();

        if (currX == nextX - 1) {
            current.setRightWall(false);
            next.setLeftWall(false);
        } else if (currX == nextX + 1) {
            current.setLeftWall(false);
            next.setRightWall(false);
        } else if (currY == nextY - 1) {
            current.setBottomWall(false);
            next.setTopWall(false);
        } else {
            current.setTopWall(false);
            next.setBottomWall(false);
        }
    }

    /**
     * Creates 5 coins at random locations in the maze.
     *
     * @return the list of Coins created.
     */
    ArrayList<Coin> createCoins() {
        ArrayList<Coin> coins = new ArrayList<>();
        SparseIntArray coinCoordinates = new SparseIntArray();
        int mazeCols = getNumMazeCols();
        int mazeRows = getNumMazeRows();

        /* Appends Player start and Maze exit coordinates to the coinCoordinates array. */
        coinCoordinates.append(0,0);
        coinCoordinates.append(mazeCols - 1, mazeRows - 1);

        /* 7 is the total number of coordinates, including Player and exit coordinates */
        while (coinCoordinates.size() < 7) {
            int x = rand.nextInt(mazeCols);
            if (coinCoordinates.get(x, -1) == -1) {
                coinCoordinates.append(x, rand.nextInt(mazeRows));
            } else {
                int y = rand.nextInt(mazeRows);
                while (coinCoordinates.get(x) == y) {
                    y = rand.nextInt(mazeRows);
                }
                coinCoordinates.append(x,y);
            }
        }

        /* Deletes the Player start and Maze exit coordinates. */
        coinCoordinates.delete(0);
        coinCoordinates.delete(mazeCols);

        /* Creates Coins at the coordinates from coinCoordinates. */
        for (int i = 0; i < 5; i++) {
            int x = coinCoordinates.keyAt(i);
            int y = coinCoordinates.get(x);
            Coin coin = new Coin(x, y);
            coins.add(coin);
        }

        return coins;
    }

    /**
     * Calculates the cellSize based on the screen's dimensions.
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

    /**
     * Returns the Maze's cellSize.
     *
     * @return float value for cellSize.
     */
    float getCellSize() {
        return cellSize;
    }

    /**
     * Sets the cellSize in pixel for all MazeCells.
     *
     * @param cellSize the new cellSize.
     */
    private void setCellSize(float cellSize) {
        this.cellSize = cellSize;
    }

    /**
     * Returns the Maze's horizontalPadding.
     *
     * @return float value for horizontalPadding.
     */
    float getHorizontalPadding() {
        return horizontalPadding;
    }

    /**
     * Sets the horizontalPadding for this Maze.
     *
     * @param horizontalPadding the new horizontalPadding of the Maze.
     */
    private void setHorizontalPadding(float horizontalPadding) {
        this.horizontalPadding = horizontalPadding;
    }

    /**
     * Returns the Maze's verticalPadding.
     *
     * @return float value for verticalPadding.
     */
    float getVerticalPadding() {
        return verticalPadding;
    }

    /**
     * Sets the verticalPadding for this Maze.
     *
     * @param verticalPadding the new verticalPadding of the Maze.
     */
    private void setVerticalPadding(float verticalPadding) {
        this.verticalPadding = verticalPadding;
    }

    /**
     * Returns the Maze's wall Paint.
     *
     * @return Paint object for walls.
     */
    Paint getWallPaint() {
        return wallPaint;
    }

    /**
     * Returns the Maze's Player Paint.
     *
     * @return Paint object for Player.
     */
    Paint getPlayerPaint() {
        return playerPaint;
    }

    void setPlayerPaint(int newColour) {
        playerPaint.setColor(newColour);
    }

    /**
     * Returns the Maze's exit square Paint.
     *
     * @return Paint object for exit square.
     */
    Paint getExitPaint() {
        return exitPaint;
    }

    /**
     * Returns the Maze's coin Paint.
     *
     * @return Paint object for coins.
     */
    Paint getCoinPaint() {
        return coinPaint;
    }

    private int getNumMazeCols() {
        return numMazeCols;
    }

    private int getNumMazeRows() {
        return numMazeRows;
    }
}
