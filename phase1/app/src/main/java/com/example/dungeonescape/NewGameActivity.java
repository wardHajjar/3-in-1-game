package com.example.dungeonescape;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.graphics.Color;
import android.widget.TextView;

import com.example.dungeonescape.brickbreaker.BBMainActivity;

import java.io.File;

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
        final EditText name = findViewById(R.id.nameInput);
        final TextView diffPrompt = findViewById(R.id.diffPrompt);
        final Button enter = findViewById(R.id.enter);
        final Button easy = findViewById(R.id.easy);
        final Button hard = findViewById(R.id.hard);
        final Button colour1 = findViewById(R.id.colour1);
        colour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameText = name.getText().toString();
                player = new Player(nameText);
                gameManager.addPlayer(player);
                save();
                player.setColour(Color.argb(255, 173, 0, 0));
                diffPrompt.setText((CharSequence)("Chose Difficulty Level:"));
                easy.setVisibility(View.VISIBLE);
                hard.setVisibility(View.VISIBLE);
            }
        });

        final Button colour2 = findViewById(R.id.colour2);
        colour2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameText = name.getText().toString();
                player = new Player(nameText);
                gameManager.addPlayer(player);
                save();
                player.setColour(Color.argb(255, 76, 175, 80));
                diffPrompt.setText((CharSequence)("Chose Difficulty Level:"));
                easy.setVisibility(View.VISIBLE);
                hard.setVisibility(View.VISIBLE);

            }
        });

        final Button colour3 = findViewById(R.id.colour3);
        colour3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameText = name.getText().toString();
                player = new Player(nameText);
                gameManager.addPlayer(player);
                save();
                player.setColour(Color.argb(255, 255, 193, 7));
                diffPrompt.setText((CharSequence)("Chose Difficulty Level:"));
                easy.setVisibility(View.VISIBLE);
                hard.setVisibility(View.VISIBLE);
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setDifficulty("Easy");
                enter.setVisibility(View.VISIBLE);
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setDifficulty("Hard");
                enter.setVisibility(View.VISIBLE);
            }
        });

        name.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (!(name.getText().toString().matches(""))){
                    TextView colorPrompt =  (TextView) findViewById(R.id.colorPromptText);
                    colorPrompt.setText((CharSequence)("Select Character Colour:"));
                    colour1.setVisibility(View.VISIBLE);
                    colour2.setVisibility(View.VISIBLE);
                    colour3.setVisibility(View.VISIBLE);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
