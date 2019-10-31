package com.example.dungeonescape;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dungeonescape.Maze.MazeActivity;

public class NewGameActivity extends AppCompatActivity {
    GameManager gameManager;
    Player player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        buttons();
        Intent i = getIntent();
        gameManager = (GameManager) i.getSerializableExtra("Game Manager");
    }

    private void buttons() {
        EditText name = (EditText) findViewById(R.id.txtSub);
        String nameText = name.getText().toString();
        gameManager.addPlayer(nameText);
        player = gameManager.getPlayer(nameText);

        Button enter = (Button) findViewById(R.id.Enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(NewGameActivity.this, MazeActivity.class);
                intent.putExtra("Player", player);
                startActivity(intent);
            }
        });



    }
}
