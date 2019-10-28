package com.example.dungeonescape.platformer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;

import android.util.AttributeSet;
import android.view.View;

public class Level2View extends View {
    private Character character;

    public Level2View(Context context, AttributeSet attrs) {
        super(context, attrs);
        character = new Character(50,50,100);
    }
    public Level2View(Context context) {
        super(context);
        character = new Character(50,50,100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        character.move(canvas);

        canvas.drawOval(character.oval,character.paint);

        invalidate(); // See note
    }
}