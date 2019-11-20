package com.example.dungeonescape.maze;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.SparseIntArray;

import com.example.dungeonescape.platformer.Coin;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Managers all (visible) Maze GameObjects and calculates sizes for various objects. Mazes and coins
 * are created in this class and then passed to MazeView to be drawn out on screen.
 */
class MazeManager {
//  size of each cell
    private float cellSize;
//  number of columns and rows of the maze, passed in from MazeView.
    private int numMazeCols;
    private int numMazeRows;
//  paint of all the objects on the screen.
    private Paint wallPaint;
    private Paint playerPaint;
    private Paint exitPaint;
    private Paint coinPaint;
//  a Random instance used to randomize coin locations.
    private Random rand = new Random();

    /** The horizontal and vertical margin from the edge of the screen to the walls of the maze */
    private float horizontalPadding;
    private float verticalPadding;

    MazeManager(int cols, int rows) {
        numMazeCols = cols;
        numMazeRows = rows;
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

    /*
     * Create a maze using a specific algorithm:
     * 1. Create a maze with cols X rows of grids, every cell is closed off.
     * 2. Start at the top left hand corner as the "current" cell, add this cell to a stack,
     * traverse to a random neighbor cell that has not been visited before,
     * and knock out the wall in between the two cells.
     * 3. If all neighbors have been visited, then we traverse back to previous cell and pop a
     * cell out of the stack, repeat until we arrive at a cell with unvisited neighbor or until
     * the stack is empty.
     * 4. Mark the new cell as the "current cell" and repeat until the stack is empty, which
     * guarantees all cells are visited so all cells have a path through which we can access.
     *  @return a new maze with a path already made.
     */
    MazeCell[][] createMaze(){
        Stack<MazeCell> stack = new Stack<>();
        MazeCell current, next;
        int mazeCols = getNumMazeCols();
        int mazeRows = getNumMazeRows();
        MazeCell[][] cells = new MazeCell[mazeCols][mazeRows];
        //Creating a maze with cols X rows cells.
        for (int x = 0; x < mazeCols; x++) {
            for (int y = 0; y < mazeRows; y++) {
                cells[x][y] = new MazeCell(x, y);
            }
        }

        current = cells[0][0];
        current.setVisited(true);

        //check for neighbors and remove walls until all cells are traversed.
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
     * get a random neighbor that has not been visited and proceed to that cell.
     * @param cell the current cell we're at, used to determine possible neighbors.
     * @param cells all the possible cells in the maze.
     * @return an unvisited cell which is a neighbor of the passed in cell.
     */
    private MazeCell getNeighbour(MazeCell cell, MazeCell[][] cells) {
        ArrayList<MazeCell> neighbours = new ArrayList<>();
        int cellX = cell.getX();
        int cellY = cell.getY();
        int mazeCols = getNumMazeCols();
        int mazeRows = getNumMazeRows();
        // left
        if (cellX - 1 >= 0 && !cells[cellX - 1][cellY].isVisited()) {
            neighbours.add(cells[cellX - 1][cellY]);
        }
        // right
        if (cellX + 1 < mazeCols && !cells[cellX + 1][cellY].isVisited()) {
            neighbours.add(cells[cellX + 1][cellY]);
        }
        // bottom
        if (cellY + 1 < mazeRows && !cells[cellX][cellY + 1].isVisited()) {
            neighbours.add(cells[cellX][cellY + 1]);
        }
        // top
        if (cellY - 1 >= 0 && !cells[cellX][cellY - 1].isVisited()) {
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
     * Create 2 coins at random locations in the maze and return this list.
     * @return The list of coins we just created.
     */
    ArrayList<Coin> createCoins() {
        ArrayList<Coin> coins = new ArrayList<>();
        SparseIntArray coordinates = new SparseIntArray();
        coordinates.append(0,0);
        coordinates.append(numMazeCols, numMazeRows);
        while(coordinates.size()<4){
            int x = rand.nextInt(numMazeCols);
            if(coordinates.get(x, -1) == -1) {
                coordinates.append(x, rand.nextInt(numMazeRows));
            } else
                {
                int y = rand.nextInt(numMazeRows);
                while(coordinates.get(x)==y){
                    y = rand.nextInt(numMazeRows);
                }
                coordinates.append(x,y);
            }
        }
        coordinates.delete(0);
        coordinates.delete(numMazeCols);
        for (int i = 0; i < 2; i++) {
            int x = coordinates.keyAt(i);
            int y = coordinates.get(x);
            Coin coin = new Coin(x, y);
            coins.add(coin);
        }
        return coins;
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

    /** Returns the Maze's cellSize.
     *
     * @return float value for cellSize.
     */
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

    /** Returns the Maze's horizontalPadding.
     *
     * @return float value for horizontalPadding.
     */
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

    /** Returns the Maze's verticalPadding.
     *
     * @return float value for verticalPadding.
     */
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

    /** Returns the Maze's wall Paint.
     *
     * @return Paint object for walls.
     */
    Paint getWallPaint() {
        return wallPaint;
    }

    /** Returns the Maze's Player Paint.
     *
     * @return Paint object for Player.
     */
    Paint getPlayerPaint() {
        return playerPaint;
    }

    void setPlayerPaint(int newColour) {
        playerPaint.setColor(newColour);
    }

    /** Returns the Maze's exit square Paint.
     *
     * @return Paint object for exit square.
     */
    Paint getExitPaint() {
        return exitPaint;
    }

    /** Returns the Maze's coin Paint.
     *
     * @return Paint object for coins.
     */
    Paint getCoinPaint() {
        return coinPaint;
    }

    public int getNumMazeCols() {
        return numMazeCols;
    }

    public int getNumMazeRows() {
        return numMazeRows;
    }
}
