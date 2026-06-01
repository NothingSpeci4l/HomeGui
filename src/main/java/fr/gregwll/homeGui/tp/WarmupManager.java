package fr.gregwll.homeGui.tp;

import fr.gregwll.homeGui.HomeGui;
import fr.gregwll.homeGui.utils.Constents;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WarmupManager {

    private record Entry(BukkitTask task, Location startLocation, Location destination, String homeName) {}

    private final Map<UUID, Entry> warmups = new HashMap<>();

    public void start(Player player, Location destination, String homeName, Runnable onComplete) {
        cancel(player.getUniqueId());

        int warmupSeconds = HomeGui.getInstance().getConfigManager().getTeleportWarmup();
        Location startLocation = player.getLocation().clone();

        BukkitTask task = HomeGui.getInstance().getServer().getScheduler()
                .runTaskLater(HomeGui.getInstance(), () -> {
                    warmups.remove(player.getUniqueId());
                    onComplete.run();
                }, 20L * warmupSeconds);

        warmups.put(player.getUniqueId(), new Entry(task, startLocation, destination, homeName));

        player.sendMessage(Constents.getPrefix() + "§fTeleportation in §l" + warmupSeconds + "§r§f seconds...");
    }

    public void cancel(UUID uuid) {
        Entry entry = warmups.remove(uuid);
        if (entry != null) {
            entry.task().cancel();
        }
    }

    public boolean isWaitingFor(UUID uuid) {
        return warmups.containsKey(uuid);
    }

    public Location getStartLocation(UUID uuid) {
        Entry entry = warmups.get(uuid);
        return entry != null ? entry.startLocation() : null;
    }

    public String getHomeName(UUID uuid) {
        Entry entry = warmups.get(uuid);
        return entry != null ? entry.homeName() : null;
    }
}