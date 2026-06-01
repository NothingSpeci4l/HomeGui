package fr.gregwll.homeGui.files;

import fr.gregwll.homeGui.HomeGui;

public class ConfigManager {

    private final HomeGui plugin;

    public ConfigManager(HomeGui plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
    }

    public int getMaxHomes() {
        return plugin.getConfig().getInt("max-homes", 3);
    }

    public String getPrefix() {
        return plugin.getConfig().getString("prefix", "§bHomeGUI §8» §f");
    }

    public boolean isUnsafeTeleportAllowed() {
        return plugin.getConfig().getBoolean("allow-unsafe-teleport", false);
    }

    public void reload() {
        plugin.reloadConfig();
    }

    public int getConfirmExpiry() {
        return plugin.getConfig().getInt("confirm-expiry", 60);
    }

    public int getTeleportCooldown() {
        return plugin.getConfig().getInt("teleport-cooldown", 5);
    }

    public int getTeleportWarmup() {
        return plugin.getConfig().getInt("teleport-warmup", 0);
    }

    public boolean isCancelOnMove() {
        return plugin.getConfig().getBoolean("cancel-on-move", true);
    }

    public boolean isCancelOnDamage() {
        return plugin.getConfig().getBoolean("cancel-on-damage", true);
    }

    public boolean isBackEnabled() {
        return plugin.getConfig().getBoolean("enable-back", true);
    }
}