package com.example.dungeonescape.activities;

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
import android.widget.Toast;

import com.example.dungeonescape.player.Player;
import com.example.dungeonescape.player.PlayerManager;
import com.example.dungeonescape.R;
import com.example.dungeonescape.game.SaveData;
import com.example.dungeonescape.brickbreaker.BBMainActivity;
import java.io.File;
import java.util.ArrayList;

import static android.text.TextUtils.isEmpty;

public class NewGameActivity extends AppCompatActivity {
    PlayerManager playerManager;
    Player player;
    EditText name;
    String nameText;
    Boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Intent i = getIntent();
        playerManager = (PlayerManager) i.getSerializableExtra("Game Manager");
        isValid = false;
        name = findViewById(R.id.nameInput);
        buttons();
    }

    private void buttons() {
        final TextView newGameText = findViewById(R.id.newGameText);
        final Button enter = findViewById(R.id.enter);
        final EditText name = findViewById(R.id.nameInput);
        final TextView diffPrompt = findViewById(R.id.diffPrompt);
        final Button easy = findViewById(R.id.easy);
        final Button hard = findViewById(R.id.hard);
        final Button colour1 = findViewById(R.id.colour1);
        final Button colour2 = findViewById(R.id.colour2);
        final Button enterName = findViewById(R.id.checkName);
        final Button colour3 = findViewById(R.id.colour3);
        final TextView namePrompt = findViewById(R.id.enterNameText);
        final TextView welcomeDisplay = findViewById(R.id.welcomeDisplay);

        enterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkName();
                if (isValid){
                    player = new Player(nameText);
                    playerManager.addPlayer(player);
                    enter.setVisibility(View.INVISIBLE);
                    name.setVisibility(View.INVISIBLE);
                    namePrompt.setVisibility(View.INVISIBLE);
                    newGameText.setVisibility(View.INVISIBLE);
                    welcomeDisplay.setVisibility(View.VISIBLE);
                    String welcomeMessage = "Welcome " + nameText;
                    welcomeDisplay.setText(welcomeMessage);
                    TextView colorPrompt =  (TextView) findViewById(R.id.colorPromptText);
                    enterName.setVisibility(View.INVISIBLE);
                    colorPrompt.setText((CharSequence)("Select Character Colour:"));
                    colour1.setVisibility(View.VISIBLE);
                    colour2.setVisibility(View.VISIBLE);
                    colour3.setVisibility(View.VISIBLE);
                }
            }
        });

        colour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid) {
                    int color = Color.argb(255, 173, 0, 0);
                    player.setColour(color);
                    save();
                    diffPrompt.setText(("Chose Difficulty Level:"));
                    easy.setVisibility(View.VISIBLE);
                    hard.setVisibility(View.VISIBLE);
                }
            }
        });

        colour2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid) {
                    int color = Color.argb(255, 76, 175, 80);
                    player.setColour(color);
                    save();
                    diffPrompt.setText(("Chose Difficulty Level:"));
                    easy.setVisibility(View.VISIBLE);
                    hard.setVisibility(View.VISIBLE);

                }
            }
        });


        colour3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValid) {
                    int color = Color.argb(255, 255, 193, 7);
                    player.setColour(color);
                    save();
                    diffPrompt.setText(("Chose Difficulty Level:"));
                    easy.setVisibility(View.VISIBLE);
                    hard.setVisibility(View.VISIBLE);
                }
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid) {
                    player.setDifficulty("Easy");
                    save();
                    enter.setVisibility(View.VISIBLE);

                }

            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid) {
                    player.setDifficulty("Hard");
                    save();
                    enter.setVisibility(View.VISIBLE);
                }

            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid) {
                    Intent intent = new Intent(NewGameActivity.this, BBMainActivity.class);
                    intent.putExtra("Player", player);
                    intent.putExtra("Game Manager", playerManager);
                    startActivity(intent);
                }
            }
        });

    }
    void checkName() {
        nameText = name.getText().toString();
        isValid = true;
        ArrayList<String> names = playerManager.getPlayerNames();
        if (isEmpty(nameText)) {
            Toast t = Toast.makeText(this, "Please Enter a Name", Toast.LENGTH_SHORT);
            isValid = false;
            t.show();
        }
        else if (names.contains(nameText)) {
            Toast t = Toast.makeText(this, "There is already a player saved with " +
                    "this name", Toast.LENGTH_SHORT);
            isValid = false;
            t.show();
        }
    }

    private void save() {
        playerManager.updatePlayer(player.getName(), player);
        try {
            String filePath = this.getFilesDir().getPath() + "/GameState.txt";
            File f = new File(filePath);
            SaveData.save(playerManager, f);
        }
        catch (Exception e) {
            System.out.println("Couldn't save: " + e.getMessage());
        }
    }
}
