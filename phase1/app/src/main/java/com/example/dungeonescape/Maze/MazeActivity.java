package com.example.dungeonescape.Maze;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dungeonescape.GameView;
import com.example.dungeonescape.MainActivity;
import com.example.dungeonescape.Player;
import com.example.dungeonescape.R;
import com.example.dungeonescape.platformer.PlatformerMainActivity;

import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class MazeActivity extends MainActivity {
    private MazeView mazeView;

    // initial time set in milliseconds
//    public long counter = 120000; // 2 min
     public long counter = 6000; // 5s (for testing)
    long minutes;
    long seconds;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);
        Intent i = getIntent();
        player = (Player) i.getSerializableExtra("Player");
        mazeView = findViewById(R.id.view);
        mazeView.setPlayer(player);
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
                setContentView(R.layout.activity_maze_game_over);

                player.loseLife();
                int playerLivesLeft = player.getNumLives();

                TextView textView = (TextView) findViewById(R.id.playerLives);
                textView.setText(String.format(Locale.getDefault(),
                        "You have %d lives left.", playerLivesLeft));

                configureStartOverButton();
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
                intent.putExtra("Player", player);
                startActivity(intent);
            }
        });
    }

    private void configureStartOverButton() {
        Button startOver = (Button) findViewById(R.id.startOver);
        startOver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MazeActivity.this, MazeActivity.class);
                intent.putExtra("Player", player);
                startActivity(intent);
            }
        });
    }

    public void movePlayerUp(View view){
//        MazeView mazeView = findViewById(R.id.view);
        mazeView.movePlayer("UP");
    }
    public void movePlayerDown(View view){
//        MazeView mazeView = findViewById(R.id.view);
        mazeView.movePlayer("DOWN");
    }
    public void movePlayerLeft(View view){
//        MazeView mazeView = findViewById(R.id.view);
        mazeView.movePlayer("LEFT");
    }
    public void movePlayerRight(View view){
//        MazeView mazeView = findViewById(R.id.view);
        mazeView.movePlayer("RIGHT");
    }
}
