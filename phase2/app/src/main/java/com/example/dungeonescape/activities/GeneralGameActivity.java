package com.example.dungeonescape.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dungeonescape.player.PlayerManager;
import com.example.dungeonescape.game.SaveData;

import java.io.File;

public abstract class GeneralGameActivity extends AppCompatActivity {

    private PlayerManager playerManager;

    /** Saves the Player data.
     *
     * @param playerManager the playerManager in use.
     */

    public void save(PlayerManager playerManager) {

        try {
            String filePath = this.getFilesDir().getPath() + "/GameState.txt";
            File f = new File(filePath);
            SaveData.save(playerManager, f);
        }
        catch (Exception e) {
            System.out.println("Couldn't save: " + e.getMessage());
        }
    }
    /** Loads the Player data.
     */
    public void load() {
        try {
            String filePath = this.getFilesDir().getPath() + "/GameState.txt";
            File f = new File(filePath);
            playerManager = (PlayerManager) SaveData.load(f);
        }
        catch (Exception e) {
            System.out.println("Couldn't load load data: " + e.getMessage());
        }
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
        save(playerManager);
    }
}