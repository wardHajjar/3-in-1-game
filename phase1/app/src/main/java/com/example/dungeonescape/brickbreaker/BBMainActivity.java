package com.example.dungeonescape.brickbreaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dungeonescape.R;
import com.example.dungeonescape.Maze.MazeActivity;

/**
 * The main activity of the game (entry point).
 */
public class BBMainActivity extends Activity {
    /**
     * The game's view that updates and draws the objects within it.
     */
    BBView gameView;
    boolean running;
    /**
     *
     * @param savedInstanceState Bundle object that passes data between activities.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the View we are using
        setContentView(R.layout.activity_brick_breaker_main);
        gameView = findViewById(R.id.BBView2);
        setTitle("Level1: Brick Breaker");

        Button nextButton = (Button) findViewById(R.id.nextlvl);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BBMainActivity.this, MazeActivity.class);
                startActivity(intent);
            }
        });

        running = true;

        // Thread code is from the following Youtube Video, however, body of run() is original.
        // https://www.youtube.com/watch?v=6sBqeoioCHE&t=193s
        Thread t = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (running) {
                                    // Update the score shown
                                    int score = gameView.manager.getCharacterCoins();
                                    String scr = String.valueOf(score) ;
                                    String scre = "Score: " + scr;

                                    TextView score1 = findViewById(R.id.score);
                                    score1.setText(scre);
                                    int lives = gameView.manager.getCharacterLives();
                                    String life = "Lives: " + lives;
                                    TextView lifeText = findViewById(R.id.lives);
                                    lifeText.setText(life);
//                                    boolean doneLevel = gameView.nextLevel();
//                                    if (doneLevel) {
//                                        nextLevel();
//                                        running = false;
//                                    }
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }


    /**
     * Method executes when the player starts the game.
     */
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    /**
     * Method executes when the player quits the game.
     */
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }
}
