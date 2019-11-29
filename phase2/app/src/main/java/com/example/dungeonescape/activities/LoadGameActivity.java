package com.example.dungeonescape.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dungeonescape.player.Player;
import com.example.dungeonescape.player.PlayerManager;
import com.example.dungeonescape.R;
import com.example.dungeonescape.maze.MazeActivity;
import com.example.dungeonescape.brickbreaker.BBMainActivity;
import com.example.dungeonescape.platformer.PlatformerInstructionsActivity;

import java.util.List;

public class LoadGameActivity extends AppCompatActivity {
    private Player player;
    private PlayerManager playerManager;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);
        Intent i = getIntent();
        playerManager = (PlayerManager) i.getSerializableExtra("Game Manager");

        backButton();
        if (playerManager.getPlayerNames().size() == 0) {
            Toast t = Toast.makeText(this, "No players have been saved",
                    Toast.LENGTH_LONG);
            t.show();

        }
        else {
            setSpinner();
            enterButton();
        }
    }
    private void setSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> names = playerManager.getPlayerNames();
        String[] arr = names.toArray(new String[names.size()]);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(LoadGameActivity.this,
                android.R.layout.simple_list_item_1, arr);
        myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(myAdapter);
    }
    private void enterButton() {

        Button enter = (Button) findViewById(R.id.Enter);
        enter.setVisibility(View.VISIBLE);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = spinner.getSelectedItem().toString();
                player = playerManager.getPlayer(text);
                progress();
            }
        });

    }
    private void backButton() {
        Button back = (Button) findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoadGameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void progress() {
        int level = player.getCurrentLevel();
        if (level == 1 || level == 0) {
            Intent intent = new Intent(LoadGameActivity.this, BBMainActivity.class);
            intent.putExtra("Player", player);
            intent.putExtra("Game Manager", playerManager);
            startActivity(intent);
        }
        else if(level == 2) {
            Intent intent = new Intent(LoadGameActivity.this, MazeActivity.class);
            intent.putExtra("Player", player);
            intent.putExtra("Game Manager", playerManager);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(LoadGameActivity.this, PlatformerInstructionsActivity.class);
            intent.putExtra("Player", player);
            intent.putExtra("Game Manager", playerManager);
            startActivity(intent);
        }
    }

}
