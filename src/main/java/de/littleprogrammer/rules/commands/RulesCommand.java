package de.littleprogrammer.rules.commands;

import de.littleprogrammer.rules.CustomePlayer;
import de.littleprogrammer.rules.listeners.MessageListener;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RulesCommand implements CommandExecutor, TabCompleter {

    private MessageListener messageListener = new MessageListener();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1){
            completions.add("accept");
        }

        return completions;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 1){
                    if (args[0].equalsIgnoreCase("accept")) {
                        try {
                            CustomePlayer playerData = new CustomePlayer(player.getUniqueId());
                            if (playerData.getAcceptRules() == 0) {
                                playerData.setAcceptRules((byte) 1);
                                player.resetTitle();
                                messageListener.sendServer(player, "Lobby-1");

                                player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Du hast die Regeln Akzeptiert.\n" + ChatColor.RESET + ChatColor.GREEN + "Willkommen auf LPMC.me vielen Dank, dass du die Regeln dieses Netzwerkes akzeptiert hast und einhältst.\nWir wünschen dir viel Spaß auf dem Netzwerk.");
                            } else {
                                player.sendMessage(ChatColor.RED + "Du hast die Regeln bereits akzeptiert!");
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (args.length == 0){
                    TextComponent p1 = new TextComponent("§aBitte lies dir die ");

                    TextComponent cliclable = new TextComponent("§a§l§nRegeln ");
                    cliclable.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://lpmc.me/rules"));

                    TextComponent p2 = new TextComponent("§adurch.");

                    TextComponent p3 = new TextComponent("\n§aUnd akzeptiere sie mit ");

                    TextComponent clickCommand = new TextComponent("§a§l§n/rules accept");
                    clickCommand.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/rules accept"));

                    p1.addExtra(cliclable);
                    p1.addExtra(p2);
                    p1.addExtra(p3);
                    p1.addExtra(clickCommand);

                    player.spigot().sendMessage(p1);

                }
        }
        return false;
    }
}
