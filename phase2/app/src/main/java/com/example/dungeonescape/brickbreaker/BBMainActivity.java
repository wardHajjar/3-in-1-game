package com.example.dungeonescape.brickbreaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dungeonescape.PlayerManager;
import com.example.dungeonescape.GeneralGameActivity;
import com.example.dungeonescape.maze.MazeActivityInstructions;
import com.example.dungeonescape.Player;
import com.example.dungeonescape.R;
import com.example.dungeonescape.DeadActivity;

import android.os.SystemClock;

/**
 * The main activity of the game (entry point).
 */
public class BBMainActivity extends GeneralGameActivity {
    /**
     * The game's view that updates and draws the objects within it.
     */
    BBView gameView;
    boolean running;
    Player player;
    PlayerManager playerManager;
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
        // starts the clock
        startTime = SystemClock.elapsedRealtime();
        // Set the View we are using
        setContentView(R.layout.activity_brick_breaker_main);
        gameView = findViewById(R.id.BBView2);

        setTitle("Level1: Brick Breaker");
        Intent i = getIntent();
        player = (Player) i.getSerializableExtra("Player");
        playerManager = (PlayerManager) i.getSerializableExtra("Game Manager");
        gameView.manager.addPlayer(player);

        Button nextButton = findViewById(R.id.nextlvl);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save(playerManager, player);
                //Intent intent = new Intent(BBMainActivity.this, MazeActivity.class);
                Intent intent = new Intent(BBMainActivity.this, MazeActivityInstructions.class);

                intent.putExtra("Player", player);
                intent.putExtra("Game Manager", playerManager);
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
                                    if (player.getNumLives() == 0){
                                        endGame();
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
        player.setCurrentLevel(2);
        // time at which the user has finished the level.
        long endTime = SystemClock.elapsedRealtime();
        long elapsedMilliSeconds = endTime - startTime;
        player.updateTotalTime(elapsedMilliSeconds);
        save(playerManager, player);
        //Intent intent = new Intent(BBMainActivity.this, MazeActivity.class);
        Intent intent = new Intent(BBMainActivity.this, MazeActivityInstructions.class);
        intent.putExtra("Player", player);
        intent.putExtra("Game Manager", playerManager);
        startActivity(intent);
    }

    /**
     * User has lost the Game i.e. no more lives left.
     */
    protected void endGame(){
        // time at which the user has lost.
        long endTime = SystemClock.elapsedRealtime();
        long elapsedMilliSeconds = endTime - startTime;
        player.updateTotalTime(elapsedMilliSeconds);
        Intent intent = new Intent(BBMainActivity.this, DeadActivity.class);
        save(playerManager, player);
        intent.putExtra("Player", player);
        intent.putExtra("Game Manager", playerManager);
        startActivity(intent);
    }

    @Override
    public void save(PlayerManager playerManager, Player player) {
        super.save(playerManager, player);
        player.setCurrentLevel(2);
    }
}
