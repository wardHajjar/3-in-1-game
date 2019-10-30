package com.example.dungeonescape;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dungeonescape.Maze.MazeActivity;

public class LoadGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);
        buttons();
    }

    private void buttons() {


        Button enter = (Button) findViewById(R.id.Enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoadGameActivity.this, MazeActivity.class);
                startActivity(intent);
            }
        });

        EditText name = (EditText) findViewById(R.id.name);
        String nameText = name.getText().toString();
    }
}
