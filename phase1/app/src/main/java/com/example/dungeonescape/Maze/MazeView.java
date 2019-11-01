package com.example.dungeonescape.Maze;

import java.util.Iterator;
import java.util.Random;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.dungeonescape.GameObject;
import com.example.dungeonescape.Player;
import com.example.dungeonescape.platformer.Coin;

import java.util.ArrayList;
import java.util.Stack;

/**
 * This class is responsible for drawing out the game objects and walls of the maze, as well as
 * executing the movements of the player in the maze on the touch screen.
 *
 * The original code from MazeView was from the following videos:
 * https://www.youtube.com/watch?v=I9lTBTAk5MU
 * https://www.youtube.com/watch?v=iri0wZ3NvdQ
 *
 * It has been edited and adjusted to fit our own objectives and visions of the game.
 * TODO: Edit this javadoc as we change the code below.
 */

public class MazeView extends View {
    /** A 2D Array of MazeCell cells. */
    private MazeCell[][] cells;

    /** A list of coins that can be collected for score. */
    private ArrayList<Coin> coins;

    /** Player and exit objects, and their positions. */

    private Player player;
    private GameObject exit;
    private MazeCell playerLoc;

    /** The number of columns and rows in this maze. */
    private int numMazeCols = 5;
    private int numMazeRows = 5;

    /** The MazeCell size in pixels. */
    float cellSize;

    /** The horizontal and vertical margin from the edge of the screen to the walls of the maze */
    float horizontalPadding;
    float verticalPadding;

    /** A randomizer. */
    private Random rand = new Random();

    /** Instantiates the MazeManager class for this Maze. */
    private MazeManager mazeManager = new MazeManager();

    /** Number of times the Player has gone through the maze. */
    public int mazeIterations = 0;

    public MazeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createMaze();
        createCoins();
    }

    /** Adds 5 Coins to the coins ArrayList, in random locations. */
    private void createCoins() {
        coins = new ArrayList<>();
        int mazeCols = getNumMazeCols();
        int mazeRows = getNumMazeRows();

        for (int i = 0; i < 5; i++) {
            Coin coin = new Coin(rand.nextInt(mazeCols), rand.nextInt(mazeRows));
            coins.add(coin);
        }
    }

    public boolean doneLevel() {
        return mazeIterations >= 3;
    }

    public void setPlayer(Player player){
        this.player = player;
        relocatePlayer();
    }
    public void relocatePlayer(){
        if(player != null) {
            player.setY(0);
            player.setX(0);
        }
    }

    private void createMaze(){
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
         */
        Stack<MazeCell> stack = new Stack<>();
        MazeCell current, next;
        int mazeCols = getNumMazeCols();
        int mazeRows = getNumMazeRows();

        cells = new MazeCell[mazeCols][mazeRows];
        /*
         * Creating a maze with cols X rows cells.
         */
        for (int x = 0; x < mazeCols; x++) {
            for (int y = 0; y < mazeRows; y++) {
                cells[x][y] = new MazeCell(x, y, 1);
            }
        }

        current = cells[0][0];
        current.setVisited(true);

        //check for neighbors and remove walls until all cells are traversed.
        do {
            next = getNeighbour(current);
            if (next != null) {
                removeWall(current, next);
                stack.push(current);
                current = next;
                current.setVisited(true);
            } else {
                current = stack.pop();
            }
        } while (!stack.isEmpty());
        playerLoc = cells[0][0];
        exit = new GameObject(numMazeCols-1, numMazeRows-1, 1);
        relocatePlayer();
    }

    private MazeCell getNeighbour(MazeCell cell) {
        //get a random neighbor
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

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        /* Represents the width and height of the available screen in pixels. */
        int screenWidth = getWidth();
        int screenHeight = getHeight();
        int mazeCols = getNumMazeCols();
        int mazeRows = getNumMazeRows();

        cellSize = mazeManager.calculateCellSize(screenWidth, screenHeight,
                mazeCols, mazeRows);
        horizontalPadding = mazeManager.calculateCellHorizontalPadding(screenWidth,
                mazeCols, cellSize);
        verticalPadding = mazeManager.calculateCellVerticalPadding(screenHeight,
                mazeRows, cellSize);
        //translate the canvas by our padding values so the maze is always centered on our screen.
        canvas.translate(horizontalPadding, verticalPadding);

        //adding a padding so the player cell and the exit cells don't touch the walls.
        float margin = cellSize / 10;

        // draws walls, Coins, the Player and the exit square on the screen
        paintWalls(canvas, mazeCols, mazeRows);
        paintCoins(canvas, margin);
        paintPlayer(canvas, margin);
        paintExit(canvas, margin);
    }

    /** Draws walls (borders) for each mazeCell.
     *
     * @param canvas the Canvas to draw the walls on.
     * @param mazeCols the number of columns in this Maze.
     * @param mazeRows the number of rows in this Maze.
     */
    private void paintWalls(Canvas canvas, int mazeCols, int mazeRows) {
        Paint mazeWallPaint = mazeManager.getWallPaint();

        for(int x = 0; x < mazeCols; x++) {
            for(int y = 0; y < mazeRows; y++) {
                if (cells[x][y].isTopWall()) {
                    canvas.drawLine(
                            x * cellSize,
                            y * cellSize,
                            (x + 1) * cellSize,
                            y * cellSize,
                            mazeWallPaint);
                }
                if (cells[x][y].isLeftWall()) {
                    canvas.drawLine(
                            x * cellSize,
                            y * cellSize,
                            x * cellSize,
                            (y + 1) * cellSize,
                            mazeWallPaint);
                }
                if (cells[x][y].isBottomWall()) {
                    canvas.drawLine(
                            x * cellSize,
                            (y + 1) * cellSize,
                            (x + 1) * cellSize,
                            (y + 1) * cellSize,
                            mazeWallPaint);
                }
                if (cells[x][y].isRightWall()) {
                    canvas.drawLine(
                            (x + 1) * cellSize,
                            y * cellSize,
                            (x + 1) * cellSize,
                            (y + 1) * cellSize,
                            mazeWallPaint);
                }
            }
        }
    }

    /** Draws the Coin circles on the screen.
     *
     * @param canvas the Canvas to draw the Coins on.
     * @param margin the space around the Coin circles.
     */
    private void paintCoins(Canvas canvas, float margin) {
        Paint mazeCoinPaint = mazeManager.getCoinPaint();

        for (Coin coin : coins) {
            canvas.drawOval(
                    coin.getX() * cellSize + margin,
                    coin.getY() * cellSize + margin,
                    (coin.getX() + 1) * cellSize - margin,
                    (coin.getY() + 1) * cellSize - margin,
                    mazeCoinPaint);
        }
    }

    /** Draws the Player square on the screen.
     *
     * @param canvas the Canvas to draw the Player on.
     * @param margin the space around the Player square.
     */
    private void paintPlayer(Canvas canvas, float margin) {
        Paint mazePlayerPaint = mazeManager.getPlayerPaint();
        int playerX = player.getX();
        int playerY = player.getY();

        canvas.drawRect(
                playerX * cellSize + margin,
                playerY * cellSize + margin,
                (playerX + 1) * cellSize - margin,
                (playerY + 1) * cellSize - margin,
                mazePlayerPaint);
    }

    /** Draws the exit square on the screen.
     *
     * @param canvas the Canvas to draw the exit on.
     * @param margin the space around the square.
     */
    private void paintExit(Canvas canvas, float margin) {
        Paint mazeExitPaint = mazeManager.getExitPaint();
        int exitX = exit.getX();
        int exitY = exit.getY();

        canvas.drawRect(
                exitX * cellSize + margin,
                exitY * cellSize + margin,
                (exitX + 1) * cellSize - margin,
                (exitY + 1) * cellSize - margin,
                mazeExitPaint);
    }

    void movePlayer(String direction){
        //depending on the given direction, move the player to that cell if it's in the maze.
        switch (direction){
            case "UP":
                if(!playerLoc.isTopWall()) {
                    playerLoc = cells[player.getX()][player.getY() - 1];
                    player.setY(player.getY() - 1);
                }
                break;
            case "DOWN":
                if(!playerLoc.isBottomWall()) {
                    playerLoc = cells[player.getX()][player.getY() + 1];
                    player.setY(player.getY() + 1);
                }
                break;
            case "LEFT":
                if(!playerLoc.isLeftWall()) {
                    playerLoc = cells[playerLoc.getX() - 1][playerLoc.getY()];
                    player.setX(player.getX() - 1);
                }
                break;
            case "RIGHT":
                if(!playerLoc.isRightWall()) {
                    playerLoc = cells[playerLoc.getX() + 1][playerLoc.getY()];
                    player.setX(player.getX() + 1);
                }
                break;
        }
        playerAtExit();
        playerOnCoin();
        invalidate();
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

    /** Checks if this Player has arrived at the exit, create a new Maze if true. */
    private void playerAtExit() {
        if (player.getX() == exit.getX() && player.getY() == exit.getY()) {
            mazeIterations++;
            createMaze();
        }
    }

    /** Checks if this Player has the same coordinates as a Coin.
     * Removes Coin from game & adds it to Player if true.
     */
    private void playerOnCoin() {
        Iterator<Coin> coinIterator = coins.iterator();
        while (coinIterator.hasNext()) {
            Coin coin = coinIterator.next();
            if (player.getX() == coin.getX() && player.getY() == coin.getY()) {
                coinIterator.remove();
                player.addCoin();
            }
        }
    }

    public int getNumMazeCols() {
        return numMazeCols;
    }

    public int getNumMazeRows() {
        return numMazeRows;
    }
}
