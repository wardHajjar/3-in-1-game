package com.example.dungeonescape.platformer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.dungeonescape.activities.GeneralGameActivity;
import com.example.dungeonescape.activities.MainActivity;
import com.example.dungeonescape.activities.MenuActivity;
import com.example.dungeonescape.player.PlayerManager;
import com.example.dungeonescape.player.Player;
import com.example.dungeonescape.R;

public class PlatformerInstructionsActivity extends GeneralGameActivity {

    private Player player;
    private PlayerManager playerManager;

    private MenuActivity menuActivity = new MenuActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platformer_main);
        setTitle("Level3: Platformer");
        // getting player instance from intent
        Intent i = getIntent();
        player = (Player) i.getSerializableExtra("Player");
        playerManager = (PlayerManager) i.getSerializableExtra("Game Manager");
        configureNextButton();
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
            Intent intent = menuActivity.createIntent(PlatformerInstructionsActivity.this,
                    MainActivity.class, playerManager, player);
            startActivity(intent);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private void configureNextButton() {
        Button nextButton = (Button) findViewById(R.id.startButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PlatformerInstructionsActivity.this, PlatformerMainActivity.class);
                intent.putExtra("Player", player);
                intent.putExtra("Game Manager", playerManager);

                startActivity(intent);
            }
        });


    }
}
