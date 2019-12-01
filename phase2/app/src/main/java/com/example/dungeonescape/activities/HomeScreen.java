package com.example.dungeonescape.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dungeonescape.R;
import com.example.dungeonescape.brickbreaker.BBInstructionsActivity;
import com.example.dungeonescape.player.Player;
import com.example.dungeonescape.player.PlayerManager;

import java.util.Map;

public class HomeScreen extends AppCompatActivity {
    private Player player;
    private PlayerManager playerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        /* Gather saved data. */
        Intent i = getIntent();
        player = (Player) i.getSerializableExtra("Player");
        playerManager = (PlayerManager) i.getSerializableExtra("Game Manager");

        TextView congratulatePlayer = findViewById(R.id.welcome);
        congratulatePlayer.setText(String.format("Welcome, %s!", player.getName()));

        Map<String, Integer> highScore = player.getHighScore();

        TextView playerLives = findViewById(R.id.numLives);
        playerLives.setText(String.format("%s", highScore.get("Lives")));

        TextView coins = findViewById(R.id.numCoins);
        coins.setText(String.format("%s", highScore.get("Lives")));

        TextView time = findViewById(R.id.numTime);
        time.setText(String.format("%s", highScore.get("Time")));
        enterButton();
    }

    private void enterButton() {
        Button enterGame = findViewById(R.id.playButton);

        enterGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, BBInstructionsActivity.class);
                intent.putExtra("Player", player);
                intent.putExtra("Game Manager", playerManager);
                startActivity(intent);
            }
        });
    }
}
