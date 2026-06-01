package fr.gregwll.homeGui.obj;

import java.util.HashMap;

public class User {

    String playerName;
    HashMap<String, HomeLocation> homes;

    public User(String playerName) {
        this.playerName = playerName;
        homes = new HashMap<>();
    }

    public HashMap<String,HomeLocation> getHomesMap() {
        return homes;
    }

    public String getPlayerName() {
        return playerName;
    }
}
