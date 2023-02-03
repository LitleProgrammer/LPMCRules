package de.littleprogrammer.rules.commands;

import de.littleprogrammer.rules.listeners.MessageListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand implements CommandExecutor {

    private MessageListener messageListener = new MessageListener();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        messageListener.kickPlayer((Player) sender, "Test");

        sender.sendMessage("Should've been kicked");

        return false;
    }
}
