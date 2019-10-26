package com.example.dungeonescape.platformer;
import android.graphics.Paint;

public class Character {

    /**
     * This items's first coordinate.
     */
    int x;
    /**
     * This items's second coordinate.
     */
    int y;
    float speedX, speedY; // Ball's speed per step in x and y (package access)
    float radius;         // Ball's radius (package access)
//    private Color color;  // Ball's color
//    private static final Color DEFAULT_COLOR = Color.BLUE;

    Paint paintText = new Paint();

    Character() {

        x = 0;
        y = 0;
    }
//    void drawString(Canvas canvas, String s, int x, int y) {
//        canvas.drawText(s, x * FishTankView.charWidth, y * FishTankView.charHeight, paintText);
//    }


    /**
     * Draws this fish tank item.
     *
     * @param canvas the canvas on which to draw this item.
     */
//    public void draw(Canvas canvas) {
//        drawString(canvas, appearance, x, y);
//    }


}
