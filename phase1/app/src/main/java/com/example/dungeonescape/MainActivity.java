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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureNextButton();
    }

    private void configureNextButton() {
        Button nextButton = (Button) findViewById(R.id.button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Level2MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
