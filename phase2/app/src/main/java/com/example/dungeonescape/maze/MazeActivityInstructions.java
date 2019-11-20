package com.example.dungeonescape.maze;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.dungeonescape.PlayerManager;
import com.example.dungeonescape.Player;
import com.example.dungeonescape.R;

public class MazeActivityInstructions extends AppCompatActivity {

    /** Initializes a Player and PlayerManager. */
    Player player;
    PlayerManager playerManager;

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

    /** Creates the Start button to play the Maze. */
    private void configureNextButton() {
        Button nextButton = findViewById(R.id.start);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        com.example.dungeonescape.maze.MazeActivityInstructions.this,
                        MazeActivity.class);
                intent.putExtra("Player", player);
                intent.putExtra("Game Manager", playerManager);
                startActivity(intent);
            }
        });
    }
}
