package de.littleprogrammer.rules.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TestInvCommand implements CommandExecutor {
    private Inventory inv;
    private ItemStack stack;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        stack = new ItemStack(Material.MAP);
        ItemMeta meta = stack.getItemMeta();
        meta.setCustomModelData(1010);
        stack.setItemMeta(meta);

        inv = Bukkit.createInventory(null, 54, ChatColor.WHITE + "\uE000\uE000\uE000\uE000\uE000\uE000\uE000\uE000\uE001");
        inv.setItem(48, stack);
        inv.setItem(49, stack);
        inv.setItem(50, stack);

        player.openInventory(inv);

        player.sendTitle(ChatColor.WHITE.toString() + ChatColor.BOLD + "\uE002", " ", 5, 300, 20);


        return false;
    }
}
