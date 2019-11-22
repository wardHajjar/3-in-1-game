package com.example.dungeonescape.platformer;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dungeonescape.activities.DeadActivity;
import com.example.dungeonescape.player.PlayerManager;
import com.example.dungeonescape.activities.GeneralGameActivity;
import com.example.dungeonescape.player.Player;
import com.example.dungeonescape.activities.EndGameActivity;
import com.example.dungeonescape.R;

public class Level3MainActivity extends GeneralGameActivity {
    private Level3View game;
    private boolean running;
    Player player;
    PlayerManager playerManager;
    long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startTime = SystemClock.elapsedRealtime();
        // Set the View we are using
        Intent i = getIntent();
        player = (Player) i.getSerializableExtra("Player");
        playerManager = (PlayerManager) i.getSerializableExtra("Game Manager");

        setContentView(R.layout.activity_level3_main);
        game = findViewById(R.id.level2);

        // getting player instance from intent
        //pass player into manager
        game.getManager().setPlayer(player);

        setTitle("Level3: Platformer");

        // Set Buttons
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
                                    int lives = game.getManager().getPlayer().getNumLives();
                                    String life = "Lives: " + String.valueOf(lives);
                                    TextView lifeText = (TextView) findViewById(R.id.lives);
                                    lifeText.setText(life);
                                    boolean doneLevel = game.nextLevel();
                                    if (doneLevel) {
                                        nextLevel();
                                        save(playerManager, player);
                                        running = false;
                                    }
                                    boolean dead = game.dead();
                                    if (dead){
                                        save(playerManager, player);
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
    private void nextLevel() {
        long endTime = SystemClock.elapsedRealtime();
        long elapsedMilliSeconds = endTime - startTime;
        player.updateTotalTime(elapsedMilliSeconds);
        save(playerManager, player);
        Intent intent = new Intent(Level3MainActivity.this, EndGameActivity.class);
        intent.putExtra("Player", player);
        intent.putExtra("Game Manager", playerManager);
        startActivity(intent);
    }
    private void deadPage() {
        long endTime = SystemClock.elapsedRealtime();
        long elapsedMilliSeconds = endTime - startTime;
        player.updateTotalTime(elapsedMilliSeconds);
        save(playerManager, player);
        Intent intent = new Intent(Level3MainActivity.this, DeadActivity.class);
        intent.putExtra("Player", player);
        intent.putExtra("Game Manager", playerManager);
        startActivity(intent);
    }

    /**
     * Method executes when the player starts the game.
     */
    @Override
    protected void onResume() {
        super.onResume();
        game.resume();
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

    @Override
    protected void onPause() {
        super.onPause();
        game.pause();
    }

    @Override
    public void save(PlayerManager playerManager, Player player) {
        super.save(playerManager, player);
        player.setCurrentLevel(3);
    }


}

