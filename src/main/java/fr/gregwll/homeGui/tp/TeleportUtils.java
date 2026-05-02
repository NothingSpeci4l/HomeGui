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
     * Retourne true si le tp a eu lieu, false si unsafe et nécessite confirmation.
     */
    public static boolean teleportOrConfirm(Player player, Location loc, String homeName) {
        if (isSafe(loc)) {
            player.teleport(loc);
            player.sendMessage(Constents.getPrefix() + "§fTeleported to home §l" + homeName + "§r§f.");
            return true;
        }

        if (!HomeGui.getInstance().getConfigManager().isUnsafeTeleportAllowed()) {
            player.sendMessage(Constents.getPrefix() + "§cThis home is unsafe and unsafe teleport is disabled in the config.");
            return false;
        }

        PendingTeleport.set(player.getUniqueId(), loc);
        player.sendMessage(Constents.getPrefix() + "§e⚠ This home §l" + homeName + "§r§e is an unsafe location.");
        player.sendMessage(Constents.getPrefix() + "§eType §l/confirmhome§r§e to teleport anyway.");
        return false;
    }
}