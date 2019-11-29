package com.example.dungeonescape.game.collectable;

import com.example.dungeonescape.game.GameObject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.Random;


public class Coin extends GameObject implements Collectable {

    /** The shape of the coin. */
    private Rect coinShape;
    /** The available status of the coin */
    private Boolean available;
    /** The radius of the coin. */
    private int size;

    public Coin(int x, int y, int size) {
        super(x, y);
        setPaintColour(Color.YELLOW);
        this.available = true;
        this.size = size;
        this.coinShape = new Rect(x, y, x + size, y + size);
    }

    private void updateCoinLocation() {
        this.coinShape.top = getX();
        this.coinShape.right = getX() + size;
        this.coinShape.bottom = getY() + size;
        this.coinShape.left = getY();
    }

    public void gotCoin() {
        setY(0);
        Random r = new Random();
        setX(r.nextInt(1080 - 150));
        updateCoinLocation();
    }
    public Rect getCoinShape() {
        return coinShape;
    }

    protected Rect getCoinShape(int size) {
        return new Rect(getX(), getY(),
                getX() + size, getY() + size);
    }
    /** Moves the coin down when the Character jumps up. */
    public void update(int down, int height) {

        if (getY() + down > height) {
            /* Moves coin up if the Character moves down without collection the PlatformerCoin. */
            int diff = Math.abs(getY() + down - height);
            if (diff > 400) {
                setY(0);
            }
            else if (diff > 200) {
                setY(-200);
            }
            else {
                setY(-diff);
            }
            Random r = new Random();
            int a = r.nextInt(height - 150);
            this.setX(a);
        }
        else {
            incY(down);
        }
        updateCoinLocation();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(this.getX(), this.getY(), size, this.getPaint());
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
