package com.example.dungeonescape.brickbreaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dungeonescape.Maze.MazeActivity;
import com.example.dungeonescape.Player;
import com.example.dungeonescape.R;

/**
 * The main activity of the game (entry point).
 */
public class BBMainActivity extends Activity {
    /**
     * The game's view that updates and draws the objects within it.
     */
    BBView gameView;
    boolean running;
    Player player;
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
        Intent i = getIntent();
        player = (Player) i.getSerializableExtra("Player");
        gameView.manager.addPlayer(player);
        Button nextButton = (Button) findViewById(R.id.nextlvl);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BBMainActivity.this, MazeActivity.class);
                intent.putExtra("Player", player);
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
                                    int score = player.getNumCoins();
                                    String scr = String.valueOf(score) ;
                                    String scre = "Score: " + scr;

                                    TextView score1 = findViewById(R.id.score);
                                    score1.setText(scre);
                                    int lives = player.getNumLives();
                                    String life = "Lives: " + lives;
                                    TextView lifeText = findViewById(R.id.lives);
                                    lifeText.setText(life);
                                    boolean doneLevel = gameView.doneLevel();
                                    if (doneLevel) {
                                        nextLevel();
                                        running = false;
                                    }
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

    /**
     * User has successfully finished Brick Breaker and will now move on to Maze.
     */
    protected void nextLevel(){
        Intent intent = new Intent(BBMainActivity.this, MazeActivity.class);
        intent.putExtra("Player", player);
        startActivity(intent);
    }
}
