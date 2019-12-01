package com.example.dungeonescape.game.collectable;

import com.example.dungeonescape.game.GameObject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Path;
import com.example.dungeonescape.game.Drawable;


public class Potion extends GameObject implements Collectable, Drawable {

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
        int width = potionShape.width();
        int x = getX();
        int y = getY();

        Path path = new Path();

        path.moveTo(x + width/3, y + width/3);
        path.lineTo(x + width/3, y);
        path.lineTo(x + (width * 2/3), y);
        path.lineTo(x + (width * 2/3), y + width/3);
        path.lineTo(x + width, y + width/3);
        path.lineTo(x + (width * 2/3), y + width);
        path.lineTo(x + width/3, y + width);
        path.lineTo(x, y + width/3);
        path.lineTo(x + width/3, y + width/3);
        path.close();

        canvas.drawPath(path, getPaint());
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
