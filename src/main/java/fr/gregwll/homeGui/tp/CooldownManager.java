package fr.gregwll.homeGui.tp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private final Map<UUID, Long> cooldowns = new HashMap<>();

    /**
     * Retourne les secondes restantes, 0 si le cooldown est expiré ou inexistant.
     */
    public long getRemaining(UUID uuid, int cooldownSeconds) {
        Long last = cooldowns.get(uuid);
        if (last == null) return 0;
        long elapsed = (System.currentTimeMillis() - last) / 1000;
        long remaining = cooldownSeconds - elapsed;
        return Math.max(0, remaining);
    }

    public boolean isOnCooldown(UUID uuid, int cooldownSeconds) {
        return getRemaining(uuid, cooldownSeconds) > 0;
    }

    public void set(UUID uuid) {
        cooldowns.put(uuid, System.currentTimeMillis());
    }

    public void remove(UUID uuid) {
        cooldowns.remove(uuid);
    }
}