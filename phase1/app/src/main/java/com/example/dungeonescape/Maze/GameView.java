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
 * The code from GameView was from the following video: https://www.youtube.com/watch?v=I9lTBTAk5MU.
 *
 * TODO: Edit this javadoc as we change the code below.
 */

public class GameView  extends View {
    private Cell[][] cells;
    private static final int COLS =20, ROWS = 20;
    private static final float wallThickness = 4;
    private float cellSize, hMargin, vMargin;
    private Paint wallPaint;
    private Random rand = new Random();


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        wallPaint = new Paint();
        wallPaint.setColor(Color.WHITE);
        wallPaint.setStrokeWidth(wallThickness);
        createMaze();
    }

    private void createMaze(){
        Stack<Cell> stack = new Stack<>();
        Cell current, next;

        cells = new Cell[COLS][ROWS];

        for(int x=0; x<COLS; x++){
            for(int y=0; y<ROWS; y++){
                cells[x][y] = new Cell(x,y);
            }
        }
        current = cells[0][0];
        current.visited = true;

        do {
            next = getNeighbor(current);
            if (next != null) {
                removeWall(current, next);
                stack.push(current);
                current = next;
                current.visited = true;
            } else {
                current = stack.pop();
            }
        } while (!stack.isEmpty());
    }

    private Cell getNeighbor(Cell cell){
        ArrayList<Cell> neighbors = new ArrayList<>();
        // left
        if (cell.col-1>=0 && !cells[cell.col-1][cell.row].visited)
            neighbors.add(cells[cell.col-1][cell.row]);
        // right
        if (cell.col+1<COLS && !cells[cell.col+1][cell.row].visited)
            neighbors.add(cells[cell.col+1][cell.row]);
        // bottom
        if (cell.row+1<ROWS && !cells[cell.col][cell.row+1].visited)
            neighbors.add(cells[cell.col][cell.row+1]);
        // top
        if (cell.row-1>=0 && !cells[cell.col][cell.row-1].visited)
            neighbors.add(cells[cell.col][cell.row-1]);
        if (!neighbors.isEmpty())
            return neighbors.get(rand.nextInt(neighbors.size()));
        else
            return null;
    }

    private void removeWall(Cell current, Cell next){
        if (current.col == next.col-1){
            current.rightWall= false;
            next.leftWall = false;
        } else if (current.col == next.col+1){
            current.leftWall = false;
            next.rightWall = false;
        } else if (current.row == next.row-1) {
            current.bottomWall = false;
            next.topWall = false;
        } else {
            current.topWall = false;
            next.bottomWall = false;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        int width = getWidth();
        int height = getHeight();

        if((float) width/height < (float) COLS/ROWS)
            cellSize = width / (COLS + 1);
        else
            cellSize = height / (ROWS + 1);


        System.out.println(cellSize);

        hMargin = (width - COLS*cellSize)/2;
        vMargin = (height - ROWS*cellSize)/2;

        canvas.translate(hMargin, vMargin);

        for(int x=0; x<COLS; x++){
            for(int y=0; y<ROWS; y++){
                if (cells[x][y].topWall)
                    canvas.drawLine(
                            x*cellSize,
                            y*cellSize,
                            (x+1)*cellSize,
                            y*cellSize,
                            wallPaint);

                if (cells[x][y].leftWall)
                    canvas.drawLine(
                            x*cellSize,
                            y*cellSize,
                            x*cellSize,
                            (y+1)*cellSize,
                            wallPaint);

                if (cells[x][y].bottomWall)
                    canvas.drawLine(
                            x*cellSize,
                            (y+1)*cellSize,
                            (x+1)*cellSize,
                            (y+1)*cellSize,
                            wallPaint);

                if (cells[x][y].rightWall)
                    canvas.drawLine(
                            (x+1)*cellSize,
                            y*cellSize,
                            (x+1)*cellSize,
                            (y+1)*cellSize,
                            wallPaint);
            }
        }
    }

    private class Cell{
        boolean
            topWall = true,
            bottomWall = true,
            leftWall = true,
            rightWall = true,
            visited = false;

        int col, row;

        Cell(int col, int row) {
            this.col = col;
            this.row = row;
        }
    }
}
