package com.example.dungeonescape;
import com.example.dungeonescape.brickbreaker.BBMainActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class PlayerStats extends GeneralGameActivity {
    GameManager gameManager;
    Player player;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Intent i = getIntent();
        player = (Player) i.getSerializableExtra("Player");
        gameManager = (GameManager) i.getSerializableExtra("Game Manager");

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
                save(gameManager, player);
                Intent intent = new Intent(PlayerStats.this, BBMainActivity.class);
                intent.putExtra("Player", player);
                intent.putExtra("Game Manager", gameManager);
                startActivity(intent);
            }
        });

        Button menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                player.resetStats();
                save(gameManager, player);
                Intent intent = new Intent(PlayerStats.this, MainActivity.class);
                intent.putExtra("Game Manager", gameManager);
                startActivity(intent);
            }
        });
    }

    @Override
    public void save(GameManager gameManager, Player player) {
        super.save(gameManager, player);
    }
}