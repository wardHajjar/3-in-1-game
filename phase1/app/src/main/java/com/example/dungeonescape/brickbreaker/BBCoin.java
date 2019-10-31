package com.example.dungeonescape.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

class BBCoin extends BBObject{

    private int radius;
    private boolean collect;
    private boolean drawn;
    BBCoin(int x, int y, int radius) {
        super(x, y);
        this.radius = radius; // set appropriate radius
        collect = false;
        drawn = false;
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

    boolean getDrawStatus(){ return this.drawn; }
    void setVisible(){ this.drawn = true; }

}
