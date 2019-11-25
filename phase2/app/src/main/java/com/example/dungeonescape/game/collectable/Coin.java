package com.example.dungeonescape.game.collectable;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.shapes.RectShape;

import com.example.dungeonescape.game.GameObject;

public class Coin extends GameObject {
    private Rect coinShape;

    public Coin() {
        super();
        setPaintColour(Color.YELLOW);
    }

    public Coin(int x, int y) {
        super(x, y);
        setPaintColour(Color.YELLOW);
    }

    public void setCoinShape() {
        int x = getX();
        int y = getY();
        this.coinShape = new Rect(x, y, (x + 1), (y + 1));
    }

    public Rect getCoinShape() {
        return coinShape;
    }
}
