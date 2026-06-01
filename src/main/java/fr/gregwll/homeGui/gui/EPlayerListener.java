package fr.gregwll.homeGui.gui;

import fr.gregwll.homeGui.HomeGui;
import fr.gregwll.homeGui.utils.Constents;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EPlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        HomeGui.getInstance().getUserCache().load(player.getUniqueId(), player.getName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        HomeGui.getInstance().getUserCache().unload(player.getUniqueId(), player.getName());
        HomeGui.getInstance().getWarmupManager().cancel(player.getUniqueId());
        HomeGui.getInstance().getCooldownManager().remove(player.getUniqueId());
        HomeGui.getInstance().getBackManager().remove(player.getUniqueId());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        var warmupManager = HomeGui.getInstance().getWarmupManager();

        if (!warmupManager.isWaitingFor(player.getUniqueId())) return;
        if (!HomeGui.getInstance().getConfigManager().isCancelOnMove()) return;

        Location from = event.getFrom();
        Location to = event.getTo();
        if (to == null) return;

        // Ignorer les rotations de caméra, uniquement les déplacements XYZ
        if (from.getBlockX() == to.getBlockX()
                && from.getBlockY() == to.getBlockY()
                && from.getBlockZ() == to.getBlockZ()) return;

        warmupManager.cancel(player.getUniqueId());
        player.sendMessage(Constents.getPrefix() + "§cTeleportation canceled : you moved.");
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        var warmupManager = HomeGui.getInstance().getWarmupManager();

        if (!warmupManager.isWaitingFor(player.getUniqueId())) return;
        if (!HomeGui.getInstance().getConfigManager().isCancelOnDamage()) return;

        warmupManager.cancel(player.getUniqueId());
        player.sendMessage(Constents.getPrefix() + "§cTeleportation canceled : you taked damages.");
    }
}