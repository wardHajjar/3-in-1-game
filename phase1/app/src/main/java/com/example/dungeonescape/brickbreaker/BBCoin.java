package com.example.dungeonescape.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Creates a coin that the user can collect upon ball collision with the coin.
 */
class BBCoin extends BBObject{

    private int radius;
    private boolean collect;
    BBCoin(int x, int y, int radius) {
        super(x, y);
        this.radius = radius;
        collect = false;
    }

    /** Draws coin onto the canvas.
     * @param canvas the graphic context on which the coin is drawn.
     */
    @Override
    void draw(Canvas canvas) {
        paintStyle.setStyle(Paint.Style.FILL);
        paintStyle.setColor(Color.YELLOW);
        paintStyle.setStrokeWidth(3);
        canvas.drawCircle(x, y, radius, paintStyle);
    }

    /**
     * Returns the rectangle representation of the coin.
     * @return Rect representation of the coin.
     */
    Rect getRect () {
        return new Rect(x - radius, y - radius, x + radius, y+ radius);
    }

    /**
     * Sets the coin's collect status to true.
     */
    void gotCollected () {
        collect = true;
    }

    /**
     * Returns the coin's collect status.
     * @return boolean of if the coin has been collected.
     */
    boolean getCollectStatus() {
        return this.collect;
    }

}
