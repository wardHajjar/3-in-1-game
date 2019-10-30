package com.example.dungeonescape.platformer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dungeonescape.R;


public class Level2MainActivity extends Activity {
    Level2View game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_main);
        game = findViewById(R.id.level2);
        buttons();

    }

    /**
     * Method executes when the player starts the game.
     */
    @Override
    protected void onResume() {
        super.onResume();
        game.resume();
    }
    private void buttons() {
        Button left = (Button) findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.manager.left_button();
            }
        });

        Button right = (Button) findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.manager.right_button();
            }
        });

        int score = game.manager.getCharacterScore();
        String scr = String.valueOf(score);
        TextView score1=(TextView) findViewById(R.id.score);
        score1.setText(scr);
    }
    /**
     * Method executes when the player quits the game.
     */
    @Override
    protected void onPause() {
        super.onPause();
        game.pause();
    }
}

