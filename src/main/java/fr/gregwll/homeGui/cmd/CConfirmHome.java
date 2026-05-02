package fr.gregwll.homeGui.cmd;

import fr.gregwll.homeGui.utils.Constents;
import fr.gregwll.homeGui.tp.PendingTeleport;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CConfirmHome implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player p)) return true;

        if (!PendingTeleport.has(p.getUniqueId())) {
            p.sendMessage(Constents.getPrefix() + "§fNo pending teleportation.");
            return true;
        }

        Location loc = PendingTeleport.get(p.getUniqueId());
        PendingTeleport.remove(p.getUniqueId());

        p.teleport(loc);
        p.sendMessage(Constents.getPrefix() + "§fTeleported to unsafe home.");
        return true;
    }
}