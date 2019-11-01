package com.example.dungeonescape;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dungeonescape.GameManager;
import com.example.dungeonescape.Player;
import com.example.dungeonescape.SaveData;

import java.io.File;

public abstract class GeneralGameActivity extends AppCompatActivity {

    /** Saves the Player data.
     *
     * @param gameManager the gameManager in use.
     * @param player the current Player.
     */
    public void save(GameManager gameManager, Player player) {
        gameManager.updatePlayer(player.getName(), player);
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