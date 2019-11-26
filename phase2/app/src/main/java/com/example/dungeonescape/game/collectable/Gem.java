package com.example.dungeonescape.game.collectable;

import com.example.dungeonescape.game.GameObject;

import android.graphics.Canvas;


public class Gem extends GameObject implements Collectable {

    private Boolean available;

    public Gem(int x, int y) {
        super(x, y);
        available = true;
    }

    @Override
    public void draw(Canvas canvas) {

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
