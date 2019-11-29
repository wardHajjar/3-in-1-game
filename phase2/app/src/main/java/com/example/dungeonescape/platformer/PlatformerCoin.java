package com.example.dungeonescape.platformer;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.example.dungeonescape.game.collectable.Coin;
import java.util.Random;


class PlatformerCoin extends Coin {

    private int radius;
    private PlatformerManager manager;
    private Rect shape;

    PlatformerCoin(int x, int y, int radius, PlatformerManager manager) {

        super(x,y, radius);
        this.radius = radius;
        this.manager = manager;
        this.shape = getCoinShape(radius);
    }


    /** Deletes a PlatformerCoin that the Character has already collected.
     * Creates a new PlatformerCoin at a new y value.
     */


    /** Moves the Platforms down. */
    void update(int down) {
        coinDown(down);
        shape = getCoinShape(radius);
    }
    PlatformerManager getManager() {
        return this.manager;
    }

    Rect getShape() {
        return this.shape;
    }

    public void draw(Canvas canvas) {
        canvas.drawOval(getX() - radius / 2, getY() - radius / 2, getX() + radius / 2,
                getY() + radius / 2, getPaint());
    }

    /** Moves the PlatformerCoin down when the Character jumps up. */
    private void coinDown(int down) {
        if (getY() + down > getManager().getGridHeight()) {
            /* Moves coin up if the Character moves down without collection the PlatformerCoin. */
            int diff = Math.abs(getY() + down - getManager().getGridHeight());
            if (diff > 400) {
                setY(0);
            }
            else if (diff > 200) {
                setY(-200);
            }
            else {
                setY(-diff);
            }
            Random r = new Random();
            int a = r.nextInt(getManager().getGridWidth() - 150);
            this.setX(a);
        }
        else {
            incY(down);
        }
    }

}
