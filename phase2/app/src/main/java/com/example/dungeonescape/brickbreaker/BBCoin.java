package com.example.dungeonescape.brickbreaker;

import com.example.dungeonescape.game.collectable.Coin;
import android.graphics.Canvas;

/** Creates a coin that the user can collect upon ball collision with the coin. */
class BBCoin extends Coin {

    private int radius;

    BBCoin(int x, int y, int radius) {
        super(x, y, radius);
        this.radius = radius;
    }

//    /** Draws coin onto the canvas.
//     * @param canvas the graphic context on which the coin is drawn.
//     */
//    @Override
//    public void draw(Canvas canvas) {
//        canvas.drawCircle(this.getX(), this.getY(), radius, this.getPaint());
//    }

}
