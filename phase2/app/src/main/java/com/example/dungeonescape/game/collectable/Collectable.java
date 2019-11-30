package com.example.dungeonescape.game.collectable;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface Collectable {

    void draw(Canvas canvas);

    Boolean getAvailableStatus();

    void gotCollected();

    Rect getItemShape();
}
