package com.example.dungeonescape.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dungeonescape.player.Player;
import com.example.dungeonescape.player.PlayerManager;
import com.example.dungeonescape.game.SaveData;

import java.io.File;

public abstract class GeneralGameActivity extends AppCompatActivity {

    /** Saves the Player data.
     *
     * @param playerManager the playerManager in use.
     * @param player the current Player.
     */
    public void save(PlayerManager playerManager, Player player) {
        playerManager.updatePlayer(player.getName(), player);
        try {
            String filePath = this.getFilesDir().getPath() + "/GameState.txt";
            File f = new File(filePath);
            SaveData.save(playerManager, f);
        }
        catch (Exception e) {
            System.out.println("Couldn't save: " + e.getMessage());
        }
    }
}