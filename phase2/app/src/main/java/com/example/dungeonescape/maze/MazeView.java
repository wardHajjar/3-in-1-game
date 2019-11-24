package com.example.dungeonescape.maze;

import java.util.Iterator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.dungeonescape.game.GameObject;
import com.example.dungeonescape.player.Player;
import com.example.dungeonescape.game.collectable.Coin;

import java.util.ArrayList;

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
    private PlayerSprite playerSprite;
    private GameObject exit;
    private MazeCell playerLoc;

    /** The number of columns and rows in this maze. */
    private int numMazeCols;
    private int numMazeRows;

    /** Instantiates the MazeManager class for this Maze. */
    private MazeManager mazeManager;

    /** Number of times the Player has gone through the maze. */
    public int mazeIterations = 0;

    public MazeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        coins = new ArrayList<>();
        numMazeCols = 5;
        numMazeRows = 5;
        mazeManager = new MazeManager(numMazeCols, numMazeRows);
        cells = mazeManager.createMaze();
        relocatePlayerSprite();
        coins = mazeManager.createCoins();
    }

    public boolean doneLevel() {
        return mazeIterations >= 3;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public void setPlayerSprite(PlayerSprite playerSprite) {
        this.playerSprite = playerSprite;
        relocatePlayerSprite();
    }

    public void relocatePlayerSprite() {
        playerLoc = cells[0][0];
        exit = new GameObject(numMazeCols - 1, numMazeRows - 1);
        exit.setPaintColour(Color.BLUE);
        if (playerSprite != null) {
            playerSprite.setX(0);
            playerSprite.setY(0);
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

        // calculates MazeCell cellSize in pixels
        mazeManager.calculateCellSize(screenWidth, screenHeight, mazeCols, mazeRows);
        float cellSize = mazeManager.getCellSize();

        // calculates MazeCell horizontalPadding and verticalPadding
        mazeManager.calculateCellHorizontalPadding(screenWidth, mazeCols, cellSize);
        mazeManager.calculateCellVerticalPadding(screenHeight, mazeRows, cellSize);

        float horizontalPadding = mazeManager.getHorizontalPadding();
        float verticalPadding = mazeManager.getVerticalPadding();

        // translate the canvas by our padding values so the maze is always centered on our screen.
        canvas.translate(horizontalPadding, verticalPadding);

        // adding a padding so the player cell and the exit cells don't touch the walls.
        float margin = cellSize / 10;

        // draws walls, Coins, the Player and the exit square on the screen
        paintWalls(canvas, mazeCols, mazeRows, cellSize);
        paintCoins(canvas, cellSize, margin);
        paintPlayerSprite(canvas, cellSize, margin);
        paintExit(canvas, cellSize, margin);
    }

    /** Draws walls (borders) for each mazeCell.
     *
     * @param canvas the Canvas to draw the walls on.
     * @param mazeCols the number of columns in this Maze.
     * @param mazeRows the number of rows in this Maze.
     */
    private void paintWalls(Canvas canvas, int mazeCols, int mazeRows, float cellSize) {
        for(int x = 0; x < mazeCols; x++) {
            for(int y = 0; y < mazeRows; y++) {
                MazeCell currentCell = cells[x][y];
                if (cells[x][y].isTopWall()) {
                    canvas.drawLine(
                            x * cellSize,
                            y * cellSize,
                            (x + 1) * cellSize,
                            y * cellSize,
                            currentCell.getPaint());
                }
                if (cells[x][y].isLeftWall()) {
                    canvas.drawLine(
                            x * cellSize,
                            y * cellSize,
                            x * cellSize,
                            (y + 1) * cellSize,
                            currentCell.getPaint());
                }
                if (cells[x][y].isBottomWall()) {
                    canvas.drawLine(
                            x * cellSize,
                            (y + 1) * cellSize,
                            (x + 1) * cellSize,
                            (y + 1) * cellSize,
                            currentCell.getPaint());
                }
                if (cells[x][y].isRightWall()) {
                    canvas.drawLine(
                            (x + 1) * cellSize,
                            y * cellSize,
                            (x + 1) * cellSize,
                            (y + 1) * cellSize,
                            currentCell.getPaint());
                }
            }
        }
    }

    /** Draws the Coin circles on the screen.
     *
     * @param canvas the Canvas to draw the Coins on.
     * @param margin the space around the Coin circles.
     */
    private void paintCoins(Canvas canvas, float cellSize, float margin) {
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

    /** Draws the PlayerSprite square on the screen.
     *
     * @param canvas the Canvas to draw the PlayerSprite on.
     * @param margin the space around the PlayerSprite square.
     */
    private void paintPlayerSprite(Canvas canvas, float cellSize, float margin) {
        //Paint mazePlayerPaint = mazeManager.getPlayerSpritePaint();
        int playerX = playerSprite.getX();
        int playerY = playerSprite.getY();

        Paint mazePlayerPaint = playerSprite.getPaint();

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
    private void paintExit(Canvas canvas, float cellSize, float margin) {
        int exitX = exit.getX();
        int exitY = exit.getY();

        Paint mazeExitPaint = exit.getPaint();

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
                    playerLoc = cells[playerSprite.getX()][playerSprite.getY() - 1];
                    playerSprite.setY(playerSprite.getY() - 1);
                }
                break;
            case "DOWN":
                if(!playerLoc.isBottomWall()) {
                    playerLoc = cells[playerSprite.getX()][playerSprite.getY() + 1];
                    playerSprite.setY(playerSprite.getY() + 1);
                }
                break;
            case "LEFT":
                if(!playerLoc.isLeftWall()) {
                    playerLoc = cells[playerLoc.getX() - 1][playerLoc.getY()];
                    playerSprite.setX(playerSprite.getX() - 1);
                }
                break;
            case "RIGHT":
                if(!playerLoc.isRightWall()) {
                    playerLoc = cells[playerLoc.getX() + 1][playerLoc.getY()];
                    playerSprite.setX(playerSprite.getX() + 1);
                }
                break;
        }
        playerAtExit();
        playerOnCoin();
        invalidate();
    }

    /** Checks if this Player has arrived at the exit, create a new Maze if true. */
    private void playerAtExit() {
        if (playerSprite.getX() == exit.getX() && playerSprite.getY() == exit.getY()) {
            mazeIterations++;
            cells = mazeManager.createMaze();
            coins = mazeManager.createCoins();
            relocatePlayerSprite();
        }
    }

    /** Checks if this Player has the same coordinates as a Coin.
     * Removes Coin from game & adds it to Player if true.
     */
    private void playerOnCoin() {
        Iterator<Coin> coinIterator = coins.iterator();
        while (coinIterator.hasNext()) {
            Coin coin = coinIterator.next();
            if (playerSprite.getX() == coin.getX() && playerSprite.getY() == coin.getY()) {
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

    public MazeManager getMazeManager() {
        return mazeManager;
    }
}
