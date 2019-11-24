package com.example.dungeonescape.maze;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dungeonescape.player.PlayerManager;
import com.example.dungeonescape.player.Player;
import com.example.dungeonescape.R;
import com.example.dungeonescape.activities.GeneralGameActivity;
import com.example.dungeonescape.platformer.PlatformerInstructions;

import java.util.Locale;


public class MazeActivity extends GeneralGameActivity {
    private MazeView mazeView;

    /** Initial time set in milliseconds. */
    public long counter = 120000; // 2 min
    // public long counter = 6000; // 5s (for testing)

    /** Minutes and seconds values. */
    long minutes;
    long seconds;

    /** Initializes a Player and PlayerManager. */
    Player player;
    PlayerManager playerManager;
    PlayerSprite playerSprite = new PlayerSprite();

    long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

        /* Gather saved data. */
        Intent i = getIntent();
        player = (Player) i.getSerializableExtra("Player");
        playerManager = (PlayerManager) i.getSerializableExtra("Game Manager");
        playerSprite.setPaintColour(player.getColour());
        mazeView = findViewById(R.id.view);
        mazeView.setPlayer(player);
        mazeView.setPlayerSprite(playerSprite);

        /* Sets the Player colour to the input choice from the New Game page. */
        MazeManager mazeManager = mazeView.getMazeManager();
//        mazeManager.setPlayerSpritePaint(player.getColour());
//        playerSprite.setPaintColour(player.getColour());

        /* Go to next game. Testing only. */
        configureNextButton();

        // starts the clock
        startTime = SystemClock.elapsedRealtime();

        /* Initializes the countdown to losing the game. */
        final TextView countTime = findViewById(R.id.countTime);
        countTime.setTextColor(Color.WHITE);

        /* Countdown code from: https://www.tutorialspoint.com/how-to-make-a-countdown-timer-in-android
          Partially edited.
         */
        new CountDownTimer(counter, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                counter = millisUntilFinished;
                updateCountDown();
                countTime.setText(String.format(Locale.getDefault(),
                        "%02d:%02d", minutes, seconds));
                counter--;
            }

            @Override
            public void onFinish() {
                /* Player loses a life when the countdown runs out. */
                player.loseLife();
                int playerLivesLeft = player.getNumLives();

                /* Change UI to Lose Life screen. */
                setContentView(R.layout.activity_maze_lose_life);
                TextView textView = (TextView) findViewById(R.id.playerLives);
                textView.setText(String.format(Locale.getDefault(),
                        "You have %d lives left.", playerLivesLeft));
                configureStartOverButton();
            }
        }.start();
    }

    /**
     * Converts milliseconds to minutes and seconds.
     * Calculation from:
     * https://codinginflow.com/tutorials/android/countdowntimer/part-2-configuration-changes
     */
    private void updateCountDown() {
        minutes = (counter / 1000) / 60;
        seconds = (counter / 1000) % 60;
    }

    private void configureNextButton() {
        Button nextButton = findViewById(R.id.nextlvl);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                save(playerManager, player);
                Intent intent = new Intent(MazeActivity.this,
                        PlatformerInstructions.class);
                intent.putExtra("Player", player);
                intent.putExtra("Game Manager", playerManager);
                startActivity(intent);
            }
        });
    }

    private void configureStartOverButton() {
        Button startOver = findViewById(R.id.startOver);
        startOver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MazeActivity.this, MazeActivity.class);
                intent.putExtra("Player", player);
                intent.putExtra("Game Manager", playerManager);
                startActivity(intent);
            }
        });
    }

    /** Moves the Player up by one MazeCell. */
    public void movePlayerUp(View view) {
        mazeView.movePlayer("UP");
        checkDoneLevel(view);
    }

    /** Moves the Player down by one MazeCell. */
    public void movePlayerDown(View view) {
        mazeView.movePlayer("DOWN");
        checkDoneLevel(view);
    }

    /** Moves the Player left by one MazeCell. */
    public void movePlayerLeft(View view) {
        mazeView.movePlayer("LEFT");
        checkDoneLevel(view);
    }

    /** Moves the Player right by one MazeCell. */
    public void movePlayerRight(View view) {
        mazeView.movePlayer("RIGHT");
        checkDoneLevel(view);
    }

    /** Checks if the Maze has been completed 3 times. Go to next level if true. */
    public void checkDoneLevel(View view) {
        if (mazeView.doneLevel())
            nextLevel();
    }

    /** User has successfully finished Maze and will now move on to Platformer. */
    protected void nextLevel() {
        /* The time at which the user has finished the level. */
        long endTime = SystemClock.elapsedRealtime();
        long elapsedMilliSeconds = endTime - startTime;

        /* Updates the total time elapsed in Player. */
        player.updateTotalTime(elapsedMilliSeconds);
        save(playerManager, player);
        Intent intent = new Intent(MazeActivity.this, PlatformerInstructions.class);
        intent.putExtra("Player", player);
        intent.putExtra("Game Manager", playerManager);
        startActivity(intent);
    }

    @Override
    public void save(PlayerManager playerManager, Player player) {
        super.save(playerManager, player);
        player.setCurrentLevel(3);
    }
}
