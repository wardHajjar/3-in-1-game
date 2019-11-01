package com.example.dungeonescape;
import com.example.dungeonescape.brickbreaker.BBMainActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.File;


public class PlayerStats extends AppCompatActivity {
    GameManager gameManager;
    Player player;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Intent i = getIntent();
        player = (Player) i.getSerializableExtra("Player");
        gameManager = (GameManager) i.getSerializableExtra("Game Manager");

        buttons();

//        TextView numtimer = findViewById(R.id.numTimerText);
//        numtimer.setText()

        TextView numcoins = findViewById(R.id.numCoinsText);
        numcoins.setText(String.valueOf(player.getNumCoins()));

        TextView numlives = findViewById(R.id.numLivesText);
        numlives.setText(String.valueOf(player.getNumLives()));
    }


    private void buttons() {
        Button playAgain = findViewById(R.id.playAgain);
        playAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                player.resetStats();
                save();
                Intent intent = new Intent(PlayerStats.this, BBMainActivity.class);
                intent.putExtra("Player", player);
                intent.putExtra("Game Manager", gameManager);
                startActivity(intent);
            }
        });
    }

    private void save() {
        try {
            String filePath = this.getFilesDir().getPath() + "/GameState.txt";
            File f = new File(filePath);
            SaveData.save(gameManager, f);
        }
        catch (Exception e) {
            System.out.println("Couldn't save: " + e.getMessage());
        }
    }


}