package com.example.dungeonescape.platformer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;


class Coin extends Level3Object {

    Coin(int x, int y, int size, PlatformerManager manager) {
        super(x,y,size,manager);
        paint.setColor(Color.YELLOW);
        this.rect = new Rect(x - size / 2, (int) (y - size /2), x + size / 2, y + size / 2);

    }
    Coin(int x, int y, int size) {
        super(x,y,size);
        paint.setColor(Color.YELLOW);
        this.rect = new Rect(x - size / 2, (int) (y - size /2), x + size / 2, y + size / 2);
    }

    Rect getRect() {
        return rect;
    }

    void gotCoin() {
        // Character has gotten the coin, delete this coin and regenerate one at a new y value
        this.y = 0;
        Random r = new Random();
        int a = r.nextInt(1080- 150);
        this.x = a;
        this.rect = new Rect((int)(x - size / 2), (int) (y - size/2), (int) (x + size / 2), (int) (y + size / 2));
        this.oval = new RectF(x - size / 2, y - size / 2, x + size / 2, y + size / 2);
    }
    void update(int down) {
        // Moves platforms down
        coinDown(down);
        this.rect = new Rect((int)(x - size / 2), (int) (y - size /2), (int) (x + size / 2), (int) (y + size / 2));
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
