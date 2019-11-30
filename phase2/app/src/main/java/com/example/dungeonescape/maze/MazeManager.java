package com.example.dungeonescape.maze;

import android.graphics.Color;
import android.util.SparseIntArray;

import com.example.dungeonescape.game.GameObject;
import com.example.dungeonescape.game.collectable.Coin;
import com.example.dungeonescape.player.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

/**
 * Managers all (visible) Maze GameObjects and calculates sizes for various objects. Mazes and coins
 * are created in this class and then passed to MazeView to be drawn out on screen.
 */
class MazeManager {

    /** A 2D Array of MazeCell cells. */
    private MazeCell[][] cells;

    /** The size of each MazeCell */
    private float cellSize;

    /** The number of columns and rows in the Maze, passed in from MazeView. */
    private int numMazeCols;
    private int numMazeRows;

    /** a Random instance used to randomize Coin locations. */
    private Random rand = new Random();

    /** The horizontal and vertical margin from the edge of the screen to the walls of the maze */
    private float horizontalPadding;
    private float verticalPadding;

    private Player player;

    private MazeView mazeView;
    private PlayerSprite playerSprite;
    private MazeCell playerLoc;
    private GameObject exit;

    /** Number of times the Player has gone through the maze. */
    private int mazeIterations = 0;

    MazeManager(MazeView mazeView, Player player) {
        this.mazeView = mazeView;
        this.player = player;
        setNumMazeRows(5 * player.getGameDifficulty());
        setNumMazeCols(5 * player.getGameDifficulty());

        mazeView.setNumMazeCols(getNumMazeCols());
        mazeView.setNumMazeRows(getNumMazeRows());

        cells = createMaze();
        mazeView.setCells(this.cells);
        mazeView.setCoins(createCoins());
        playerSprite = mazeView.playerSprite;
        playerLoc = mazeView.playerLoc;
        mazeView.exit = new GameObject(numMazeCols - 1, numMazeRows - 1);
        mazeView.exit.setPaintColour(Color.BLUE);
        exit = mazeView.exit;
    }

    /** Populates a 2D Array with MazeCell GameObjects to create the Maze. */
    private MazeCell[][] createMaze(){
        Stack<MazeCell> stack = new Stack<>();
        MazeCell current, next;
        int mazeCols = getNumMazeCols();
        int mazeRows = getNumMazeRows();
        MazeCell[][] cells = new MazeCell[mazeCols][mazeRows];
        //Creating a maze with cols X rows cells.
        for (int x = 0; x < mazeCols; x++) {
            for (int y = 0; y < mazeRows; y++) {
                // cells[x][y] = new MazeCell(x, y);
                MazeCell newMazeCell = new MazeCell(x, y);
                cells[x][y] = newMazeCell;
                newMazeCell.setPaintColour(Color.WHITE);
                newMazeCell.setPaintStrokeWidth(4);
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
    private ArrayList<Coin> createCoins() {
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
            Coin coin = new Coin(x, y, (int) cellSize);
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

    boolean doneLevel() {
        return mazeIterations >= 3;
    }

    void movePlayer(String direction){
        // depending on the given direction, move the player to that cell if it's in the maze.
        switch (direction){
            case "UP":
                if(!playerLoc.isTopWall()) {
                    playerLoc = this.cells[playerSprite.getX()][playerSprite.getY() - 1];
                    playerSprite.setY(playerSprite.getY() - 1);
                }
                break;
            case "DOWN":
                if(!playerLoc.isBottomWall()) {
                    playerLoc = this.cells[playerSprite.getX()][playerSprite.getY() + 1];
                    playerSprite.setY(playerSprite.getY() + 1);
                }
                break;
            case "LEFT":
                if(!playerLoc.isLeftWall()) {
                    playerLoc = this.cells[playerLoc.getX() - 1][playerLoc.getY()];
                    playerSprite.setX(playerSprite.getX() - 1);
                }
                break;
            case "RIGHT":
                if(!playerLoc.isRightWall()) {
                    playerLoc = this.cells[playerLoc.getX() + 1][playerLoc.getY()];
                    playerSprite.setX(playerSprite.getX() + 1);
                }
                break;
        }
        playerAtExit();
        playerOnCoin();
        mazeView.invalidate();
    }

    /** Checks if this Player has arrived at the exit, create a new Maze if true. */
    private void playerAtExit() {
        if (playerSprite.getX() == exit.getX() && playerSprite.getY() == exit.getY()) {
            mazeIterations++;
            this.cells = createMaze();
            mazeView.setCells(this.cells);
            mazeView.setCoins(createCoins());
            relocatePlayerSprite();
        }
    }

    /** Checks if this Player has the same coordinates as a Coin.
     * Removes Coin from game & adds it to Player if true.
     */
    private void playerOnCoin() {
        Iterator<Coin> coinIterator = mazeView.getCoins().iterator();
        while (coinIterator.hasNext()) {
            Coin coin = coinIterator.next();
            if (playerSprite.getX() == coin.getX() && playerSprite.getY() == coin.getY()) {
                coinIterator.remove();
                player.addCoin();
            }
        }
    }

    void relocatePlayerSprite() {
        playerSprite.setPaintColour(player.getColour());
        playerLoc = this.cells[0][0];
        exit = new GameObject(numMazeCols - 1, numMazeRows - 1);
        exit.setPaintColour(Color.BLUE);
        if (playerSprite != null) {
            playerSprite.setX(0);
            playerSprite.setY(0);
        }
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

    private int getNumMazeCols() {
        return numMazeCols;
    }

    private void setNumMazeCols(int cols){this.numMazeCols = cols;}

    private int getNumMazeRows() {
        return numMazeRows;
    }

    private void setNumMazeRows(int rows){this.numMazeRows = rows;}

    MazeView getMazeView() {
        return this.mazeView;
    }

    void setMazeView(MazeView mazeView) {
        this.mazeView = mazeView;
    }
}
