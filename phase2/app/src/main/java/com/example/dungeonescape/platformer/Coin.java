package com.example.dungeonescape.platformer;

import android.graphics.Color;
import java.util.Random;


class Coin extends PlatformerObject {

    Coin(int x, int y, int size, PlatformerManager manager) {
        super(x,y,size,manager);
        setShape();
        getPaint().setColor(Color.YELLOW);
    }


    /** Deletes a Coin that the Character has already collected.
     * Creates a new Coin at a new y value.
     */
    void gotCoin() {
        setY(0);
        Random r = new Random();
        setX(r.nextInt(1080- 150));
        setShape();
    }

    /** Moves the Platforms down. */
    void update(int down) {
        coinDown(down);
        setShape();
    }

    /** Moves the Coin down when the Character jumps up. */
    private void coinDown(int down) {
        if (getY() + down > getManager().getGridHeight()) {
            /* Moves coin up if the Character moves down without collection the Coin. */
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
