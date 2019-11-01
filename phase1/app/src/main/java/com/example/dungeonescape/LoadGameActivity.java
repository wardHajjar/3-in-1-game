package com.example.dungeonescape;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dungeonescape.Maze.MazeActivity;
import com.example.dungeonescape.brickbreaker.BBMainActivity;
import com.example.dungeonescape.platformer.PlatformerMainActivity;

import java.io.File;

public class LoadGameActivity extends AppCompatActivity {
    Player player;
    GameManager gameManager;
    GameManager data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);
        Intent i = getIntent();
        gameManager = (GameManager) i.getSerializableExtra("Game Manager");

        load();
        buttons();

    }

    private void buttons() {

        final EditText name = (EditText) findViewById(R.id.name);

        Button enter = (Button) findViewById(R.id.Enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameText = name.getText().toString();

                player = gameManager.getPlayer(nameText);
                progress();

            }
        });

    }
    private void progress() {
        int level = player.getCurrentLevel();

        if (level == 1) {
            Intent intent = new Intent(LoadGameActivity.this, BBMainActivity.class);
            intent.putExtra("Player", player);
            intent.putExtra("Game Manager", gameManager);
            startActivity(intent);
        }
        else if(level == 2) {
            Intent intent = new Intent(LoadGameActivity.this, MazeActivity.class);
            intent.putExtra("Player", player);
            intent.putExtra("Game Manager", gameManager);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(LoadGameActivity.this, PlatformerMainActivity.class);
            intent.putExtra("Player", player);
            intent.putExtra("Game Manager", gameManager);
            startActivity(intent);
        }
    }
    private void load() {
        try {
            String filePath = this.getFilesDir().getPath() + "/GameState.txt";
            File f = new File(filePath);
            gameManager = (GameManager) SaveData.load(f);
        }
        catch (Exception e) {
            System.out.println("Couldn't load save data: " + e.getMessage());
        }
    }

}
