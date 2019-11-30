package com.example.dungeonescape.platformer;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dungeonescape.activities.MainActivity;
import com.example.dungeonescape.activities.MenuActivity;
import com.example.dungeonescape.player.PlayerManager;
import com.example.dungeonescape.activities.GeneralGameActivity;
import com.example.dungeonescape.player.Player;
import com.example.dungeonescape.activities.EndGameActivity;
import com.example.dungeonescape.R;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * The activity for the main level3 game.
 */
public class PlatformerHiddenActivity extends GeneralGameActivity {
    private LevelView game;
    private boolean running;
    Player player;
    PlayerManager playerManager;
    long startTime;
    private Intent i;

    private MenuActivity menuActivity = new MenuActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startTime = SystemClock.elapsedRealtime();
        // Set the View we are using
        i = getIntent();
        player = (Player) i.getSerializableExtra("Player");
        playerManager = (PlayerManager) i.getSerializableExtra("Game Manager");


        setContentView(R.layout.activity_platformer_hidden_main);
        game = findViewById(R.id.level);

        // getting player instance from intent
        //pass player into manager
        game.getManager().setPlayer(player);

        setTitle("Level 3: PlatformerBonusLevel");
        game.setEndGameListener(new OnCustomEventListener() {
            public void onEvent() {
                endLevel();
            }
        });
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_menu) {
            Intent intent = menuActivity.createIntent(PlatformerHiddenActivity.this,
                    MainActivity.class, playerManager, player);
            startActivity(intent);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }


    /**
     * User has died in the hidden level i.e. Fell once.
     */
    private void endLevel() {
        long endTime = SystemClock.elapsedRealtime();
        long elapsedMilliSeconds = endTime - startTime;
        player.updateTotalTime(elapsedMilliSeconds);

        Intent intent = new Intent(PlatformerHiddenActivity.this, PlatformerMainActivity.class);
        intent.putExtra("Player", player);
        intent.putExtra("Game Manager", playerManager);
        intent.putExtra("Character", i.getSerializableExtra("Character"));
        intent.putExtra("Platforms", i.getSerializableExtra("Platforms"));
        intent.putExtra("Score", i.getSerializableExtra("Score"));
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



}


