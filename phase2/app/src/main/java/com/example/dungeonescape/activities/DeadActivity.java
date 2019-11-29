package com.example.dungeonescape.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.dungeonescape.player.Player;
import com.example.dungeonescape.player.PlayerManager;
import com.example.dungeonescape.R;
import com.example.dungeonescape.game.SaveData;
import com.example.dungeonescape.brickbreaker.BBMainActivity;

import java.io.File;

public class DeadActivity extends GeneralGameActivity {
    private PlayerManager playerManager;
    private Player player;

    private MenuActivity menuActivity = new MenuActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dead);
        Intent i = getIntent();
        playerManager = (PlayerManager) i.getSerializableExtra("Game Manager");
        player = (Player) i.getSerializableExtra("Player");
        configureActionButtons();
        player.resetStats();
        save(playerManager, player);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_menu) {
            save(playerManager, player);
            Intent intent = menuActivity.createIntent(DeadActivity.this,
                    MainActivity.class, playerManager, player);
            startActivity(intent);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private void configureActionButtons() {
        configurePlayAgainButton();
        configureMenuButton();
    }

    private void configurePlayAgainButton() {
        Button playAgain = findViewById(R.id.playAgain);
        playAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                save(playerManager, player);
                Intent intent = new Intent(DeadActivity.this, BBMainActivity.class);
                intent.putExtra("Player", player);
                intent.putExtra("Game Manager", playerManager);
                startActivity(intent);
            }
        });
    }

    private void configureMenuButton() {
        Button mainMenu = findViewById(R.id.mainMenu);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeadActivity.this, MainActivity.class);
                intent.putExtra("Game Manager", playerManager);
                startActivity(intent);
            }
        });
    }

    @Override
    public void save(PlayerManager playerManager, Player player) {
        super.save(playerManager, player);
    }

    private void load() {
        try {
            String filePath = this.getFilesDir().getPath() + "/GameState.txt";
            File f = new File(filePath);
            playerManager = (PlayerManager) SaveData.load(f);
        }
        catch (Exception e) {
            System.out.println("Couldn't load load data: " + e.getMessage());
        }
    }
}
