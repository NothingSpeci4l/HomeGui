package fr.gregwll.homeGui;

import fr.gregwll.homeGui.cache.UserCache;
import fr.gregwll.homeGui.cmd.*;
import fr.gregwll.homeGui.cmd.ad.CDelhomeAd;
import fr.gregwll.homeGui.cmd.ad.CHomeAd;
import fr.gregwll.homeGui.files.ConfigManager;
import fr.gregwll.homeGui.gui.EGuiListener;
import fr.gregwll.homeGui.gui.EPlayerListener;
import fr.gregwll.homeGui.files.UserSerializationManager;
import fr.gregwll.homeGui.tp.BackManager;
import fr.gregwll.homeGui.tp.CooldownManager;
import fr.gregwll.homeGui.tp.WarmupManager;
import fr.gregwll.homeGui.utils.Constents;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class HomeGui extends JavaPlugin {

    private UserSerializationManager userSerializationManager;
    private static HomeGui instance;
    private ConfigManager configManager;
    private UserCache userCache;
    private CooldownManager cooldownManager;
    private BackManager backManager;
    private WarmupManager warmupManager;

    @Override
    public void onEnable() {
        instance = this;
        this.configManager = new ConfigManager(this);

        if (!Constents.getSaveDir().exists()) {
            Constents.getSaveDir().mkdirs();
        }

        this.userSerializationManager = new UserSerializationManager();
        this.userCache = new UserCache(userSerializationManager);
        this.cooldownManager = new CooldownManager();
        this.backManager = new BackManager();
        this.warmupManager = new WarmupManager();

        // Charger les joueurs déjà connectés (reload à chaud)
        for (var player : Bukkit.getOnlinePlayers()) {
            userCache.load(player.getUniqueId(), player.getName());
        }

        getCommand("sethome").setExecutor(new CsetHome());
        getCommand("delhome").setExecutor(new CDelhome());
        getCommand("home").setExecutor(new CHome());
        getCommand("home").setTabCompleter(new CHome());

        getCommand("homeadmin").setExecutor(new CHomeAd());
        getCommand("homeadmin").setTabCompleter(new CHomeAd());

        getCommand("delhomeadmin").setExecutor(new CDelhomeAd());
        getCommand("delhomeadmin").setTabCompleter(new CDelhomeAd());

        getCommand("confirmhome").setExecutor(new CConfirmHome());
        getCommand("back").setExecutor(new CBack());

        getCommand("homereload").setExecutor((sender, cmd, label, args) -> {
            if (!sender.isOp()) {
                sender.sendMessage(Constents.getPrefix() + "§fNo permission.");
                return true;
            }
            configManager.reload();
            sender.sendMessage(Constents.getPrefix() + "§fConfig reloaded.");
            return true;
        });

        Bukkit.getPluginManager().registerEvents(new EGuiListener(), this);
        Bukkit.getPluginManager().registerEvents(new EPlayerListener(), this);

        int pluginId = 31062;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new SimplePie("chart_id", () -> "My value"));
    }

    @Override
    public void onDisable() {
        // Sauvegarder tous les joueurs encore connectés
        if (userCache != null) {
            userCache.saveAll();
        }
    }

    public UserSerializationManager getUserSerializationManager() {
        return userSerializationManager;
    }

    public static HomeGui getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public UserCache getUserCache() {
        return userCache;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public BackManager getBackManager() {
        return backManager;
    }

    public WarmupManager getWarmupManager() {
        return warmupManager;
    }
}