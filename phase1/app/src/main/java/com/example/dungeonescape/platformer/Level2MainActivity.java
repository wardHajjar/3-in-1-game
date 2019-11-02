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

public class Level2MainActivity extends GeneralGameActivity {
    /**
     * The game's view that updates and draws the objects within it.
     */
    private Level2View game;
    private boolean running;
    Player player;
    GameManager gameManager;

    /**
     * The time at which the brick breaker game has been started.
     */
    long startTime;

    /**
     *
     * @param savedInstanceState Bundle object that passes data between activities.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startTime = SystemClock.elapsedRealtime();
        // Set the View we are using
        Intent i = getIntent();
        player = (Player) i.getSerializableExtra("Player");
        gameManager = (GameManager) i.getSerializableExtra("Game Manager");

        setContentView(R.layout.activity_level2_main);
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
                                        save(gameManager, player);
                                        running = false;
                                    }
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

    /**
     * User has successfully finished Brick Breaker and will now move on to Maze.
     */
    private void nextLevel() {
        long endTime = SystemClock.elapsedRealtime();
        long elapsedMilliSeconds = endTime - startTime;
        player.updateTotalTime(elapsedMilliSeconds);
        save(gameManager, player);
        Intent intent = new Intent(Level2MainActivity.this, PlayerStats.class);
        intent.putExtra("Player", player);
        intent.putExtra("Game Manager", gameManager);
        startActivity(intent);
    }

    /**
     * User has lost the Game i.e. no more lives left.
     */
    private void deadPage() {
        long endTime = SystemClock.elapsedRealtime();
        long elapsedMilliSeconds = endTime - startTime;
        player.updateTotalTime(elapsedMilliSeconds);
        save(gameManager, player);
        Intent intent = new Intent(Level2MainActivity.this, Dead.class);
        intent.putExtra("Player", player);
        intent.putExtra("Game Manager", gameManager);
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
    public void save(GameManager gameManager, Player player) {
        super.save(gameManager, player);
        player.setCurrentLevel(3);
    }


}

