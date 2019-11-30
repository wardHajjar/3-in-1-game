package com.example.dungeonescape.brickbreaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.dungeonescape.activities.GeneralGameActivity;
import com.example.dungeonescape.activities.MainActivity;
import com.example.dungeonescape.activities.MenuActivity;
import com.example.dungeonescape.player.PlayerManager;
import com.example.dungeonescape.player.Player;
import com.example.dungeonescape.R;

public class BBInstructionsActivity extends GeneralGameActivity{
    /** Initializes a Player and PlayerManager. */
    private Player player;
    private PlayerManager playerManager;

    private MenuActivity menuActivity = new MenuActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brick_breaker_instructions);
        setTitle("Level1: Brick Breaker");

        /* Gather saved data. */
        Intent i = getIntent();
        player = (Player) i.getSerializableExtra("Player");
        playerManager = (PlayerManager) i.getSerializableExtra("Game Manager");

        /* Button to start the Maze. */
        configureNextButton();
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
            save(playerManager, player);
            Intent intent = menuActivity.createIntent(BBInstructionsActivity.this,
                    MainActivity.class, playerManager, player);
            startActivity(intent);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    /** Creates the Start button to play the Maze. */
    private void configureNextButton() {
        Button nextButton = findViewById(R.id.startGame);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        BBInstructionsActivity.this, BBMainActivity.class);
                intent.putExtra("Player", player);
                intent.putExtra("Game Manager", playerManager);
                startActivity(intent);
            }
        });
    }
}