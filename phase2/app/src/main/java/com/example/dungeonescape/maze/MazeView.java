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
import java.util.List;

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
    private Sprite playerSprite = new Sprite();
    private Sprite exitSprite = new Sprite();

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
     */
    void calculateCellSize(int screenWidth, int screenHeight) {
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
     * @param cellSize the calculated size of the MazeCell.
     */
    void calculateCellHorizontalPadding(int screenWidth, float cellSize) {
        horizontalPadding = (screenWidth - (numMazeCols * cellSize)) / 2;
    }

    /**
     * Calculates the cell's vertical padding based on the screen's height and calculated cell size.
     *
     * @param screenHeight the height of the phone screen in pixels.
     * @param cellSize the calculated size of the MazeCell.
     */
    void calculateCellVerticalPadding(int screenHeight, float cellSize) {
        verticalPadding = (screenHeight - (numMazeRows * cellSize)) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        calculateDimensions();

        // translate the canvas by our padding values so the maze is always centered on our screen.
        canvas.translate(horizontalPadding, verticalPadding);

        // adding a padding so the player cell and the exit cells don't touch the walls.
        float margin = cellSize / 10;

        // draws walls, Coins, the Player and the exit square on the screen
        paintWalls(canvas, cellSize);
        paintCoins(canvas, cellSize, margin);
        playerSprite.draw(canvas, cellSize, margin);
        exitSprite.draw(canvas, cellSize, margin);
    }

    /** Performs dimensions calculations including cellSize and padding values. */
    private void calculateDimensions() {
        calculateCellSize(getWidth(), getHeight());
        calculateCellHorizontalPadding(getWidth(), cellSize);
        calculateCellVerticalPadding(getHeight(), cellSize);
    }

    /** Draws walls (borders) for each mazeCell.
     *
     * @param canvas the Canvas to draw the walls on.
     */
    private void paintWalls(Canvas canvas, float cellSize) {
        for(int x = 0; x < numMazeCols; x++) {
            for(int y = 0; y < numMazeRows; y++) {
                cells[x][y].draw(canvas, cellSize);
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
            coin.draw(canvas, cellSize, margin);
        }
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

    Sprite getExitSprite() {
        return this.exitSprite;
    }

    void setExitSprite() {
        this.exitSprite.setX(this.numMazeCols-1);
        this.exitSprite.setY(this.numMazeRows-1);
    }

    Sprite getPlayerSprite() {
        return this.playerSprite;
    }

    void setPlayerSprite(Sprite playerSprite) {
        this.playerSprite = playerSprite;
    }
}
