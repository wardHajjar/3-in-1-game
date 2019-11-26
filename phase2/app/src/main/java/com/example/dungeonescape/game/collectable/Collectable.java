package com.example.dungeonescape.game.collectable;

import android.graphics.Canvas;

public interface Collectable {

    void draw(Canvas canvas);

    Boolean getAvailableStatus();

    void gotCollected();
}
