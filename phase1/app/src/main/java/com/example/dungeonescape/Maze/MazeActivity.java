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

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MazeActivity extends MainActivity {

    // initial time set in milliseconds
    public long counter = 120000;
    long minutes;
    long seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

        // go to next game
        configureNextButton();

        // countdown to losing the game
        final TextView countTime=findViewById(R.id.countTime);

        // countdown code from: https://www.tutorialspoint.com/how-to-make-a-countdown-timer-in-android
        // partially edited
        new CountDownTimer(counter, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                countTime.setTextColor(Color.WHITE);
                counter = millisUntilFinished;
                updateCountDown();
                countTime.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
                counter--;
            }

            @Override
            public void onFinish() {
                countTime.setTextColor(Color.WHITE);
                countTime.setText("Game Over");
            }
        }.start();
    }

    private void updateCountDown() {
        /* minutes & seconds calculation from here:
        https://codinginflow.com/tutorials/android/countdowntimer/part-2-configuration-changes
         */
        minutes = (counter / 1000) / 60;
        seconds = (counter / 1000) % 60;
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
