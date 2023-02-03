package de.littleprogrammer.rules.listeners;

import de.littleprogrammer.rules.CustomePlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;

public class Listeners implements Listener {

    private Inventory inv;
    private ItemStack stack;

    private MessageListener messageListener = new MessageListener();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException, InterruptedException {

        Player player = event.getPlayer();
        CustomePlayer playerData = new CustomePlayer(player.getUniqueId());
        player.setResourcePack("https://download.mc-packs.net/pack/de1e67f350877182eb7131b9d7a28a6d60344723.zip");
    }

    @EventHandler
    public void onPlayerResourcePackStatus(PlayerResourcePackStatusEvent event) throws SQLException {

        PlayerResourcePackStatusEvent.Status status = event.getStatus();
        Player player = event.getPlayer();
        CustomePlayer playerData = new CustomePlayer(player.getUniqueId());

        if (playerData.getAcceptRules() == 0){
            player.teleport(new Location(player.getWorld(), 0, player.getWorld().getHighestBlockYAt(0, 0), 0));
        }

        if (status.equals(PlayerResourcePackStatusEvent.Status.DECLINED) || status.equals(PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD)){
            messageListener.kickPlayer(player, ChatColor.RED.toString() + ChatColor.BOLD + "Bitte aktiviere TexturePacks");
        }
        if (status.equals(PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED) || status.equals(PlayerResourcePackStatusEvent.Status.ACCEPTED)){
            if (playerData.getAcceptRules() == 0){

                //Title
                player.sendTitle(ChatColor.WHITE.toString() + ChatColor.BOLD + "\uE002", " ", 5, 999999999, 20);

                //GUI
                stack = new ItemStack(Material.MAP);
                ItemMeta meta = stack.getItemMeta();
                meta.setCustomModelData(1010);
                meta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "CLOSE");
                stack.setItemMeta(meta);

                inv = Bukkit.createInventory(null, 54, ChatColor.WHITE + "\uE000\uE000\uE000\uE000\uE000\uE000\uE000\uE000\uE001");
                inv.setItem(48, stack);
                inv.setItem(49, stack);
                inv.setItem(50, stack);

                player.openInventory(inv);

            }else {
                messageListener.sendServer(player, "Lobby-1");
            }
        }

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (ChatColor.translateAlternateColorCodes('&', event.getView().getTitle()).equals(ChatColor.WHITE + "\uE000\uE000\uE000\uE000\uE000\uE000\uE000\uE000\uE001") && event.getCurrentItem() != null){

            event.setCancelled(true);

            event.getWhoClicked().closeInventory();

        }
    }
}
