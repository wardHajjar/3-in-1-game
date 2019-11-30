package com.example.dungeonescape.platformer;

import android.content.Context;
import android.util.AttributeSet;

public class HiddenLevelView extends LevelView {

    public HiddenLevelView(Context context, AttributeSet attrs) {
        super(context, attrs, "Blitz");

    }

    public void update() {
        if (!getManager().update()) {
            exitHiddenLevel();
        }
    }

    public void exitHiddenLevel() {
        endGameListener.onEvent();
    }

}
