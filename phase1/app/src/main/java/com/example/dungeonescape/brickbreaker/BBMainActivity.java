package com.example.dungeonescape.brickbreaker;

import android.app.Activity;
import android.os.Bundle;

/*
BBMainActivity and BBView were structured like the following game:
http://gamecodeschool.com/android/building-a-simple-game-engine/
 */
/**
 * The main activity of the game (entry point).
 */
public class BBMainActivity extends Activity {
    /**
     * The game's view that updates and draws the objects within it.
     */
    BBView gameView;

    /**
     *
     * @param savedInstanceState Bundle object that passes data between activities.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize gameView and set it as the view
        gameView = new BBView(this);
        setContentView(gameView);
    }

    /**
     * Method executes when the player starts the game.
     */
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    /**
     * Method executes when the player quits the game.
     */
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }
}
