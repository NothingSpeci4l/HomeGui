package fr.gregwll.homeGui.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EGuiListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // On délègue à la GUI si c'est la bonne
        if (event.getView().getTitle().startsWith("§x§0§0§8§D§F§FH§x§1§9§7§B§F§Fo§x§3§2§6§A§F§Fm§x§4§B§5§8§F§Fe")) {
            event.setCancelled(true);
            HomesGui.handleClick(event);
        }
    }
}
