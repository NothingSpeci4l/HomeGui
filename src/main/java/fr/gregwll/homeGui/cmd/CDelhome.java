package fr.gregwll.homeGui.cmd;

import fr.gregwll.homeGui.*;
import fr.gregwll.homeGui.files.FileUtils;
import fr.gregwll.homeGui.files.UserSerializationManager;
import fr.gregwll.homeGui.obj.User;
import fr.gregwll.homeGui.utils.Constents;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CDelhome implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player p) {
            if(args.length == 1) {
                String homeName = args[0];

                final File fUser = new File(Constents.getSaveDir(), p.getName() + ".json");
                if (!fUser.exists()) {
                    p.sendMessage(Constents.getPrefix() + "§fYou have no homes.");
                    return true;
                }

                final UserSerializationManager usm = HomeGui.getInstance().getUserSerializationManager();
                final User user = usm.deserialize(FileUtils.loadContent(fUser));

                if (!user.getHomesMap().containsKey(homeName)) {
                    p.sendMessage(Constents.getPrefix() + "§fHome §l" + homeName + "§r§f not found.");
                    return true;
                }

                user.getHomesMap().remove(homeName);
                FileUtils.save(fUser, usm.serialize(user));
                p.sendMessage(Constents.getPrefix() + "§fHome §l" + homeName + "§r§f deleted.");
            } else {
                p.sendMessage(Constents.getPrefix() + "§f§lUsage: §7/delhome <homeName>");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (sender instanceof Player p && args.length == 1) {
            final File fUser = new File(Constents.getSaveDir(), p.getName() + ".json");
            if (!fUser.exists()) return suggestions;

            final UserSerializationManager usm = HomeGui.getInstance().getUserSerializationManager();
            final User user = usm.deserialize(FileUtils.loadContent(fUser));

            for (String homeName : user.getHomesMap().keySet()) {
                if (homeName.startsWith(args[0])) {
                    suggestions.add(homeName);
                }
            }
        }
        return suggestions;
    }
}
