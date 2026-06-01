package fr.gregwll.homeGui.cmd;

import fr.gregwll.homeGui.HomeGui;
import fr.gregwll.homeGui.utils.Constents;
import fr.gregwll.homeGui.tp.BackManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CBack implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player p)) return true;

        if (!HomeGui.getInstance().getConfigManager().isBackEnabled()) {
            p.sendMessage(Constents.getPrefix() + "§cThe /back command is disabled in the config.");
            return true;
        }

        BackManager backManager = HomeGui.getInstance().getBackManager();

        if (!backManager.has(p.getUniqueId())) {
            p.sendMessage(Constents.getPrefix() + "§fNo previous location saved.");
            return true;
        }

        Location loc = backManager.get(p.getUniqueId());
        backManager.remove(p.getUniqueId());
        p.teleport(loc);
        p.sendMessage(Constents.getPrefix() + "§fReturned to your previous location.");
        return true;
    }
}