package com.example.dungeonescape;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dungeonescape.R;
import com.example.dungeonescape.brickbreaker.BBMainActivity;

import java.io.File;

public class Dead extends GeneralGameActivity {
    public GameManager gameManager;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dead);
        Intent i = getIntent();
        gameManager = (GameManager) i.getSerializableExtra("Game Manager");
        player = (Player) i.getSerializableExtra("Player");
        buttons();
        player.resetStats();
        save(gameManager, player);
    }

    private void buttons() {

        Button restart = findViewById(R.id.menu);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dead.this, MainActivity.class);
                intent.putExtra("Game Manager", gameManager);
                startActivity(intent);
            }
        });

        Button playAgain = findViewById(R.id.restart);
        playAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                save(gameManager, player);
                Intent intent = new Intent(Dead.this, BBMainActivity.class);
                intent.putExtra("Player", player);
                intent.putExtra("Game Manager", gameManager);
                startActivity(intent);
            }
        });
    }

    @Override
    public void save(GameManager gameManager, Player player) {
        super.save(gameManager, player);
    }

    private void load() {
        try {
            String filePath = this.getFilesDir().getPath() + "/GameState.txt";
            File f = new File(filePath);
            gameManager = (GameManager) SaveData.load(f);
        }
        catch (Exception e) {
            System.out.println("Couldn't load load data: " + e.getMessage());
        }
    }
}
