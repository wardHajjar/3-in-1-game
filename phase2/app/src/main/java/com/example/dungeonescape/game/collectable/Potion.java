package com.example.dungeonescape.game.collectable;

import com.example.dungeonescape.game.GameData;
import com.example.dungeonescape.game.GameObject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Path;
import com.example.dungeonescape.game.Drawable;
import com.example.dungeonescape.game.RetrieveData;
import com.example.dungeonescape.player.Player;

/**
 * Creates a potion that the player can collect to gain an extra life.
 */
public class Potion extends GameObject implements Collectable, Drawable {
    /**
     * available - whether the potion is available for the user to collect.
     * potionShape - Rect representation of the potion.
     */
    private Boolean available;
    private Rect potionShape;

    /**
     * Creates a potion item.
     * @param x x coordinate of the top left corner of the potion.
     * @param y y coordinate of the top left corner of the potion.
     * @param size size of the potion.
     */
    public Potion(int x, int y, int size) {
        super(x, y);
        available = true;
        setPaintColour(Color.GREEN);
        potionShape = new Rect(x, y, x + size, y + size);
    }

    @Override
    public void draw(Canvas canvas) {
        int width = potionShape.width();
        int x = getX();
        int y = getY();

        Path path = new Path();

        path.moveTo(x + width/3, y + width/3);
        path.lineTo(x + width/3, y);
        path.lineTo(x + (width * 2/3), y);
        path.lineTo(x + (width * 2/3), y + width/3);
        path.lineTo(x + width, y + width/3);
        path.lineTo(x + (width * 2/3), y + width);
        path.lineTo(x + width/3, y + width);
        path.lineTo(x, y + width/3);
        path.lineTo(x + width/3, y + width/3);
        path.close();

        canvas.drawPath(path, getPaint());
    }

    @Override
    public void collect(Player player) {
        player.addToSatchel(this);
    }

    @Override
    public Boolean getAvailableStatus() {
        return this.available;
    }

    @Override
    public void gotCollected() {
        this.available = false;
    }

    @Override
    public Rect getItemShape(){
        return potionShape;
    }
}
