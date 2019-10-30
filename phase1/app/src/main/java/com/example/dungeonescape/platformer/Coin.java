package com.example.dungeonescape.platformer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RectShape;

import java.util.Random;

public class Coin extends RectShape {
    private float x,y,size;
    private Paint paint;
    private RectF oval;
    private PlatformerManager manager;
    private float bottom;
    Rect rectangle;

    Coin(int x, int y, int size, PlatformerManager manager) {
        this.x = x;
        this.y = y;
        this.size = size;
        paint = new Paint();
        paint.setColor(Color.YELLOW);

        this.manager = manager;
        this.rectangle = new Rect(x - size / 2, (int) (y - size /2), x + size / 2, y + size / 2);
        this.oval = new RectF(x - size / 2, y - size / 2, x + size / 2, y + size / 2);
    }
    void draw(Canvas canvas) {
        canvas.drawOval(this.oval,this.paint);
    }
    void gotCoin() {
        // Character has gotten the coin, delete this coin and regenerate one at a new y value
        this.y = 0;
        Random r = new Random();
        int a = r.nextInt(1080- 150);
        this.x = a;
        this.rectangle = new Rect((int)(x - size / 2), (int) (y - size/2), (int) (x + size / 2), (int) (y + size / 2));
        this.oval = new RectF(x - size / 2, y - size / 2, x + size / 2, y + size / 2);
    }
    void update(int down) {
        // Moves platforms down
        coinDown(down);
        this.rectangle = new Rect((int)(x - size / 2), (int) (y - size /2), (int) (x + size / 2), (int) (y + size / 2));
        this.oval = new RectF(x - size / 2, y - size / 2, x + size / 2, y + size / 2);
    }
    private void coinDown(int down) {
        if (this.y + down > manager.getGridHeight()) {
            // Move it to the top
            int diff = Math.abs((int)this.y + down - manager.getGridHeight());
            if (diff > 400) {
                this.y = 0;
            }
            else if (diff > 200) {
                this.y = 200;
            }
            else {
                this.y = diff;
            }
            Random r = new Random();
            int a = r.nextInt(1080- 150);
            this.x = a;
        }
        else {
            this.y = (this.y + down);

        }
    }

}
