package com.example.dungeonescape.Platformer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.dungeonescape.R;

public class PlatformerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platformer_main);
        setTitle("Level2: Platformer");
    }
}
