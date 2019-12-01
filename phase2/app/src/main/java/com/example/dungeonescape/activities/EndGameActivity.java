package com.example.dungeonescape.activities;
import com.example.dungeonescape.player.Player;
import com.example.dungeonescape.player.PlayerManager;
import com.example.dungeonescape.R;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class EndGameActivity extends GeneralGameActivity {
    private Player player;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        //* Gather saved data. */
        load();
        Intent i = getIntent();
        String name = (String) i.getSerializableExtra("Player Name");
        player = getPlayerManager().getPlayer(name);

        configureActionButtons();

        TextView congratulatePlayer = findViewById(R.id.congratulatePlayer);
        congratulatePlayer.setText(String.format("Congratulation, %s!", player.getName()));

        populatePlayerStats();
    }

    private void configureActionButtons() {
        configurePlayAgainButton();
        configureMenuButton();
    }

    private void configurePlayAgainButton() {
        Button playAgain = findViewById(R.id.playAgain);
        playAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                player.resetStats();
                save(getPlayerManager());
                Intent intent = new Intent(EndGameActivity.this, HomeScreen.class);
                intent.putExtra("Player Name", player.getName());
                startActivity(intent);
            }
        });
    }

    private void configureMenuButton() {
        Button menu = findViewById(R.id.mainMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                player.resetStats();
                save(getPlayerManager());
                Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
                intent.putExtra("Player Name", player.getName());
                startActivity(intent);
            }
        });
    }

    private void populatePlayerStats() {
        List<Integer> score = new ArrayList<>();
        TextView playerTimeElapsed = findViewById(R.id.playerTimeElapsed);
        String minutes = String.valueOf(player.totalMinutes());
        String seconds = String.valueOf(player.totalSeconds());
        String time = minutes +":" + seconds;
        playerTimeElapsed.setText(time);

        TextView playerNumCoins = findViewById(R.id.playerNumCoins);
        playerNumCoins.setText(String.valueOf(player.getNumCoins()));

        TextView playerNumLives = findViewById(R.id.playerNumLives);
        playerNumLives.setText(String.valueOf(player.getNumLives()));
        score.add(player.getNumLives());
        score.add(player.getNumCoins());
        score.add((int)player.getTotalTime());
        player.setHighScore(score);
        save(getPlayerManager());
    }

    @Override
    public void save(PlayerManager playerManager) {
        super.save(getPlayerManager());
    }
}