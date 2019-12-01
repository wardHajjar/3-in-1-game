package com.example.dungeonescape.game.collectable;

import android.graphics.Rect;

import com.example.dungeonescape.player.Player;

public interface Collectable {

    Boolean getAvailableStatus();

    void gotCollected();

    Rect getItemShape();

    void collect(Player player);
}
