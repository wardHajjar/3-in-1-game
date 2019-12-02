package com.example.dungeonescape.game.collectable;

import com.example.dungeonescape.game.GameObject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Path;
import com.example.dungeonescape.game.Drawable;

import java.io.Serializable;
import java.util.Random;

public class Gem extends GameObject implements Collectable, Drawable, Serializable {

    private Boolean available;
    private Rect gemShape;
    /** The size of the Gem. */
    private int size;

    public Gem(int x, int y, int size) {

        super(x, y);
        this.size = size;
        available = true;
        setPaintColour(Color.BLUE);
        gemShape = new Rect(x, y, x + size, y + size);
    }

    @Override
    public void draw(Canvas canvas) {
        /*
         * Math required to draw the gem shape is taken from:
         * https://stackoverflow.com/questions/51370566/diamond-shape-in-android-studio/51372564
         */
        int width = gemShape.width();
        int x = getX();
        int y = getY();

        Path path = new Path();

        path.moveTo(x, y + width); // Top
        path.lineTo(x - width/2, y); // Left
        path.lineTo(x, y - width); // Bottom
        path.lineTo(x + width/2, y); // Right
        path.lineTo(x, y + width); // Back to Top
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

        return gemShape;
    }
    /** Moves the Gem down when the Character jumps up. */
    public void update(int down, int height) {

        if (getY() + down > height) {
            /* Moves coin up if the Character moves down without collection the PlatformerCoin. */
            int diff = Math.abs(getY() + down - height);
            if (diff > 400) {
                setY(0);
            } else if (diff > 200) {
                setY(-200);
            } else {
                setY(-diff);
            }
            Random r = new Random();
            int a = r.nextInt(height - 150);
            this.setX(a);
        } else {
            incY(down);
        }
        updateGemLocation();
    }
    private void updateGemLocation() {
        this.gemShape.top = getY();
        this.gemShape.right = getX() + size;
        this.gemShape.bottom = getY() + size;
        this.gemShape.left = getX();
    }

    public void gotCollectable() {
        setY(0);
        Random r = new Random();
        setX(r.nextInt(1080 - 150));
        updateGemLocation();
    }
}
