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
    private int speed = 5;
    private Paint paint;
    private RectF oval;
    private PlatformerManager manager;
    private float bottom;
    private Rect rect;

    private boolean start;

    private int gameScore;

    //    private Player user; // referencing from player class for lives
    private int lives;

    int getGamescore(){
        return this.gameScore;
    }
    void setGamescore(int gameScore){
        this.gameScore = gameScore;
    }

    //    int getGamelives(){
//        return user.getNumLives();
//    }

    int getLives(){
        return this.lives;
    }
    void setLives(int lives){
        this.lives = lives;
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

        this.lives = 3;

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
            speed = - 75;
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
    private void collision_detection() {
        this.bottom = this.y+ (size/2);
        if (speed > 10) {
            for(Platforms platform: manager.getPlatforms()) {
                if (this.rect.intersect(platform.rectangle) || (Math.abs((int)bottom - (int)platform.gety()) < 20 &&
                        x > platform.getx() && x < platform.getx() + 150)) {
                    System.out.println("hit");
                    this.gameScore += 1;
                    y = platform.gety() - size/2;
                    speed = -75;
                    y += speed;
                    start = true;
                    this.oval = new RectF(x-size/2,(int)(y + size/4),x+size/2,y+size/2 + 5);
                    Rect bounds = new Rect();
                    this.oval.roundOut(bounds);
                }

            }
        }

    }
    void coin_detection() {
        this.bottom = this.y+ (size/2);

        for(Coin coin: manager.getCoins()) {
            if (this.rect.intersect(coin.rectangle)) {
                coin.gotCoin();
            }
        }

    }

    void draw(Canvas canvas) {
        canvas.drawOval(this.oval,this.paint);
    }

    boolean isAlive() {
        if (start && y > manager.getGridHeight()) {
            return false;
        }
        return true;
    }

    void move_left() {
        if (x - 50 <= 0) {
            x = manager.getGridWidth();
        }
        else {
            x -= 50;
        }
    }
    void move_right() {
        if (x + 50 >= manager.getGridWidth()) {
            x = 0;
        }
        else {
            x += 50;
        }
    }
}
//577 is max height