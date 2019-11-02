package com.example.dungeonescape;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents the Game Manager.
 *
 * It controls the statistics that determines pass/fail conditions.
 */

public class GameManager implements Serializable {
    private ArrayList<Player> players;

    public GameManager() {
        players = new ArrayList<>();
    }

    /**
     * Returns the list of Player names created within this Game.
     *
     * @return the ArrayList of Player names.
     */
    ArrayList<String> getPlayerNames() {
        ArrayList<String> playerNames = new ArrayList<>();
        for (Player player: players) {
            playerNames.add(player.getName());
        }
        return playerNames;
    }

    /** Adds a Player to the list of Players in this game. */
    void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Updates Player statistics for Game save data.
     *
     * @param name the name of the Player.
     * @param player the Player object.
     */
    void updatePlayer(String name, Player player) {
        Player p = getPlayer(name);
        p.setCurrentLevel(player.getCurrentLevel());
        p.setNumLives(player.getNumLives());
        p.setNumCoins(player.getNumCoins());

    }

    /**
     * Returns the Player associated with the specified name.
     *
     * @param name the String name.
     *
     * @return the Player object associated with name.
     */
    Player getPlayer(String name) {
        if (players.size() != 0) {
            Player p = players.get(0);

            for (Player player: players) {
                if (player.getName().equals(name)) {
                    p = player;
                    break;
                }
            }
            return p;
        } else {
            return null;
        }
    }
}
