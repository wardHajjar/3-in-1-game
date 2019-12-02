package com.example.dungeonescape.game.collectable;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.io.Serializable;

public interface Collectable{

    Boolean getAvailableStatus();

    void gotCollected();

    Rect getItemShape();

    void update(int down, int height);

    void gotCollectable();
}
