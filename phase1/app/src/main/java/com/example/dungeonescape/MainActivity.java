package com.example.dungeonescape;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dungeonescape.Maze.MazeActivity;
import com.example.dungeonescape.platformer.Level2MainActivity;
import com.example.dungeonescape.platformer.PlatformerMainActivity;

public class MainActivity extends AppCompatActivity {
    public GameManager gameManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttons();
        gameManager = new GameManager();
    }


    private void buttons() {

        Button nextButton = (Button) findViewById(R.id.button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, MazeActivity.class);
                startActivity(intent);
            }
        });

        Button newGame = (Button) findViewById(R.id.newGame);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, NewGameActivity.class);
                startActivity(intent);
            }
        });

        Button loadGame = (Button) findViewById(R.id.loadGame);
        loadGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, LoadGameActivity.class);
                startActivity(intent);
            }
        });


    }
}
