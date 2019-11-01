package com.example.dungeonescape.Maze;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dungeonescape.GameManager;
import com.example.dungeonescape.Player;
import com.example.dungeonescape.R;

public class MazeActivityInstructions extends AppCompatActivity {

    Player player;
    GameManager gameManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze_instructions);
        setTitle("Level2: Maze");
        // getting player instance from intent
        Intent i = getIntent();
        player = (Player) i.getSerializableExtra("Player");
        gameManager = (GameManager) i.getSerializableExtra("Game Manager");
        configureNextButton();
    }

    private void configureNextButton() {
        Button nextButton = findViewById(R.id.start);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.dungeonescape.Maze.MazeActivityInstructions.this, MazeActivity.class);
                intent.putExtra("Player", player);
                intent.putExtra("Game Manager", gameManager);
                startActivity(intent);
            }
        });


    }
}
