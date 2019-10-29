package com.example.dungeonescape.Maze;

import java.util.Random;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.dungeonescape.Player;

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

    /** Player and exit objects, and their positions. */
    private Player player;
    private MazeCell playerLoc;
    private MazeCell exitLoc;

    /** The number of columns and rows in this maze. */
    private int numMazeCols = 20;
    private int numMazeRows = 20;

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

    private enum Direction{
        UP, DOWN, LEFT, RIGHT
    }

    private Random rand = new Random();

    /** Instantiates the MazeManager class for this Maze. */
    private MazeManager thisMaze = new MazeManager();

    public MazeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        player = new Player(0,0,1, "PLAYER");
        wallPaint = new Paint();
        wallPaint.setColor(Color.WHITE);
        wallPaint.setStrokeWidth(wallThickness);
        playerPaint = new Paint();
        playerPaint.setColor(Color.RED);
        exitPaint = new Paint();
        exitPaint.setColor(Color.BLUE);
        createMaze();
    }

    private void createMaze(){
        Stack<MazeCell> stack = new Stack<>();
        MazeCell current, next;
        int mazeCols = getNumMazeCols();
        int mazeRows = getNumMazeRows();

        cells = new MazeCell[mazeCols][mazeRows];

        for (int x = 0; x < mazeCols; x++) {
            for (int y = 0; y < mazeRows; y++) {
                cells[x][y] = new MazeCell(x, y, 1);
            }
        }

        current = cells[0][0];
        current.setVisited(true);

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
        exitLoc = cells[numMazeCols-1][numMazeRows-1];
    }

    private MazeCell getNeighbour(MazeCell cell) {
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

        canvas.translate(horizontalPadding, verticalPadding);

        for(int x = 0; x < numMazeCols; x++) {
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



        float margin = cellSize/10;

        canvas.drawRect(
                playerLoc.getX()*cellSize+margin,
                playerLoc.getY()*cellSize+margin,
                (playerLoc.getX()+1)*cellSize-margin,
                (playerLoc.getY()+1)*cellSize-margin,
                playerPaint);

        canvas.drawRect(
                exitLoc.getX()*cellSize+margin,
                exitLoc.getY()*cellSize+margin,
                (exitLoc.getX()+1)*cellSize-margin,
                (exitLoc.getY()+1)*cellSize-margin,
                exitPaint);
    }

    private void movePlayer(Direction direction){
        switch (direction){
            case UP:
                if(!playerLoc.isTopWall())
                    playerLoc = cells[playerLoc.getX()][playerLoc.getY()-1];
                break;
            case DOWN:
                if(!playerLoc.isBottomWall())
                    playerLoc = cells[playerLoc.getX()][playerLoc.getY()+1];
                break;
            case LEFT:
                if(!playerLoc.isLeftWall())
                    playerLoc = cells[playerLoc.getX()-1][playerLoc.getY()];
                break;
            case RIGHT:
                if(!playerLoc.isRightWall())
                    playerLoc = cells[playerLoc.getX()+1][playerLoc.getY()];
                break;
        }
        checkExit();
        invalidate();
    }

    private void checkExit(){
        if(playerLoc == exitLoc)
            createMaze();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
            return true;

        if(event.getAction() == MotionEvent.ACTION_MOVE){
            float x = event.getX();
            float y = event.getY();

            float playerCenterX = horizontalPadding + (playerLoc.getX()+0.5f)*cellSize;
            float playerCenterY = verticalPadding + (playerLoc.getY()+0.5f)*cellSize;

            float dx = x - playerCenterX;
            float dy = y - playerCenterY;

            float absDx = Math.abs(dx);
            float absDy = Math.abs(dy);

            if(absDx > cellSize || absDy > cellSize){
                if(absDx > absDy){
                    //move in x direction
                    if (dx > 0)
                        movePlayer(Direction.RIGHT);
                    else
                        movePlayer(Direction.LEFT);
                }
                else {
                    //move in y direction
                    if(dy>0)
                        movePlayer(Direction.DOWN);
                    else
                        movePlayer((Direction.UP));
                }
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    public int getNumMazeCols() {
        return numMazeCols;
    }

    public int getNumMazeRows() {
        return numMazeRows;
    }
}
