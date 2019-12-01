package com.example.dungeonescape.maze;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

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

    /** Player and exit objects, and their positions. */
    private Sprite playerSprite = new Sprite();
    private Sprite exitSprite = new Sprite();

    private MazeData mazeData;

    public MazeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    void setMazeData(MazeData mazeData) {
        this.mazeData = mazeData;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        updateMazeObjectsData();
        calculateDimensions();

        // translate the canvas by our padding values so the maze is always centered on our screen.
        canvas.translate(mazeData.getHorizontalPadding(), mazeData.getVerticalPadding());

        // draws walls, Coins, the Player and the exit square on the screen
        paintWalls(canvas);
        paintCoins(canvas);
        playerSprite.draw(canvas);
        exitSprite.draw(canvas);
    }

    /** Performs dimensions calculations including cellSize and padding values. */
    private void calculateDimensions() {
        mazeData.calculateCellSize(getWidth(), getHeight());
        mazeData.calculateCellHorizontalPadding(getWidth());
        mazeData.calculateCellVerticalPadding(getHeight());
    }

    /** Draws walls (borders) for each mazeCell.
     *
     * @param canvas the Canvas to draw the walls on.
     */
    private void paintWalls(Canvas canvas) {
        for(int x = 0; x < mazeData.getNumMazeCols(); x++) {
            for(int y = 0; y < mazeData.getNumMazeRows(); y++) {
                mazeData.getCells()[x][y].draw(canvas);
            }
        }
    }

    /** Draws the Coin circles on the screen.
     *
     * @param canvas the Canvas to draw the Coins on.
     */
    private void paintCoins(Canvas canvas) {
        for (MazeCoin coin : mazeData.getCoins()) {
            coin.draw(canvas);
        }
    }

    /** Runs method setMazeData on all GameObjects that implement RetrieveData. */
    void updateMazeObjectsData() {
        playerSprite.setGameData(this.mazeData);
        exitSprite.setGameData(this.mazeData);
        updateMazeCellData();
        updateCoinData();
    }

    /** Runs method setMazeData on all MazeCells. */
    void updateMazeCellData() {
        for (int x = 0; x < mazeData.getNumMazeRows(); x++) {
            for (int y = 0; y < mazeData.getNumMazeRows(); y++) {
                mazeData.getCells()[x][y].setGameData(mazeData);
            }
        }
    }

    /** Runs method setMazeData on all MazeCoins. */
    void updateCoinData() {
        for (MazeCoin coin: mazeData.getCoins()) {
            coin.setGameData(mazeData);
        }
    }

    Sprite getExitSprite() {
        return this.exitSprite;
    }

    void setExitSprite() {
        this.exitSprite.setX(mazeData.getNumMazeCols() - 1);
        this.exitSprite.setY(mazeData.getNumMazeRows() - 1);
    }

    Sprite getPlayerSprite() {
        return this.playerSprite;
    }

    void setPlayerSprite(Sprite playerSprite) {
        this.playerSprite = playerSprite;
    }
}
