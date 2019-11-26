package com.example.dungeonescape.game.collectable;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.shapes.RectShape;

import com.example.dungeonescape.game.GameObject;

public class Coin extends GameObject {
    private Rect coinShape;

    public Coin(int x, int y) {
        super(x, y);
        setPaintColour(Color.YELLOW);
    }

    public void setCoinShape(int x, int y, int radius){
        coinShape = new Rect(x, y, x + radius, y + radius);
    }

    public void updateCoinLocation(int x, int y) {

        this.coinShape.top = getX();
        this.coinShape.left = getY();
    }

    public Rect getCoinShape() {
        return coinShape;
    }
}
