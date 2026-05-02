package fr.gregwll.homeGui.cmd.ad;

import fr.gregwll.homeGui.HomeGui;
import fr.gregwll.homeGui.files.FileUtils;
import fr.gregwll.homeGui.files.UserSerializationManager;
import fr.gregwll.homeGui.obj.HomeLocation;
import fr.gregwll.homeGui.obj.User;
import fr.gregwll.homeGui.utils.Constents;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CHomeAd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player p)) return true;

        if (!p.isOp() && !p.hasPermission("hg.admin")) {
            p.sendMessage(Constents.getPrefix() + "§fYou don't have permission to use this command.");
            return true;
        }

        if (args.length != 1 || !args[0].contains(":")) {
            p.sendMessage(Constents.getPrefix() + "§f§lUsage: §7/homeadmin <playerName>:<homeName>");
            return true;
        }

        String[] split = args[0].split(":");
        if (split.length != 2) {
            p.sendMessage(Constents.getPrefix() + "§f§lUsage: §7/homeadmin <playerName>:<homeName>");
            return true;
        }

        String targetName = split[0];
        String homeName = split[1];

        final File fUser = new File(Constents.getSaveDir(), targetName + ".json");
        if (!fUser.exists()) {
            p.sendMessage(Constents.getPrefix() + "§fPlayer §l" + targetName + "§r§f has no homes.");
            return true;
        }

        final UserSerializationManager usm = HomeGui.getInstance().getUserSerializationManager();
        final User user = usm.deserialize(FileUtils.loadContent(fUser));

        if (!user.getHomesMap().containsKey(homeName)) {
            p.sendMessage(Constents.getPrefix() + "§fHome §l" + homeName + "§r§f not found for player §l" + targetName + "§r§f.");
            return true;
        }

        HomeLocation hl = user.getHomesMap().get(homeName);
        World world = Bukkit.getWorld(hl.getWorld());

        if (world == null) {
            p.sendMessage(Constents.getPrefix() + "§fThe world of this home no longer exists.");
            return true;
        }

        Location loc = new Location(world, hl.getX(), hl.getY(), hl.getZ(), hl.getYaw(), hl.getPitch());
        p.teleport(loc);
        p.sendMessage(Constents.getPrefix() + "§fTeleported to §l" + targetName + "§r§f's home §l" + homeName + "§r§f.");

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (!(sender instanceof Player p)) return suggestions;
        if (!p.isOp() && !p.hasPermission("hg.admin")) return suggestions;

        if (args.length == 1) {
            String input = args[0];

            if (!input.contains(":")) {
                // Autocomplétion joueurs
                String playerPart = input;
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.getName().toLowerCase().startsWith(playerPart.toLowerCase())) {
                        suggestions.add(online.getName() + ":");
                    }
                }
            } else {
                // Autocomplétion homes du joueur
                String[] split = input.split(":");
                String targetName = split[0];
                String homePart = split.length > 1 ? split[1] : "";

                final File fUser = new File(Constents.getSaveDir(), targetName + ".json");
                if (fUser.exists()) {
                    final UserSerializationManager usm = HomeGui.getInstance().getUserSerializationManager();
                    final User user = usm.deserialize(FileUtils.loadContent(fUser));

                    for (String homeName : user.getHomesMap().keySet()) {
                        if (homeName.toLowerCase().startsWith(homePart.toLowerCase())) {
                            suggestions.add(targetName + ":" + homeName);
                        }
                    }
                }
            }
        }

        return suggestions;
    }
}
