package fr.gregwll.homeGui.tp;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BackManager {

    private final Map<UUID, Location> lastLocations = new HashMap<>();

    public void save(UUID uuid, Location location) {
        lastLocations.put(uuid, location.clone());
    }

    public Location get(UUID uuid) {
        return lastLocations.get(uuid);
    }

    public boolean has(UUID uuid) {
        return lastLocations.containsKey(uuid);
    }

    public void remove(UUID uuid) {
        lastLocations.remove(uuid);
    }
}