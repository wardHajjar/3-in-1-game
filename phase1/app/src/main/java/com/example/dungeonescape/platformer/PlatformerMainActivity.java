package com.example.dungeonescape.platformer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dungeonescape.MainActivity;
import com.example.dungeonescape.Player;
import com.example.dungeonescape.R;

public class PlatformerMainActivity extends AppCompatActivity {

    Player player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platformer_main);
        setTitle("Level3: Platformer");
        // getting player instance from intent
        Intent i = getIntent();
        player = (Player) i.getSerializableExtra("Player");
        configureNextButton();
    }

    private void configureNextButton() {
        Button nextButton = (Button) findViewById(R.id.startButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PlatformerMainActivity.this, Level2MainActivity.class);
                intent.putExtra("Player", player);
                startActivity(intent);
            }
        });


    }
}
