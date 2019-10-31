package com.example.dungeonescape.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class BBCoin extends BBObject{

    private int radius;
    private boolean collect;

    BBCoin(int x, int y) {
        super(x, y);
        radius = 30; // set appropriate radius
        collect = false;
    }

    @Override
    void draw(Canvas canvas) {
        paintStyle.setStyle(Paint.Style.FILL);
        paintStyle.setColor(Color.YELLOW);
        paintStyle.setStrokeWidth(3);
        canvas.drawCircle(x, y, radius, paintStyle);
    }

    Rect getRect () {
        return new Rect(x - radius, y - radius, x + radius, y+ radius);
    }

    void gotCollected (){
        collect = true;
    }

    boolean getCollectStatus() {
        return this.collect;
    }

}
