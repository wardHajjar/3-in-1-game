package com.example.dungeonescape.maze;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.dungeonescape.activities.MainActivity;
import com.example.dungeonescape.player.Player;
import com.example.dungeonescape.R;
import com.example.dungeonescape.activities.GeneralGameActivity;
import com.example.dungeonescape.platformer.PlatformerInstructionsActivity;
import com.example.dungeonescape.player.PlayerManager;

import java.util.Locale;


public class MazeActivity extends GeneralGameActivity {
    private MazeManager mazeManager;

    /** Initial time set in milliseconds. */
    public long counter = 120000; // 2 min
    // public long counter = 6000; // 5s (for testing)

    /** Minutes and seconds values. */
    private long minutes;
    private long seconds;

    /** Initializes a Player and PlayerManager. */
    private Player player;

    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

        getPlayerSaveData(getIntent());
        initializeMazeManager();
        startCountDown();

        // starts the Time Elapsed clock
        startTime = SystemClock.elapsedRealtime();

        // go to Next Mini-game; testing only
        configureNextButton();
    }

    /** Assigns variables player and playerManager to data from the specified Intent.
     *
     * @param intent the Intent to gather data from.
     */
    private void getPlayerSaveData(Intent intent) {
        load();
        String name = (String) intent.getSerializableExtra("Player Name");
        player = getPlayerManager().getPlayer(name);
    }

    /** Initializes the MazeManager for this Maze game. */
    private void initializeMazeManager() {
        MazeView mazeView = findViewById(R.id.view);
        mazeManager = new MazeManager(mazeView, player);
        mazeManager.relocatePlayerSprite();
    }

    /** Creates and runs the countdown to losing the game. */
    private void startCountDown() {
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
                // Player loses a life when the countdown runs out
                player.loseLife();
                int playerLivesLeft = player.getNumLives();

                // Change UI to Lose Life screen
                setContentView(R.layout.activity_maze_lose_life);
                TextView textView = findViewById(R.id.playerLives);
                textView.setText(String.format(Locale.getDefault(),
                        "You have %d lives left.", playerLivesLeft));
                configureStartOverButton();
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_menu) {
            save(getPlayerManager());
            Intent intent = new Intent(MazeActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
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
                save(getPlayerManager());
                Intent intent = new Intent(MazeActivity.this,
                        PlatformerInstructionsActivity.class);
                intent.putExtra("Player Name", player.getName());
                startActivity(intent);
            }
        });
    }

    /** Creates the Button to play the Maze Game from the beginning. */
    private void configureStartOverButton() {
        Button startOver = findViewById(R.id.startOver);
        startOver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MazeActivity.this, MazeActivity.class);
                intent.putExtra("Player Name", player.getName());
                startActivity(intent);
            }
        });
    }

    /** Moves the Player up by one MazeCell. */
    public void movePlayerUp(View view) {
        mazeManager.movePlayerSprite("UP");
        goToNextLevel(view);
    }

    /** Moves the Player down by one MazeCell. */
    public void movePlayerDown(View view) {
        mazeManager.movePlayerSprite("DOWN");
        goToNextLevel(view);
    }

    /** Moves the Player left by one MazeCell. */
    public void movePlayerLeft(View view) {
        mazeManager.movePlayerSprite("LEFT");
        goToNextLevel(view);
    }

    /** Moves the Player right by one MazeCell. */
    public void movePlayerRight(View view) {
        mazeManager.movePlayerSprite("RIGHT");
        goToNextLevel(view);
    }

    /** Checks if the Maze has been completed 3 times. Go to next level if true. */
    public void goToNextLevel(View view) {
        if (mazeManager.hasCompletedLevel())
            nextLevel();
    }

    /** Send the Player to the next level of the game. */
    protected void nextLevel() {
        // The time at which the user has finished the level
        long elapsedMilliSeconds = SystemClock.elapsedRealtime() - startTime;
        player.updateTotalTime(elapsedMilliSeconds);

        save(getPlayerManager());

        Intent intent = new Intent(MazeActivity.this, PlatformerInstructionsActivity.class);
        intent.putExtra("Player Name", player.getName());
        startActivity(intent);
    }

    @Override
    public void save(PlayerManager playerManager) {
        player.setCurrentLevel(3);
        super.save(playerManager);
    }
}
