package fr.gregwll.homeGui.cmd;

import fr.gregwll.homeGui.*;
import fr.gregwll.homeGui.files.FileUtils;
import fr.gregwll.homeGui.files.UserSerializationManager;
import fr.gregwll.homeGui.obj.HomeLocation;
import fr.gregwll.homeGui.obj.User;
import fr.gregwll.homeGui.utils.Constents;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class CsetHome implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 1) {
                final File fUser = new File(Constents.getSaveDir(), p.getName() + ".json");
                final UserSerializationManager usm = HomeGui.getInstance().getUserSerializationManager();
                String homeName = args[0];
                Location loc = p.getLocation();

                User user;
                if (fUser.exists()) {
                    user = usm.deserialize(FileUtils.loadContent(fUser));
                } else {
                    user = new User(p.getName());
                }

                if (user.getHomesMap().containsKey(homeName)) {
                    p.sendMessage(Constents.getPrefix() + "§fHome §l" + homeName + "§r§f already exists, use §l/delhome§r§f first.");
                    return true;
                }

                int maxHomes = HomeGui.getInstance().getConfigManager().getMaxHomes();
                if (!p.isOp() && user.getHomesMap().size() >= maxHomes) {
                    p.sendMessage(Constents.getPrefix() + "§fYou have reached the maximum number of homes §l(" + maxHomes + ")§r§f.");
                    return true;
                }


                user.getHomesMap().put(homeName, new HomeLocation(
                        loc.getX(), loc.getY(), loc.getZ(),
                        loc.getYaw(), loc.getPitch(),
                        loc.getWorld().getName()
                ));

                FileUtils.save(fUser, usm.serialize(user));
                p.sendMessage(Constents.getPrefix() + "§fHome §l" + homeName + "§r§f set at §7" +
                        (int) loc.getX() + " " + (int) loc.getY() + " " + (int) loc.getZ() + "§f.");

                if (!HomeGui.getInstance().getConfigManager().isUnsafeTeleportAllowed()) {
                    Location feet = loc.clone();
                    Location head = loc.clone().add(0, 1, 0);
                    Location ground = loc.clone().add(0, -1, 0);

                    boolean unsafe = loc.getBlock().getType().isSolid()
                            || head.getBlock().getType().isSolid()
                            || !ground.getBlock().getType().isSolid();

                    if (unsafe) {
                        p.sendMessage(Constents.getPrefix() + "§e⚠ Warning: this location appears unsafe. You may not be able to teleport here.");
                    }
                }
            } else {
                p.sendMessage(Constents.getPrefix() + "§f§lUsage: §7/sethome <homeName>");
            }
        }
        return true;
    }
}