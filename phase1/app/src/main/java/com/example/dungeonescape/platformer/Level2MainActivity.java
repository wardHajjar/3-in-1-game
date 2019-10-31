package com.example.dungeonescape.platformer;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dungeonescape.Dead;
import com.example.dungeonescape.MainActivity;
import com.example.dungeonescape.Player;
import com.example.dungeonescape.R;

import java.util.logging.Level;


public class Level2MainActivity extends AppCompatActivity {
    private Level2View game;
    private boolean running;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the View we are using
        Intent i = getIntent();
        player = (Player) i.getSerializableExtra("Player");

        setContentView(R.layout.activity_level2_main);
        game = findViewById(R.id.level2);

        // getting player instance from intent
        //pass player into manager
        game.getManager().setPlayer(player);

        setTitle("Level3: Platformer");


        // Set Buttons
        buttons();
        running = true;

        // Thread code is from the following Youtube Video, body of run() is written myself
        // https://www.youtube.com/watch?v=6sBqeoioCHE&t=193s
        Thread t = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {

                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (running) {

                                    // Update the score shown

                                    int score = game.getManager().getCharacterScore();
                                    String scr = String.valueOf(score) ;
                                    String scre = "Score: " + scr;

                                    TextView score1 = (TextView) findViewById(R.id.score);
                                    score1.setText(scre);
                                    int lives = game.getManager().getPlayer().getNumLives();
                                    String life = "Lives: " + String.valueOf(lives);
                                    TextView lifeText = (TextView) findViewById(R.id.lives);
                                    lifeText.setText(life);
                                    boolean doneLevel = game.nextLevel();
                                    if (doneLevel) {
                                        nextLevel();
                                        running = false;
                                    }
                                    boolean dead = game.dead();
                                    if (dead){
                                        deadPage();
                                        running = false;
                                    }
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }
    private void nextLevel() {
        Intent intent = new Intent(Level2MainActivity.this, Level3FinishedActivity.class);
        startActivity(intent);
    }
    private void deadPage() {
        Intent intent = new Intent(Level2MainActivity.this, Dead.class);
        startActivity(intent);
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
     * Method for initializing left and right buttons.
     */
    private void buttons() {

        Button left = (Button) findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.getManager().left_button();
            }
        });

        Button right = (Button) findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.getManager().right_button();
            }
        });


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

