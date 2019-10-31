package com.example.dungeonescape;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.graphics.Color;
import com.example.dungeonescape.brickbreaker.BBMainActivity;

public class NewGameActivity extends AppCompatActivity {
    GameManager gameManager;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Intent i = getIntent();
        gameManager = (GameManager) i.getSerializableExtra("Game Manager");
        buttons();
    }

    private void buttons() {
        EditText name = (EditText) findViewById(R.id.txtSub);
        String nameText = name.getText().toString();
        System.out.println(nameText);
        gameManager.addPlayer(nameText);
        player = gameManager.getPlayer(nameText);

        Button colour1 = findViewById(R.id.colour1);
        colour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button enter = findViewById(R.id.Enter);
                enter.setVisibility(View.VISIBLE);
                player.setColour(Color.argb(255, 173, 0, 0));
            }
        });

        Button colour2 = findViewById(R.id.colour2);
        colour2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button enter = findViewById(R.id.Enter);
                enter.setVisibility(View.VISIBLE);
                player.setColour(Color.argb(255, 76, 175, 80));
            }
        });

        Button colour3 = findViewById(R.id.colour3);
        colour3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button enter = findViewById(R.id.Enter);
                enter.setVisibility(View.VISIBLE);
                player.setColour(Color.argb(255, 76, 175, 80));
            }
        });

        Button enter = findViewById(R.id.Enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewGameActivity.this, BBMainActivity.class);
                intent.putExtra("Player", player);
                startActivity(intent);
            }
        });
    }
}
