package fr.gregwll.homeGui.gui;

import fr.gregwll.homeGui.HomeGui;
import fr.gregwll.homeGui.obj.HomeLocation;
import fr.gregwll.homeGui.obj.User;
import fr.gregwll.homeGui.utils.Constents;
import fr.gregwll.homeGui.tp.TeleportUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class HomesGui {

    private static final int SLOTS_PER_PAGE = 7;
    private static final int[] HOME_SLOTS = {10, 11, 12, 13, 14, 15, 16};

    public static void open(Player player, int page) {
        User user = HomeGui.getInstance().getUserCache().get(player.getUniqueId());

        if (user == null || user.getHomesMap().isEmpty()) {
            player.sendMessage(Constents.getPrefix() + "§fYou have no homes.");
            return;
        }

        List<String> homeNames = new ArrayList<>(user.getHomesMap().keySet());

        int totalPages = (int) Math.ceil((double) homeNames.size() / SLOTS_PER_PAGE);
        if (totalPages == 0) totalPages = 1;
        if (page >= totalPages) page = totalPages - 1;
        if (page < 0) page = 0;

        Inventory inv = Bukkit.createInventory(null, 27,
                "§x§0§0§8§D§F§FH§x§1§9§7§B§F§Fo§x§3§2§6§A§F§Fm§x§4§B§5§8§F§Fe §8[§7" + (page + 1) + "§8/§7" + totalPages + "§8]");

        ItemStack deco = makeGlass(Material.BLUE_STAINED_GLASS_PANE, " ");
        for (int i = 0; i < 9; i++) inv.setItem(i, deco);
        for (int i = 18; i < 27; i++) inv.setItem(i, deco);

        if (page > 0) {
            inv.setItem(9, makeArrow("§c§lPrevious page", Material.RED_STAINED_GLASS_PANE));
        } else {
            inv.setItem(9, makeGlass(Material.RED_STAINED_GLASS_PANE, " "));
        }

        if (page < totalPages - 1) {
            inv.setItem(17, makeArrow("§a§lNext page", Material.GREEN_STAINED_GLASS_PANE));
        } else {
            inv.setItem(17, makeGlass(Material.GREEN_STAINED_GLASS_PANE, " "));
        }

        int start = page * SLOTS_PER_PAGE;
        int end = Math.min(start + SLOTS_PER_PAGE, homeNames.size());

        for (int i = start; i < end; i++) {
            String homeName = homeNames.get(i);
            HomeLocation hl = user.getHomesMap().get(homeName);
            int slot = HOME_SLOTS[i - start];
            inv.setItem(slot, makeHomeHead(player, homeName, hl));
        }

        player.openInventory(inv);
    }

    public static void handleClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();
        String title = event.getView().getTitle();

        int currentPage = 0;
        try {
            String stripped = ChatColor.stripColor(title).replaceAll(".*\\[(\\d+)/.*", "$1");
            currentPage = Integer.parseInt(stripped) - 1;
        } catch (Exception ignored) {}

        if (slot == 9) {
            if (currentPage > 0) open(player, currentPage - 1);
            return;
        }

        if (slot == 17) {
            open(player, currentPage + 1);
            return;
        }

        for (int i = 0; i < HOME_SLOTS.length; i++) {
            if (slot == HOME_SLOTS[i]) {
                ItemStack item = event.getCurrentItem();
                if (item == null || item.getItemMeta() == null) return;

                String homeName = ChatColor.stripColor(item.getItemMeta().getDisplayName());

                User user = HomeGui.getInstance().getUserCache().get(player.getUniqueId());
                if (user == null || !user.getHomesMap().containsKey(homeName)) return;

                HomeLocation hl = user.getHomesMap().get(homeName);
                World world = Bukkit.getWorld(hl.getWorld());
                if (world == null) {
                    player.sendMessage(Constents.getPrefix() + "§fThe world of this home no longer exists.");
                    return;
                }

                player.closeInventory();
                Location loc = new Location(world, hl.getX(), hl.getY(), hl.getZ(), hl.getYaw(), hl.getPitch());
                TeleportUtils.teleportOrConfirm(player, loc, homeName);
                return;
            }
        }
    }

    private static ItemStack makeHomeHead(Player player, String homeName, HomeLocation hl) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(player);
        meta.setDisplayName("§1§l" + homeName);
        List<String> lore = new ArrayList<>();
        lore.add("§7" + (int) hl.getX() + " " + (int) hl.getY() + " " + (int) hl.getZ());
        lore.add("§7World: §f" + hl.getWorld());
        lore.add("");
        lore.add("§9Click to teleport");
        meta.setLore(lore);
        head.setItemMeta(meta);
        return head;
    }

    private static ItemStack makeArrow(String name, Material mat) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack makeGlass(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}