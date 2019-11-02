package com.example.dungeonescape.brickbreaker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dungeonescape.GameManager;
import com.example.dungeonescape.GeneralGameActivity;
import com.example.dungeonescape.Maze.MazeActivity;
import com.example.dungeonescape.Maze.MazeActivityInstructions;
import com.example.dungeonescape.Player;
import com.example.dungeonescape.R;
import com.example.dungeonescape.Dead;
import android.os.SystemClock;

/**
 * The main activity of the game (entry point).
 */
public class BBMainActivity extends GeneralGameActivity {
    /** The game's view that updates and draws the objects within it.*/
    BBView gameView;
    /** Whether the game is still being played. */
    boolean running;
    /** The user of the game. */
    Player player;
    /** The game manager that controlls the objects within the game. */
    GameManager gameManager;
    /** The time at which the brick breaker game has been started. */
    long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTime = SystemClock.elapsedRealtime();  // starts the clock
        setContentView(R.layout.activity_brick_breaker_main); // Sets the View the game is using
        gameView = findViewById(R.id.BBView2);
        setTitle("Level1: Brick Breaker");
        Intent i = getIntent();
        player = (Player) i.getSerializableExtra("Player");
        gameManager = (GameManager) i.getSerializableExtra("Game Manager");
        gameView.manager.addPlayer(player);

        /* Next level button was just intended for testing, kept for TA testing. Button is
         * equivalent to winning the level.
         */
//        Button nextButton = findViewById(R.id.nextlvl);
//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                save(gameManager, player);
//                Intent intent = new Intent(BBMainActivity.this,
//                        MazeActivityInstructions.class);
//                intent.putExtra("Player", player);
//                intent.putExtra("Game Manager", gameManager);
//                startActivity(intent);
//            }
//        });

        running = true; // game has started.

        /*
         * Thread code is from the following Youtube Video, however, body of run() is original.
         * https://www.youtube.com/watch?v=6sBqeoioCHE&t=193s
         */
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
                                    int score = player.getNumCoins();  // The player's score.
                                    String scr = String.valueOf(score) ;
                                    String scre = "Score: " + scr;

                                    TextView score1 = findViewById(R.id.score);

                                    score1.setText(scre);
                                    int lives = player.getNumLives(); // The player's lives.
                                    String life = "Lives: " + lives;
                                    TextView lifeText = findViewById(R.id.lives);
                                    lifeText.setText(life);
                                    boolean doneLevel = gameView.doneLevel();
                                    if (doneLevel) { // If level has been won
                                        nextLevel();
                                        running = false;
                                    }
                                    if (player.getNumLives() == 0){ // If level has been lost.
                                        endGame();
                                        running = false;    // Stops the game.
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

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    /** User has successfully finished Brick Breaker and will now move on to Maze. */
    protected void nextLevel() {
        player.setCurrentLevel(2);
        /* time at which the user has finished the level. */
        long endTime = SystemClock.elapsedRealtime();
        long elapsedMilliSeconds = endTime - startTime;
        player.updateTotalTime(elapsedMilliSeconds);
        save(gameManager, player);

        /* Advances to next level (maze), displays instructions first. */
        Intent intent = new Intent(BBMainActivity.this,
                MazeActivity.class);
        intent.putExtra("Player", player);
        intent.putExtra("Game Manager", gameManager);
        startActivity(intent);
    }

    /** User has lost the Game i.e. no more lives left. */
    protected void endGame(){
        /* time at which the user has lost. */
        long endTime = SystemClock.elapsedRealtime();
        long elapsedMilliSeconds = endTime - startTime;
        player.updateTotalTime(elapsedMilliSeconds);

        /* Advances player to the screen that shows they've lost. */
        Intent intent = new Intent(BBMainActivity.this, Dead.class);
        save(gameManager, player);
        intent.putExtra("Player", player);
        intent.putExtra("Game Manager", gameManager);
        startActivity(intent);
    }

    @Override
    public void save(GameManager gameManager, Player player) {
        super.save(gameManager, player);
        player.setCurrentLevel(2);
    }
}
