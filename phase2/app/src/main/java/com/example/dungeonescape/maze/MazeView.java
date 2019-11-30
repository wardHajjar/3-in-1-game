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
    private PlayerSprite playerSprite = new PlayerSprite();
    private MazeCell exitCell;

    /** The number of columns and rows in this maze. */
    private int numMazeCols;
    private int numMazeRows;

    /** The size of each MazeCell */
    private float cellSize;

    /** The horizontal and vertical margin from the edge of the screen to the walls of the maze */
    private float horizontalPadding;
    private float verticalPadding;

    public MazeView(Context context, AttributeSet attrs) {
        super(context, attrs);
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
        horizontalPadding = (screenWidth - (numMazeCols * cellSize)) / 2;
    }

    /**
     * Calculates the cell's vertical padding based on the screen's height and calculated cell size.
     *
     * @param screenHeight the height of the phone screen in pixels.
     * @param numMazeRows the number of rows in this Maze.
     * @param cellSize the calculated size of the MazeCell.
     */
    void calculateCellVerticalPadding(int screenHeight, int numMazeRows, float cellSize) {
        verticalPadding = (screenHeight - (numMazeRows * cellSize)) / 2;
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
        calculateCellSize(screenWidth, screenHeight, mazeCols, mazeRows);

        // calculates MazeCell horizontalPadding and verticalPadding
        calculateCellHorizontalPadding(screenWidth, mazeCols, cellSize);
        calculateCellVerticalPadding(screenHeight, mazeRows, cellSize);

        // translate the canvas by our padding values so the maze is always centered on our screen.
        canvas.translate(horizontalPadding, verticalPadding);

        // adding a padding so the player cell and the exit cells don't touch the walls.
        float margin = cellSize / 10;

        // draws walls, Coins, the Player and the exit square on the screen
        paintWalls(canvas, mazeCols, mazeRows, cellSize);
        paintCoins(canvas, cellSize, margin);
        paintPlayerSprite(canvas, cellSize, margin);
        paintExit(exitCell, canvas, cellSize, margin);
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
        for (Coin coin : coins) {
            canvas.drawOval(
                    coin.getX() * cellSize + margin,
                    coin.getY() * cellSize + margin,
                    (coin.getX() + 1) * cellSize - margin,
                    (coin.getY() + 1) * cellSize - margin,
                    coin.getPaint());
        }
    }

    /** Draws the PlayerSprite square on the screen.
     *
     * @param canvas the Canvas to draw the PlayerSprite on.
     * @param margin the space around the PlayerSprite square.
     */
    private void paintPlayerSprite(Canvas canvas, float cellSize, float margin) {
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
    private void paintExit(MazeCell exit, Canvas canvas, float cellSize, float margin) {
        Paint mazeExitPaint = exit.getPaint();
        int exitX = exit.getX();
        int exitY = exit.getY();

        canvas.drawRect(
                exitX * cellSize + margin,
                exitY * cellSize + margin,
                (exitX + 1) * cellSize - margin,
                (exitY + 1) * cellSize - margin,
                mazeExitPaint);
    }

    void setNumMazeCols(int cols) {
        this.numMazeCols = cols;
    }

    void setNumMazeRows(int rows) {
        this.numMazeRows = rows;
    }

    public int getNumMazeCols() {
        return numMazeCols;
    }

    public int getNumMazeRows() {
        return numMazeRows;
    }

    MazeCell[][] getCells() {
        return this.cells;
    }

    void setCells(MazeCell[][] cells) {
        this.cells = cells;
    }

    ArrayList<Coin> getCoins() {
        return this.coins;
    }

    void setCoins(ArrayList<Coin> coins) {
        this.coins = coins;
    }

    void setCellSize(float cellSize) {
        this.cellSize = cellSize;
    }

    float getCellSize() {
        return this.cellSize;
    }

    MazeCell getExitCell() {
        return this.exitCell;
    }

    void setExitCell(MazeCell exitCell) {
        this.exitCell = exitCell;
    }

    PlayerSprite getPlayerSprite() {
        return this.playerSprite;
    }

    void setPlayerSprite(PlayerSprite playerSprite) {
        this.playerSprite = playerSprite;
    }
}
