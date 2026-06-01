package fr.gregwll.homeGui.cmd;

import fr.gregwll.homeGui.HomeGui;
import fr.gregwll.homeGui.obj.User;
import fr.gregwll.homeGui.utils.Constents;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CDelhome implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player p)) return true;

        if (args.length != 1) {
            p.sendMessage(Constents.getPrefix() + "§f§lUsage: §7/delhome <homeName>");
            return true;
        }

        String homeName = args[0];
        User user = HomeGui.getInstance().getUserCache().get(p.getUniqueId());

        if (user == null || !user.getHomesMap().containsKey(homeName)) {
            p.sendMessage(Constents.getPrefix() + "§fHome §l" + homeName + "§r§f not found.");
            return true;
        }

        user.getHomesMap().remove(homeName);
        HomeGui.getInstance().getUserCache().save(p.getUniqueId(), p.getName());
        p.sendMessage(Constents.getPrefix() + "§fHome §l" + homeName + "§r§f deleted.");
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (!(sender instanceof Player p) || args.length != 1) return suggestions;

        User user = HomeGui.getInstance().getUserCache().get(p.getUniqueId());
        if (user == null) return suggestions;

        for (String homeName : user.getHomesMap().keySet()) {
            if (homeName.startsWith(args[0])) suggestions.add(homeName);
        }
        return suggestions;
    }
}