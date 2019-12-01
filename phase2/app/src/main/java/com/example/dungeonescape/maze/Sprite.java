package com.example.dungeonescape.maze;

import android.graphics.Canvas;

import com.example.dungeonescape.game.Drawable;
import com.example.dungeonescape.game.GameObject;

class Sprite extends GameObject implements Drawable {
    private MazeData mazeData;

    Sprite(){}

    /** Draws the PlayerSprite square on the screen.
     *
     * @param canvas the Canvas to draw the PlayerSprite on. */
    public void draw(Canvas canvas) {
        float margin = mazeData.getCellSize() / 10;
        canvas.drawRect(
                getX() * mazeData.getCellSize() + margin,
                getY() * mazeData.getCellSize() + margin,
                (getX() + 1) * mazeData.getCellSize() - margin,
                (getY() + 1) * mazeData.getCellSize() - margin,
                getPaint());
    }

    /** Sets the mazeData variable equal to the passed in MazeData instance.
     *
     * @param mazeData the instance of MazeData that this Sprite will read from.
     */
    void setMazeData(MazeData mazeData) {
        this.mazeData = mazeData;
    }
}

