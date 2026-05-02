package fr.gregwll.homeGui.tp;

import fr.gregwll.homeGui.HomeGui;
import fr.gregwll.homeGui.utils.Constents;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PendingTeleport {

    private static long getExpiryMs() {
        return HomeGui.getInstance().getConfigManager().getConfirmExpiry() * 1000L;
    }

    private record Entry(Location location, long timestamp) {}

    private static final Map<UUID, Entry> pending = new HashMap<>();

    public static void set(UUID uuid, Location loc) {
        pending.put(uuid, new Entry(loc, System.currentTimeMillis()));

        // Suppression automatique après 1 minute
        Bukkit.getScheduler().runTaskLater(HomeGui.getInstance(), () -> {
            Entry entry = pending.get(uuid);
            if (entry != null && System.currentTimeMillis() - entry.timestamp() >= getExpiryMs()) {
                pending.remove(uuid);
                Player player = Bukkit.getPlayer(uuid);
                if (player != null && player.isOnline()) {
                    player.sendMessage(Constents.getPrefix() + "§fYour pending teleportation has expired.");
                }
            }
        }, 20L * 60); // 20 ticks * 60 = 1 minute
    }

    public static Location get(UUID uuid) {
        Entry entry = pending.get(uuid);
        if (entry == null) return null;
        if (System.currentTimeMillis() - entry.timestamp() >= getExpiryMs()) {
            pending.remove(uuid);
            return null;
        }
        return entry.location();
    }

    public static boolean has(UUID uuid) {
        return get(uuid) != null;
    }

    public static void remove(UUID uuid) {
        pending.remove(uuid);
    }
}