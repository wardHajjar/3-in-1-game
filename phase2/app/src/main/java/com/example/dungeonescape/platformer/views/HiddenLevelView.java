package com.example.dungeonescape.platformer.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

public class HiddenLevelView extends LevelView {

    public HiddenLevelView(Context context, AttributeSet attrs) {
        super(context, attrs, "Blitz");
    }

    public void update() {
        if (getManager().update()) {
            exitHiddenLevel();
        }
    }

    @Override
    public void draw() {
        if (holder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.rgb(139,131,120));
            getManager().draw(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void exitHiddenLevel() {
        endGameListener.onEvent();
    }

}
