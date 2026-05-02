package fr.gregwll.homeGui;

import fr.gregwll.homeGui.cmd.*;
import fr.gregwll.homeGui.cmd.ad.CDelhomeAd;
import fr.gregwll.homeGui.cmd.ad.CHomeAd;
import fr.gregwll.homeGui.files.ConfigManager;
import fr.gregwll.homeGui.gui.EGuiListener;
import fr.gregwll.homeGui.files.UserSerializationManager;
import fr.gregwll.homeGui.utils.Constents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class HomeGui extends JavaPlugin {

    private UserSerializationManager userSerializationManager;
    private static HomeGui instance;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;
        this.configManager = new ConfigManager(this);

        if(!Constents.getSaveDir().exists()) {
            Constents.getSaveDir().mkdirs();
        }

        this.userSerializationManager = new UserSerializationManager();

        getCommand("sethome").setExecutor(new CsetHome());
        getCommand("delhome").setExecutor(new CDelhome());
        getCommand("home").setExecutor(new CHome());

        getCommand("homeadmin").setExecutor(new CHomeAd());
        getCommand("homeadmin").setTabCompleter(new CHomeAd());

        getCommand("delhomeadmin").setExecutor(new CDelhomeAd());
        getCommand("delhomeadmin").setTabCompleter(new CDelhomeAd());

        getCommand("confirmhome").setExecutor(new CConfirmHome());

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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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
}
