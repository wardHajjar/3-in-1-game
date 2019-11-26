package com.example.dungeonescape.game.collectable;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.shapes.RectShape;

import com.example.dungeonescape.game.GameObject;

public class Coin extends GameObject {

    public Coin() {
        super();
        setPaintColour(Color.YELLOW);
    }

    public Coin(int x, int y) {
        super(x, y);
        setPaintColour(Color.YELLOW);
    }

    public Rect getCoinShape() {
        int x = getX();
        int y = getY();
        return new Rect(x, y, (x + 1), (y + 1));
    }
}
