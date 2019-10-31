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
    private int numMazeCols = 10;
    private int numMazeRows = 10;

    /** The line thickness of the walls. */
    private static final float wallThickness = 4;

    /** The horizontal and vertical margin from the edge of the screen to the walls of the maze */
    float cellSize;
    float horizontalPadding;
    float verticalPadding;

    /** Represents the colour of the objects to be printed. */
    private Paint wallPaint;
    private Paint playerPaint;
    private Paint exitPaint;
    private Paint coinPaint;
    private Random rand = new Random();

    /** Instantiates the MazeManager class for this Maze. */
    private MazeManager thisMaze = new MazeManager();

    public MazeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        wallPaint = new Paint();
        wallPaint.setColor(Color.WHITE);
        wallPaint.setStrokeWidth(wallThickness);
        playerPaint = new Paint();
        playerPaint.setColor(Color.RED);
        exitPaint = new Paint();
        exitPaint.setColor(Color.BLUE);
        coinPaint = new Paint();
        coinPaint.setColor(Color.YELLOW);
        player = new Player(0,0,1, "PLAYER");
        //        create 5 coins at random locations.
        coins = new ArrayList<>();
        for (int i = 0; i<5; i++) {
            Coin coin = new Coin(rand.nextInt(numMazeCols), rand.nextInt(numMazeRows));
            coins.add(coin);
        }
        createMaze();
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
        player.setX(0);
        player.setY(0);
        exit = new GameObject(numMazeCols-1, numMazeRows-1, 1);
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

    private void removeWall(MazeCell current, MazeCell next){
        /* remove the walls between current cell and next cell, only if they're not off the grids.
        (x, y) coordinates for the current and next MazeCell. */
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

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        /* Represents the width and height of the available screen in pixels. */
        int screenWidth = getWidth();
        int screenHeight = getHeight();
        int mazeCols = getNumMazeCols();
        int mazeRows = getNumMazeRows();

        cellSize = thisMaze.calculateCellSize(screenWidth, screenHeight,
                mazeCols, mazeRows);
        horizontalPadding = thisMaze.calculateCellHorizontalPadding(screenWidth,
                mazeCols, cellSize);
        verticalPadding = thisMaze.calculateCellVerticalPadding(screenHeight,
                mazeRows, cellSize);
        //translate the canvas by our padding values so the maze is always centered on our screen.
        canvas.translate(horizontalPadding, verticalPadding);

        for(int x = 0; x < numMazeCols; x++) {
            //draw out all the walls for every single cell, and remove them as we create the maze.
            for(int y = 0; y < numMazeRows; y++) {
                if (cells[x][y].isTopWall()) {
                    canvas.drawLine(
                            x * cellSize,
                            y * cellSize,
                            (x + 1) * cellSize,
                            y * cellSize,
                            wallPaint);
                }
                if (cells[x][y].isLeftWall()) {
                    canvas.drawLine(
                            x * cellSize,
                            y * cellSize,
                            x * cellSize,
                            (y + 1) * cellSize,
                            wallPaint);
                }
                if (cells[x][y].isBottomWall()) {
                    canvas.drawLine(
                            x * cellSize,
                            (y + 1) * cellSize,
                            (x + 1) * cellSize,
                            (y + 1) * cellSize,
                            wallPaint);
                }
                if (cells[x][y].isRightWall()) {
                    canvas.drawLine(
                            (x + 1) * cellSize,
                            y * cellSize,
                            (x + 1) * cellSize,
                            (y + 1) * cellSize,
                            wallPaint);
                }
            }
        }
        //adding a padding so the player cell and the exit cells don't touch the walls.
        float margin = cellSize/10;

        for(Coin coin:coins){
            canvas.drawOval(
                    coin.getX() * cellSize+margin,
                    coin.getY() * cellSize+margin,
                    (coin.getX() + 1) * cellSize-margin,
                    (coin.getY() + 1) * cellSize-margin,
                    coinPaint
            );
        }

        canvas.drawRect(
                player.getX()*cellSize+margin,
                player.getY()*cellSize+margin,
                (player.getX()+1)*cellSize-margin,
                (player.getY()+1)*cellSize-margin,
                playerPaint);

        canvas.drawRect(
                exit.getX()*cellSize+margin,
                exit.getY()*cellSize+margin,
                (exit.getX()+1)*cellSize-margin,
                (exit.getY()+1)*cellSize-margin,
                exitPaint);
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
        invalidate();
    }

    private void playerAtExit(){
        // check if the player has arrived at the exit. Create a new maze if this has happened.
        if(player.getX() == exit.getX() && player.getY()== exit.getY())
            createMaze();
        Iterator<Coin> itr = coins.iterator();
        while (itr.hasNext()) {
            Coin coin = itr.next();
            if (player.getX() == coin.getX() && player.getY()== coin.getY()) {
                itr.remove();
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
