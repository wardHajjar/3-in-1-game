package com.example.dungeonescape.platformer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dungeonescape.R;

import java.util.logging.Level;

public class Level2MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View Level2View = new Level2View(this);
        setContentView(R.layout.activity_level2_main);
        Level2View.setBackgroundColor(Color.WHITE);
    }

}
