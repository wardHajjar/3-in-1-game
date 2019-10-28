package com.example.dungeonescape.platformer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;

import android.os.Bundle;

import com.example.dungeonescape.R;


public class Level2MainActivity extends Activity {
    Level2View game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_main);
        game = findViewById(R.id.level2);
    }

    /**
     * Method executes when the player starts the game.
     */
    @Override
    protected void onResume() {
        super.onResume();
        game.resume();
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

