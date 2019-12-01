package com.example.dungeonescape.maze;

import android.graphics.Canvas;

import com.example.dungeonescape.game.collectable.Coin;
import com.example.dungeonescape.game.Drawable;
import com.example.dungeonescape.game.collectable.Collectable;

/** Represents one MazeCoin for the Maze Game. This MazeCoin can be drawn and collected. */
class MazeCoin extends Coin implements Collectable, Drawable {

    private MazeData mazeData;

    MazeCoin(int x, int y, int coinRadius) {
        super(x, y, coinRadius);
    }

    @Override
    public void draw(Canvas canvas) {
        float cellSize = mazeData.getCellSize();
        float margin = cellSize / 10;
        canvas.drawOval(
                this.getX() * cellSize + margin,
                this.getY() * cellSize + margin,
                (this.getX() + 1) * cellSize - margin,
                (this.getY() + 1) * cellSize - margin,
                this.getPaint());
    }

    /** Sets the mazeData variable equal to the passed in MazeData instance.
     *
     * @param mazeData the instance of MazeData that this MazeCoin will read from.
     */
    void setMazeData(MazeData mazeData) {
        this.mazeData = mazeData;
    }
}
