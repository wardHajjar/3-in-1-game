package com.example.dungeonescape.game.collectable;

import com.example.dungeonescape.game.GameObject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;


public class Potion extends GameObject implements Collectable {

    private Boolean available;

    public Potion(int x, int y) {
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
