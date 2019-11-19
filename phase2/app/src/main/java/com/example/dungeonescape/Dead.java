package com.example.dungeonescape;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dungeonescape.brickbreaker.BBMainActivity;

import java.io.File;

public class Dead extends GeneralGameActivity {
    public PlayerManager playerManager;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dead);
        Intent i = getIntent();
        playerManager = (PlayerManager) i.getSerializableExtra("Game Manager");
        player = (Player) i.getSerializableExtra("Player");
        buttons();
        player.resetStats();
        save(playerManager, player);
    }

    private void buttons() {

        Button restart = findViewById(R.id.menu);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dead.this, MainActivity.class);
                intent.putExtra("Game Manager", playerManager);
                startActivity(intent);
            }
        });

        Button playAgain = findViewById(R.id.restart);
        playAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                save(playerManager, player);
                Intent intent = new Intent(Dead.this, BBMainActivity.class);
                intent.putExtra("Player", player);
                intent.putExtra("Game Manager", playerManager);
                startActivity(intent);
            }
        });
    }

    @Override
    public void save(PlayerManager playerManager, Player player) {
        super.save(playerManager, player);
    }

    private void load() {
        try {
            String filePath = this.getFilesDir().getPath() + "/GameState.txt";
            File f = new File(filePath);
            playerManager = (PlayerManager) SaveData.load(f);
        }
        catch (Exception e) {
            System.out.println("Couldn't load load data: " + e.getMessage());
        }
    }
}
