package com.example.dungeonescape.brickbreaker;

import com.example.dungeonescape.game.collectable.Coin;
import android.graphics.Canvas;

/** Creates a coin that the user can collect upon ball collision with the coin. */
class BBCoin extends Coin {

    private int radius;
    private boolean collect;

    BBCoin(int x, int y, int radius) {
        super(x, y);
        this.radius = radius;
        collect = false;
        setCoinShape(x, y, radius);
    }

    /** Draws coin onto the canvas.
     * @param canvas the graphic context on which the coin is drawn.
     */
    void draw(Canvas canvas) {
        canvas.drawCircle(this.getX(), this.getY(), radius, this.getPaint());
    }

    /** Sets the coin's collect status to true. */
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
