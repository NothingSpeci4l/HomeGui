package fr.gregwll.homeGui.tp;

import fr.gregwll.homeGui.HomeGui;
import fr.gregwll.homeGui.utils.Constents;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportUtils {

    public static boolean isSafe(Location loc) {
        Location head = loc.clone().add(0, 1, 0);
        Location ground = loc.clone().add(0, -1, 0);

        return !loc.getBlock().getType().isSolid()
                && !head.getBlock().getType().isSolid()
                && ground.getBlock().getType().isSolid();
    }

    /**
     * Point d'entrée principal pour toutes les téléportations vers un home.
     * Gère : cooldown → sécurité → warmup → back → tp effectif.
     */
    public static boolean teleportOrConfirm(Player player, Location loc, String homeName) {
        var config = HomeGui.getInstance().getConfigManager();
        var cooldownManager = HomeGui.getInstance().getCooldownManager();
        var warmupManager = HomeGui.getInstance().getWarmupManager();

        // Cooldown
        int cooldown = config.getTeleportCooldown();
        if (cooldown > 0 && cooldownManager.isOnCooldown(player.getUniqueId(), cooldown)) {
            long remaining = cooldownManager.getRemaining(player.getUniqueId(), cooldown);
            player.sendMessage(Constents.getPrefix() + "§cPlease wait §l" + remaining + "§r§c second(s) before teleporting again.");
            return false;
        }

        // Sécurité
        if (!isSafe(loc)) {
            if (!config.isUnsafeTeleportAllowed()) {
                player.sendMessage(Constents.getPrefix() + "§cThis home is unsafe and unsafe teleport is disabled in the config.");
                return false;
            }
            PendingTeleport.set(player.getUniqueId(), loc);
            player.sendMessage(Constents.getPrefix() + "§e⚠ This home §l" + homeName + "§r§e is an unsafe location.");
            player.sendMessage(Constents.getPrefix() + "§eType §l/confirmhome§r§e to teleport anyway.");
            return false;
        }

        // Warmup
        int warmup = config.getTeleportWarmup();
        if (warmup > 0) {
            warmupManager.start(player, loc, homeName, () -> doTeleport(player, loc, homeName));
            return true;
        }

        // Téléportation immédiate
        doTeleport(player, loc, homeName);
        return true;
    }

    public static void doTeleport(Player player, Location loc, String homeName) {
        var backManager = HomeGui.getInstance().getBackManager();
        var cooldownManager = HomeGui.getInstance().getCooldownManager();
        var config = HomeGui.getInstance().getConfigManager();

        // Sauvegarder la position actuelle pour /back
        if (config.isBackEnabled()) {
            backManager.save(player.getUniqueId(), player.getLocation());
        }

        player.teleport(loc);
        cooldownManager.set(player.getUniqueId());
        player.sendMessage(Constents.getPrefix() + "§fTeleported to home §l" + homeName + "§r§f.");
    }
}