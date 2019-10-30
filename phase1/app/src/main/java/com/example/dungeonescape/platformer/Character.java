package com.example.dungeonescape.platformer;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;

import androidx.constraintlayout.solver.widgets.Rectangle;


class Character extends RectShape {

    private float x,y,size;
    private int direction = 1;
    private int speed = 5;
    private int height;
    private Paint paint;
    private RectF oval;
    private PlatformerManager manager;
    private float bottom;
    private Rect rect;

    private boolean start;

    private int gameScore;

    int getGamescore(){
        return this.gameScore;
    }
    void setGamescore(int gameScore){
        this.gameScore = gameScore;
    }

    Character(int x, int y, int size, PlatformerManager manager){
        this.x = x;
        this.y = y;
        this.size = size;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        start = false;

        this.gameScore = 0;
        this.manager = manager;
        this.rect = new Rect(x-size/2,(int)(y + size/4),x+size/2,y+size/2);
        this.oval = new RectF(x-size/2,y-size/2,x+size/2,y+size/2);
    }
    void setY(float y) {
        this.y = y;
    }
    float getX() {
        return x;
    }
    float getY() {
        return y;
    }
    void move() {
        collision_detection();

        double gravity = 3.0;
        if (speed >= 0) {

            speed += gravity;
        }
        else {
            speed += gravity;
        }
        if (y+ size/2 + speed - manager.getGridHeight() > 0 && !start) {
            y = manager.getGridHeight();
            speed = - 80;
            y += speed;
        }
        else{
            y += speed;
        }
        System.out.println(speed);

        this.oval = new RectF(x-size/2,y-size/2,x+size/2,y+size/2 + 5);
        this.rect = new Rect((int)(x-size/3),(int)(y + size/4),(int)(x+size/2),(int)(y+size/3));
        Rect bounds = new Rect();
        this.oval.roundOut(bounds);
    }
    boolean collision_detection() {
        this.bottom = this.y+ (size/2);
        boolean a = false;
        if (speed > 10) {
            for(Platforms platform: manager.getPlatforms()) {
                if (this.rect.intersect(platform.rectangle) || (Math.abs((int)bottom - (int)platform.gety()) < 20 &&
                        x > platform.getx() && x < platform.getx() + 150)) {
                    System.out.println("hit");
                    this.gameScore+=1;
                    y = platform.gety() - size/2;
                    speed = -90;
                    y += speed;
                    start = true;
                    this.oval = new RectF(x-size/2,(int)(y + size/4),x+size/2,y+size/2 + 5);
                    Rect bounds = new Rect();
                    this.oval.roundOut(bounds);
                    a = true;
                }

            }
        }
        return a;
    }
    void draw(Canvas canvas) {
        canvas.drawOval(this.oval,this.paint);
    }

    void move_left() {
        x -= 50;
    }
    void move_right() {
        x += 50;
    }
}
//577 is max height