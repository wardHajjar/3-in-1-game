package com.example.dungeonescape;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.graphics.Color;
import com.example.dungeonescape.brickbreaker.BBMainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class NewGameActivity extends AppCompatActivity {
    GameManager gameManager;
    GameManager data;
    Player player;

    EditText name;
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

        final EditText name = (EditText) findViewById(R.id.txtSub);
        Button enter = (Button) findViewById(R.id.Enter);
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
                String nameText = name.getText().toString();
                gameManager.addPlayer(nameText);
                player = gameManager.getPlayer(nameText);
                save();
                Intent intent = new Intent(NewGameActivity.this, BBMainActivity.class);
                intent.putExtra("Player", player);
                intent.putExtra("Game Manager", gameManager);
                startActivity(intent);
            }
        });
    }
    private void save() {
        try {
            String filePath = this.getFilesDir().getPath() + "/GameState.txt";
            File f = new File(filePath);
            SaveData.save(gameManager, f);
        }
        catch (Exception e) {
            System.out.println("Couldn't save: " + e.getMessage());
        }
    }

}
