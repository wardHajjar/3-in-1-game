package com.example.dungeonescape.Maze;

import java.util.Random;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The code from GameView was from the following videos:
 * https://www.youtube.com/watch?v=I9lTBTAk5MU
 * https://www.youtube.com/watch?v=iri0wZ3NvdQ
 *
 * TODO: Edit this javadoc as we change the code below.
 */

public class GameView extends View {
    /** A 2D Array of MazeCell cells. */
    private MazeCell[][] cells;

    /** The number of columns and rows in this maze. */
    private int numMazeCols = 20;
    private int numMazeRows = 20;

    /** The line thickness of the walls. */
    private static final float wallThickness = 4;

    /** Represents the colour of the walls. */
    private Paint wallPaint;

    private Random rand = new Random();

    /** Instantiates the MazeManager class for this Maze. */
    private MazeManager thisMaze = new MazeManager();

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        wallPaint = new Paint();
        wallPaint.setColor(Color.WHITE);
        wallPaint.setStrokeWidth(wallThickness);
        createMaze();
    }

    private void createMaze(){
        Stack<MazeCell> stack = new Stack<>();
        MazeCell current, next;

        cells = new MazeCell[numMazeCols][numMazeRows];

        for(int x=0; x<numMazeCols; x++){
            for(int y=0; y<numMazeRows; y++){
                cells[x][y] = new MazeCell(x,y, 0);
            }
        }
        current = cells[0][0];
        current.setVisited(true);

        do {
            next = getNeighbor(current);
            if (next != null) {
                removeWall(current, next);
                stack.push(current);
                current = next;
                current.setVisited(true);
            } else {
                current = stack.pop();
            }
        } while (!stack.isEmpty());
    }

    private MazeCell getNeighbor(MazeCell cell){
        ArrayList<MazeCell> neighbors = new ArrayList<>();
        // left
        if (cell.getX()-1>=0 && !cells[cell.getX()-1][cell.getY()].isVisited())
            neighbors.add(cells[cell.getX()-1][cell.getY()]);
        // right
        if (cell.getX()+1<numMazeCols && !cells[cell.getX()+1][cell.getY()].isVisited())
            neighbors.add(cells[cell.getX()+1][cell.getY()]);
        // bottom
        if (cell.getY()+1<numMazeRows && !cells[cell.getX()][cell.getY()+1].isVisited())
            neighbors.add(cells[cell.getX()][cell.getY()+1]);
        // top
        if (cell.getY()-1>=0 && !cells[cell.getX()][cell.getY()-1].isVisited())
            neighbors.add(cells[cell.getX()][cell.getY()-1]);
        if (!neighbors.isEmpty())
            return neighbors.get(rand.nextInt(neighbors.size()));
        else
            return null;
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
        int width = getWidth();
        int height = getHeight();

        float cellSize = thisMaze.calculateCellSize(width, height, numMazeCols, numMazeRows);

        System.out.println(cellSize);

        float hMargin = (width - numMazeCols * cellSize) / 2;
        float vMargin = (height - numMazeRows * cellSize) / 2;

        canvas.translate(hMargin, vMargin);

        for(int x = 0; x < numMazeCols; x++) {
            for(int y = 0; y < numMazeRows; y++) {
                if (cells[x][y].isTopWall())
                    canvas.drawLine(
                            x * cellSize,
                            y * cellSize,
                            (x + 1) * cellSize,
                            y * cellSize,
                            wallPaint);

                if (cells[x][y].isLeftWall())
                    canvas.drawLine(
                            x * cellSize,
                            y * cellSize,
                            x * cellSize,
                            (y + 1) * cellSize,
                            wallPaint);

                if (cells[x][y].isBottomWall())
                    canvas.drawLine(
                            x * cellSize,
                            (y + 1) * cellSize,
                            (x + 1) * cellSize,
                            (y + 1) * cellSize,
                            wallPaint);

                if (cells[x][y].isRightWall())
                    canvas.drawLine(
                            (x + 1) * cellSize,
                            y * cellSize,
                            (x + 1) * cellSize,
                            (y + 1) * cellSize,
                            wallPaint);
            }
        }
    }
}
