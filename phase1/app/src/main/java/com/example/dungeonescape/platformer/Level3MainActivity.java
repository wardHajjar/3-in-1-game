package com.example.dungeonescape.platformer;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dungeonescape.Dead;
import com.example.dungeonescape.GameManager;
import com.example.dungeonescape.GeneralGameActivity;
import com.example.dungeonescape.Player;
import com.example.dungeonescape.PlayerStats;
import com.example.dungeonescape.R;

public class Level3MainActivity extends GeneralGameActivity {
    private Level3View game;
    private boolean running;
    Player player;
    GameManager gameManager;
    long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Gather saved data. */
        startTime = SystemClock.elapsedRealtime();
        Intent i = getIntent();
        player = (Player) i.getSerializableExtra("Player");
        gameManager = (GameManager) i.getSerializableExtra("Game Manager");

        /* Set content view. */
        setContentView(R.layout.activity_level3_main);
        game = findViewById(R.id.level2);

        /* Set the player in platform manager */
        game.getManager().setPlayer(player);

        setTitle("Level3: Platformer");

        /* Set Buttons */
        buttons();
        running = true;

        // Thread code is from the following Youtube Video, body of run() is written myself
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
                                    int score = game.getManager().getCharacterScore();
                                    String scr = String.valueOf(score) ;
                                    String scre = "Score: " + scr;
                                    TextView score1 = (TextView) findViewById(R.id.score);
                                    score1.setText(scre);

                                    // Update the lives shown
                                    int lives = game.getManager().getPlayer().getNumLives();
                                    String life = "Lives: " + String.valueOf(lives);
                                    TextView lifeText = (TextView) findViewById(R.id.lives);
                                    lifeText.setText(life);
                                    // Checks if player is done level
                                    boolean doneLevel = game.nextLevel();
                                    if (doneLevel) {
                                        nextLevel();
                                        save(gameManager, player);
                                        running = false;
                                    }
                                    // Checks if player has no lives
                                    boolean dead = game.dead();
                                    if (dead){
                                        save(gameManager, player);
                                        deadPage();
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

    /** Updates player statistics and moves game to the next level */
    private void nextLevel() {
        long endTime = SystemClock.elapsedRealtime();
        long elapsedMilliSeconds = endTime - startTime;
        player.updateTotalTime(elapsedMilliSeconds);
        save(gameManager, player);
        Intent intent = new Intent(Level3MainActivity.this, PlayerStats.class);
        intent.putExtra("Player", player);
        intent.putExtra("Game Manager", gameManager);
        startActivity(intent);
    }
    /** Updates player statistics and moves game to the dead page */
    private void deadPage() {
        long endTime = SystemClock.elapsedRealtime();
        long elapsedMilliSeconds = endTime - startTime;
        player.updateTotalTime(elapsedMilliSeconds);
        save(gameManager, player);
        Intent intent = new Intent(Level3MainActivity.this, Dead.class);
        intent.putExtra("Player", player);
        intent.putExtra("Game Manager", gameManager);
        startActivity(intent);
    }

    /**
     * Method for initializing left and right buttons.
     */
    private void buttons() {

        Button left = (Button) findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.getManager().left_button();
            }
        });

        Button right = (Button) findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.getManager().right_button();
            }
        });
    }

    /**
     * Method for saving the current game state to file.
     */
    @Override
    public void save(GameManager gameManager, Player player) {
        super.save(gameManager, player);
        player.setCurrentLevel(3);
    }


}

