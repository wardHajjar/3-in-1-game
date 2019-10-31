package com.example.dungeonescape;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dungeonescape.Maze.MazeActivity;

public class LoadGameActivity extends AppCompatActivity {
    Player player;
    GameManager gameManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);
        Intent i = getIntent();
        gameManager = (GameManager) i.getSerializableExtra("Game Manager");
        buttons();
    }

    private void buttons() {

        EditText name = (EditText) findViewById(R.id.name);
        String nameText = name.getText().toString();

        player = gameManager.getPlayer(nameText);
        if (player == null) {
            System.out.println("USER DOES NOT EXIST");
        }
        else {
            Button enter = (Button) findViewById(R.id.Enter);
            enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(LoadGameActivity.this, MazeActivity.class);
                    intent.putExtra("Player", player);
                    startActivity(intent);
                }
            });
        }
    }

}
