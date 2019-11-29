package com.example.dungeonescape.activities;
import com.example.dungeonescape.player.Player;
import com.example.dungeonescape.player.PlayerManager;
import com.example.dungeonescape.R;
import com.example.dungeonescape.brickbreaker.BBMainActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class EndGameActivity extends GeneralGameActivity {
    private PlayerManager playerManager;
    private Player player;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Intent i = getIntent();
        player = (Player) i.getSerializableExtra("Player");
        playerManager = (PlayerManager) i.getSerializableExtra("Game Manager");

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
                save(playerManager, player);
                Intent intent = new Intent(EndGameActivity.this, BBMainActivity.class);
                intent.putExtra("Player", player);
                intent.putExtra("Game Manager", playerManager);
                startActivity(intent);
            }
        });
    }

    private void configureMenuButton() {
        Button menu = findViewById(R.id.mainMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                player.resetStats();
                save(playerManager, player);
                Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
                intent.putExtra("Game Manager", playerManager);
                startActivity(intent);
            }
        });
    }

    private void populatePlayerStats() {
        TextView playerTimeElapsed = findViewById(R.id.playerTimeElapsed);
        playerTimeElapsed.setText(String.valueOf(player.getTotalTime()));

        TextView playerNumCoins = findViewById(R.id.playerNumCoins);
        playerNumCoins.setText(String.valueOf(player.getNumCoins()));

        TextView playerNumLives = findViewById(R.id.playerNumLives);
        playerNumLives.setText(String.valueOf(player.getNumLives()));
    }

    @Override
    public void save(PlayerManager playerManager, Player player) {
        super.save(playerManager, player);
    }
}