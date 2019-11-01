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
import android.widget.Toast;

import com.example.dungeonescape.brickbreaker.BBMainActivity;

import java.io.File;
import java.util.ArrayList;

import static android.text.TextUtils.isEmpty;

public class NewGameActivity extends AppCompatActivity {
    GameManager gameManager;
    GameManager data;
    Player player;
    EditText name;
    String nameText;
    Boolean isValid;
    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Intent i = getIntent();
        gameManager = (GameManager) i.getSerializableExtra("Game Manager");
        isValid = false;
        name = findViewById(R.id.nameInput);
        buttons();
    }

    private void buttons() {

        final Button enter = findViewById(R.id.Enter);
        final Button colour1 = findViewById(R.id.colour1);
        colour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkName();
                if (isValid) {
                    color = Color.argb(255, 173, 0, 0);

                    enter.setVisibility(View.VISIBLE);
                }
            }
        });

        final Button colour2 = findViewById(R.id.colour2);
        colour2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkName();
                if (isValid) {
                    color = Color.argb(255, 76, 175, 80);

                    enter.setVisibility(View.VISIBLE);

                }
            }
        });

        final Button colour3 = findViewById(R.id.colour3);
        colour3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValid) {

                    setText(color);
                    enter.setVisibility(View.VISIBLE);
                }
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
                checkName();
                if (isValid) {
                    setText(Color.argb(255, 76, 175, 80));
                    Intent intent = new Intent(NewGameActivity.this, BBMainActivity.class);
                    intent.putExtra("Player", player);
                    intent.putExtra("Game Manager", gameManager);
                    startActivity(intent);
                }
            }
        });

    }
    void checkName() {
        nameText = name.getText().toString();
        isValid = true;
        ArrayList<String> names = gameManager.getPlayerNames();
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

    private void setText(int color) {
        player = new Player(nameText);
        player.setColour(color);
        gameManager.addPlayer(player);
        save();
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
