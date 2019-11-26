package com.example.dungeonescape.maze;

import android.content.ActivityNotFoundException;
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

public class MazeInstructionsActivity extends GeneralGameActivity {

    /** Initializes a Player and PlayerManager. */
    private Player player;
    private PlayerManager playerManager;

    private MenuActivity menuActivity = new MenuActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze_instructions);
        setTitle("Level2: Maze");

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
        switch (item.getItemId()) {
            case R.id.about:
                // TODO: Make a Dialog box with the premise of the game
                System.out.println("about");
            case R.id.help:
                // TODO: Make a Dialog box with
                System.out.println("help");
            case R.id.main_menu: // save game and return to main menu
                save(playerManager, player);
                Intent intent = menuActivity.createIntent(MazeInstructionsActivity.this,
                        MainActivity.class, playerManager, player);
                startActivity(intent);
            default:
                return super.onContextItemSelected(item);
        }
    }

    /** Creates the Start button to play the Maze. */
    private void configureNextButton() {
        Button nextButton = findViewById(R.id.start);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                try {
//                    Intent intent = new Intent(
//                            MazeInstructionsActivity.this, MazeActivity.class);
//                    intent.putExtra("Player", player);
//                    intent.putExtra("Game Manager", playerManager);
//                    startActivity(intent);
//                } catch ( ActivityNotFoundException e) {
//                    e.printStackTrace();
//                }
                Intent intent = new Intent(
                        MazeInstructionsActivity.this, MazeActivity.class);
                intent.putExtra("Player", player);
                intent.putExtra("Game Manager", playerManager);
                startActivity(intent);
            }
        });
    }
}
