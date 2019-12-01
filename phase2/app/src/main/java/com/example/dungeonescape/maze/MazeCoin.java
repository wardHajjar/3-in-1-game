package com.example.dungeonescape.maze;

import android.graphics.Canvas;

import com.example.dungeonescape.game.collectable.Coin;
import com.example.dungeonescape.game.Drawable;

class MazeCoin extends Coin implements Drawable {

    private MazeData mazeData;

    MazeCoin(int x, int y, int coinRadius) {
        super(x, y, coinRadius);
    }

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

    void setMazeData(MazeData mazeData) {
        this.mazeData = mazeData;
    }
}
