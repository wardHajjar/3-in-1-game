package com.example.dungeonescape.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.dungeonescape.player.Player;
import com.example.dungeonescape.player.PlayerManager;
import com.example.dungeonescape.R;
import com.example.dungeonescape.maze.MazeActivity;
import com.example.dungeonescape.brickbreaker.BBMainActivity;
import com.example.dungeonescape.platformer.PlatformerInstructions;

import java.util.ArrayList;

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
        setSpinner();
        buttons();
    }
    private void setSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList<String> names = playerManager.getPlayerNames();
        String[] arr = names.toArray(new String[names.size()]);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(LoadGameActivity.this,
                android.R.layout.simple_list_item_1, arr);
        myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(myAdapter);
    }
    private void buttons() {

        Button enter = (Button) findViewById(R.id.Enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = spinner.getSelectedItem().toString();
                player = playerManager.getPlayer(text);
                progress();
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
            Intent intent = new Intent(LoadGameActivity.this, PlatformerInstructions.class);
            intent.putExtra("Player", player);
            intent.putExtra("Game Manager", playerManager);
            startActivity(intent);
        }
    }

}
