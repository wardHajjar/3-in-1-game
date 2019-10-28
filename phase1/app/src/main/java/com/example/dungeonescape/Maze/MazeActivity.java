package com.example.dungeonescape.Maze;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dungeonescape.MainActivity;
import com.example.dungeonescape.R;
import com.example.dungeonescape.platformer.PlatformerMainActivity;

public class MazeActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);
        configureNextButton();
    }

    private void configureNextButton() {
        Button nextButton = (Button) findViewById(R.id.nextlvl);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MazeActivity.this, PlatformerMainActivity.class);
                startActivity(intent);
            }
        });


    }
}
