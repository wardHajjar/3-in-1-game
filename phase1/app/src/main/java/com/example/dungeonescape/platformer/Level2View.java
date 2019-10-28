package com.example.dungeonescape.platformer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class Level2View extends View {
    private List<Character> balls = new ArrayList<>();

    public Level2View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public Level2View(Context context) {
        super(context);
        init();
    }
    private void init(){
        //Add a new ball to the view
        balls.add(new Character(50,50,100));
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Draw the balls
        for(Character ball : balls){
            //Move first
            ball.move(canvas);
            //Draw them
            canvas.drawOval(ball.oval,ball.paint);
        }
        invalidate(); // See note
    }
}