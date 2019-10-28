package com.example.dungeonescape.platformer;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import java.util.Random;
import java.lang.Math;
import java.util.*;

public class Platforms {
    private double xPositions, yPositions;
    private double length;
    private double width;
    private int height;
    Paint paint;
    private boolean monstAlive;
    private PlatformerManager manager;
    private int velocityY;

    void setxPositions(double x) {
        this.xPositions = x;
    }

    double getxPositions() {
        return this.xPositions;
    }

    void setyPositions(double y) {
        this.yPositions = y;
    }

    double getyPositions() {
        return this.yPositions;
    }


    Platforms(double x, double y, double length, double width, PlatformerManager manager) {
        this.xPositions = x;
        this.yPositions = y;
        this.length = length;
        this.width = width;
        this.velocityY = 0;
        paint = new Paint();
        paint.setColor(Color.GREEN);
        this.manager = manager;
    }

    void update(){
        // Stops character from jumping too high
        platformDown();

        // Spawn Monsters
        if (yPositions > manager.getGridHeight()) {
            yPositions = 0;
            xPositions = (Math.random() * 500);
            monstAlive = true;
        }
    }
    void platformDown(){
        if (yPositions < (Math.round(manager.getGridHeight()/2) - 50)) {
            yPositions = (Math.round(manager.getGridHeight() / 2) - 50) + 1;
            yPositions -= velocityY;
        }
}


//    void drawString(Canvas canvas, String s, int x, int y) {
//        canvas.drawText(s, x * FishTankView.charWidth, y * FishTankView.charHeight, paintText);
//    }


//    public void draw(Canvas canvas) {
//        drawString(canvas, appearance, x, y);
//    }

}
