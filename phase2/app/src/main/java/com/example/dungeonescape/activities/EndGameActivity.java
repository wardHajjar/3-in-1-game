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
    PlayerManager playerManager;
    Player player;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Intent i = getIntent();
        player = (Player) i.getSerializableExtra("Player");
        playerManager = (PlayerManager) i.getSerializableExtra("Game Manager");

        buttons();

        TextView congratPlayer = findViewById(R.id.congrats);
        congratPlayer.setText(String.format("Congratulation, %s!", player.getName()));

        TextView numTimer = findViewById(R.id.numTimerText);
        numTimer.setText(String.valueOf(player.getTotalTime()));

        TextView numCoins = findViewById(R.id.numCoinsText);
        numCoins.setText(String.valueOf(player.getNumCoins()));

        TextView numLives = findViewById(R.id.numLivesText);
        numLives.setText(String.valueOf(player.getNumLives()));
    }


    private void buttons() {
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

        Button menu = findViewById(R.id.menu);
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

    @Override
    public void save(PlayerManager playerManager, Player player) {
        super.save(playerManager, player);
    }
}