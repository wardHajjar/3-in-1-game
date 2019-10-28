package com.example.dungeonescape.platformer;
import android.content.Context;
import android.graphics.Canvas;

import android.util.AttributeSet;
import android.view.View;

public class Level2View extends View {
    private Character character;
    PlatformerManager manager;

    public Level2View(Context context, AttributeSet attrs) {
        super(context, attrs);

        manager = new PlatformerManager();
    }
    public Level2View(Context context) {
        super(context);

        manager = new PlatformerManager();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        manager.draw(canvas);
        invalidate();
    }
    public void update(Canvas canvas) {
        manager.update(canvas);
    }

}