package com.example.dungeonescape.game.collectable;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface Collectable {

    Boolean getAvailableStatus();

    void gotCollected();

    Rect getItemShape();
}
