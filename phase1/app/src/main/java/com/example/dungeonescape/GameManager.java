package com.example.dungeonescape;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents the Game Manager.
 *
 * It controls the statistics that determines pass/fail conditions.
 *
 * TODO: Edit this javadoc as this class is updated.
 */

public class GameManager implements Serializable {
    ArrayList<Player> players;


    public GameManager() {
        /* Sets the initial total time elapsed in the Game to 0. */
        players = new ArrayList<>();


    }
    ArrayList<String> getPlayerNames() {
        ArrayList<String> arr = new ArrayList<>();
        for (Player player: players) {
            arr.add(player.getName());
        }
        return arr;
    }
    void addPlayer(Player player) {
        players.add(player);
    }

    public void updatePlayer(String name, Player player) {
        Player p = getPlayer(name);
        p.setCurrentLevel(player.getCurrentLevel());
        p.setNumLives(player.getNumLives());
        p.setNumCoins(player.getNumCoins());

    }
    public Player getPlayer(String name) {
        if (players.size() != 0) {
            Player p = players.get(0);

            for (Player player: players) {
                if (player.getName().equals(name)) {
                    p = player;
                    break;
                }
            }
            return p;
        }
        else {
            return null;
        }
    }
}
