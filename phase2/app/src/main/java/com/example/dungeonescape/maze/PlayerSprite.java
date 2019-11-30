package com.example.dungeonescape.maze;

import android.graphics.Canvas;

import com.example.dungeonescape.game.GameObject;

class PlayerSprite extends GameObject {
    PlayerSprite(){}

    public void paintObject(Canvas canvas, float cellSize, float margin) {
        canvas.drawRect(
                getX() * cellSize + margin,
                getY() * cellSize + margin,
                (getX() + 1) * cellSize - margin,
                (getY() + 1) * cellSize - margin,
                getPaint());
    }
}
