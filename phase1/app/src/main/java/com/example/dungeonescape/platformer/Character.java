package com.example.dungeonescape.platformer;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

class Character {
    private int direction;
    private int x,y,size;
    private double speed = 7.0;
    private int height;
    private Paint paint;
    private RectF oval;
    private PlatformerManager manager;


    Character(int x, int y, int size, PlatformerManager manager){
        this.x = x;
        this.y = y;
        this.size = size;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        direction = 1;
        this.manager = manager;
        this.oval = new RectF(x-size/2,y-size/2,x+size/2,y+size/2);
    }

    void move() {

         double gravity = 3.0;
        if (direction == 1) {

            speed += gravity;
        }
        else {
            speed -= gravity;
        }
        if (y+ size + speed*direction - manager.getGridHeight() > 0) {
            y = manager.getGridHeight();
            direction = -direction;
            speed = -80;
            y += speed;
        }
        else{
            y += speed*direction;
        }
        this.oval = new RectF(x-size/2,y-size/2,x+size/2,y+size/2);

        //Do we need to bounce next time?
        Rect bounds = new Rect();
        this.oval.roundOut(bounds); ///store our int bounds
        System.out.println(speed);
        System.out.println(y);

    }
//    void collision_detection() {
//        for(Platforms platform: manager.platforms) {
//            if (direction == -1) {
//                if (y == platform.gety() && x <= platform.getx + length && x >= platform.getx ) {
//                    // Bounce on this platform
//                    direction = -direction;
//                    speed = -80;
//                    y += speed;
//                    manager.move_down();
//                }
//            }
//        }
//    }
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