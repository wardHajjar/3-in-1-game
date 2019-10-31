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
    private int totalTimePlayed;

    public GameManager() {
        /* Sets the initial total time elapsed in the Game to 0. */
        totalTimePlayed = 0;
        players = new ArrayList<>();

    }
    void addPlayer(String name) {
        players.add(new Player(name));
    }

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
        }
        else {
            return null;
        }
    }

    public int getTotalTime() {
        return totalTimePlayed;
    }

    /** As each second passes, increment the totalTime elapsed.
     *
     * TODO: Include this in whichever main update() method we use.
     */
    public void updateTotalTime() {
        /* TODO: implement time function. */
    }
}
