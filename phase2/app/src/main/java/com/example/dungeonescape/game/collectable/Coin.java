package com.example.dungeonescape.game.collectable;

import com.example.dungeonescape.game.GameObject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;


public class Coin extends GameObject implements Collectable {

    private Rect coinShape;
    private Boolean available;

    public Coin(int x, int y, int size) {
        super(x, y);
        setPaintColour(Color.YELLOW);
        this.available = true;
        this.coinShape = new Rect(x, y, x + size, y + size);
    }

    public void updateCoinLocation(int x, int y) {
        this.coinShape.top = getX();
        this.coinShape.left = getY();
    }

    public Rect getCoinShape() {
        return coinShape;
    }

    protected Rect getCoinShape(int radius) {
        return new Rect(getX() - radius / 2, getY() - radius / 2,
                getX() + radius / 2, getY() + radius / 2);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(this.getX(), this.getY(), this.coinShape.width()/2, this.getPaint());
    }

    @Override
    public Boolean getAvailableStatus() {
        return this.available;
    }

    @Override
    public void gotCollected() {
        this.available = false;
    }



}
