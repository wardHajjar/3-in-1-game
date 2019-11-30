package com.example.dungeonescape.game.collectable;

import com.example.dungeonescape.game.GameObject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;


public class Potion extends GameObject implements Collectable {

    private Boolean available;
    private Rect potionShape;

    public Potion(int x, int y, int size) {
        super(x, y);
        available = true;
        setPaintColour(Color.GREEN);
        potionShape = new Rect(x, y, x + size, y + size);
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

    @Override
    public Rect getItemShape(){
        return potionShape;
    }
}
