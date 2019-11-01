package com.example.dungeonescape;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Color;
import com.example.dungeonescape.brickbreaker.BBMainActivity;
import android.text.TextWatcher;
import android.text.Editable;

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
        final EditText name = findViewById(R.id.txtSub);
        final Button enter = findViewById(R.id.Enter);

        final Button colour1 = findViewById(R.id.colour1);
        colour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enter.setVisibility(View.VISIBLE);
                player.setColour(Color.argb(255, 173, 0, 0));
            }
        });

        final Button colour2 = findViewById(R.id.colour2);
        colour2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enter.setVisibility(View.VISIBLE);
                player.setColour(Color.argb(255, 76, 175, 80));
            }
        });

        final Button colour3 = findViewById(R.id.colour3);
        colour3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enter.setVisibility(View.VISIBLE);
                player.setColour(Color.argb(255, 76, 175, 80));
            }
        });

//        name.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//                if (!(name.getText().toString().matches(""))){
//                    TextView colorPrompt =  (TextView) findViewById(R.id.textView3);
//                    colorPrompt.setText((CharSequence)("Select Character Colour:"));
//                    colour1.setVisibility(View.VISIBLE);
//                    colour2.setVisibility(View.VISIBLE);
//                    colour3.setVisibility(View.VISIBLE);
//                }
//            }
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//        });

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
