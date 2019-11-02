package com.example.dungeonescape;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.io.File;

/**
 * The main view when the app is loaded. The user can choose to start a new game or load a previous
 * game.
 */
public class MainActivity extends AppCompatActivity{
    public GameManager gameManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        gameManager = (GameManager) i.getSerializableExtra("Game Manager");
        load();
        if (gameManager == null) {
            gameManager = new GameManager();
        }

        buttons();
    }

    /** Create and initialize functionality of buttons. */
    private void buttons() {
        Button newGame = findViewById(R.id.newGame);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewGameActivity.class);
                intent.putExtra("Game Manager", gameManager);
                startActivity(intent);
            }
        });

        Button loadGame = findViewById(R.id.loadGame);
        loadGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoadGameActivity.class);
                intent.putExtra("Game Manager", gameManager);
                startActivity(intent);
            }
        });
    }

    /** Load a previous game state. */
    private void load() {
        try {
            String filePath = this.getFilesDir().getPath() + "/GameState.txt";
            File f = new File(filePath);
            gameManager = (GameManager) SaveData.load(f);
        }
        catch (Exception e) {
            System.out.println("Could not load load data: " + e.getMessage());
        }
    }
}
