package com.example.dungeonescape;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dungeonescape.R;

public class Dead extends AppCompatActivity {
    public GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dead);

        gameManager = new GameManager();
        buttons();
    }

    private void buttons() {

        Button restart = (Button) findViewById(R.id.button);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Dead.this, MainActivity.class);
                intent.putExtra("Game Manager", gameManager);
                startActivity(intent);
            }
        });
    }
}
