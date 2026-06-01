package fr.gregwll.homeGui.cache;

import fr.gregwll.homeGui.HomeGui;
import fr.gregwll.homeGui.files.FileUtils;
import fr.gregwll.homeGui.files.UserSerializationManager;
import fr.gregwll.homeGui.obj.User;
import fr.gregwll.homeGui.utils.Constents;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserCache {

    private final Map<UUID, User> cache = new HashMap<>();
    private final UserSerializationManager usm;

    public UserCache(UserSerializationManager usm) {
        this.usm = usm;
    }

    public void load(UUID uuid, String playerName) {
        File fUser = new File(Constents.getSaveDir(), playerName + ".json");
        if (fUser.exists()) {
            User user = usm.deserialize(FileUtils.loadContent(fUser));
            cache.put(uuid, user);
        } else {
            cache.put(uuid, new User(playerName));
        }
    }

    public void save(UUID uuid, String playerName) {
        User user = cache.get(uuid);
        if (user == null) return;
        File fUser = new File(Constents.getSaveDir(), playerName + ".json");
        FileUtils.save(fUser, usm.serialize(user));
    }

    public void unload(UUID uuid, String playerName) {
        save(uuid, playerName);
        cache.remove(uuid);
    }

    public User get(UUID uuid) {
        return cache.get(uuid);
    }

    public boolean has(UUID uuid) {
        return cache.containsKey(uuid);
    }

    /**
     * Sauvegarde tous les joueurs en cache (utilisé à onDisable).
     */
    public void saveAll() {
        for (Map.Entry<UUID, User> entry : cache.entrySet()) {
            UUID uuid = entry.getKey();
            User user = entry.getValue();
            File fUser = new File(Constents.getSaveDir(), user.getPlayerName() + ".json");
            FileUtils.save(fUser, usm.serialize(user));
        }
    }
}