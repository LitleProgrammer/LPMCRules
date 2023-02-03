package de.littleprogrammer.rules.listeners;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.littleprogrammer.rules.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class MessageListener implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("SomeSubChannel")) {
            // Use the code sample in the 'Response' sections below to read
            // the data.
        }
    }

    public void sendServer(Player player, String server) {

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF(server);

        player.sendPluginMessage(Main.getInstance(), "BungeeCord", output.toByteArray());

    }

    public void kickPlayer(Player target, String message){

        ByteArrayDataOutput output1 = ByteStreams.newDataOutput();
        output1.writeUTF("KickPlayer");
        output1.writeUTF(target.getName());
        output1.writeUTF(message);

        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        player.sendPluginMessage(Main.getInstance(), "BungeeCord", output1.toByteArray());
    }
}
