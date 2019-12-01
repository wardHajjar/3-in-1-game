package com.example.dungeonescape.maze;

import android.graphics.Canvas;

import com.example.dungeonescape.game.GameObject;

class Sprite extends GameObject {
    Sprite(){}

    /** Draws the PlayerSprite square on the screen.
     *
     * @param canvas the Canvas to draw the PlayerSprite on.
     * @param margin the space around the PlayerSprite square. */
    void draw(Canvas canvas, float cellSize, float margin) {
        canvas.drawRect(
                getX() * cellSize + margin,
                getY() * cellSize + margin,
                (getX() + 1) * cellSize - margin,
                (getY() + 1) * cellSize - margin,
                getPaint());
    }
}
