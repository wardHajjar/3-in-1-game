package com.example.dungeonescape.Maze;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dungeonescape.MainActivity;
import com.example.dungeonescape.R;
import com.example.dungeonescape.platformer.PlatformerMainActivity;

public class MazeActivity extends MainActivity {

    public int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

        // go to next game
        configureNextButton();

        // countdown to losing the game
        final TextView countTime = findViewById(R.id.countTime);

        // countdown code from: https://www.tutorialspoint.com/how-to-make-a-countdown-timer-in-android
        // partially edited
        new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                countTime.setTextColor(Color.WHITE);
                countTime.setText(String.valueOf(counter));
                counter++;
                System.out.println(counter);
            }

            @Override
            public void onFinish() {
                countTime.setTextColor(Color.WHITE);
                countTime.setText("Finished");
                System.out.println("finished");
            }
        }.start();
    }

    private void configureNextButton() {
        Button nextButton = (Button) findViewById(R.id.nextlvl);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MazeActivity.this, PlatformerMainActivity.class);
                startActivity(intent);
            }
        });


    }
}
